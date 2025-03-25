package com.example.scheduleappserver.repository;

import com.example.scheduleappserver.dto.PageResponseDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.dto.ScheduleShowResponseDto;
import com.example.scheduleappserver.entity.Page;
import com.example.scheduleappserver.entity.Plan;
import com.example.scheduleappserver.exception.DataNotFoundException;
import com.example.scheduleappserver.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {
  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateScheduleRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  // 일정 저장
  @Override
  public ScheduleResponseDto saveSchedule(Plan schedule) {

    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

    jdbcInsert.withTableName("plan").usingGeneratedKeyColumns("id");
    String currentTime = changeTimestamp();

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("task", schedule.getTask());
    parameters.put("authorId", schedule.getAuthorId());
    parameters.put("pwd", schedule.getPwd());
    parameters.put("created", currentTime);
    parameters.put("updated", currentTime);

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return new ScheduleResponseDto(key.longValue(), schedule.getTask(), schedule.getAuthorId(), currentTime, currentTime);
  }

  // 작성자의 name 과 timestamp 로 일정 조회
  @Override
  public List<ScheduleShowResponseDto> findAllSchedule(String name, String updated) {
    return jdbcTemplate.query("SELECT p.id, p.task, a.id, a.name, a.email, p.created, p.updated " +
            "FROM plan p JOIN author a ON p.authorId = a.id " +
            "WHERE (a.name = COALESCE(?, a.name)) OR (DATE(p.updated) = COALESCE(?, DATE(p.updated))) " +
            "ORDER BY p.updated", scheduleRowMapper(), name, updated);
  }

  // 일정 모두 조회
  @Override
  public List<ScheduleShowResponseDto> findAllAuthorSchedule(Long authorId) {
    // author 테이블에 있는 아이디 값을 가져와서, 그 아이디 값과 일치하는 일정 조회.
    return jdbcTemplate.query("SELECT p.id, p.task, a.id, a.name, a.email, p.created, p.updated " +
            "FROM plan p JOIN author a ON p.authorId = a.id " +
            "WHERE a.id = ?", scheduleRowMapper(), authorId);
  }

  // 일정 아이디로 조회
  @Override
  public Plan findScheduleByIdOrElseThrow(Long id) {
    List<Plan> result = jdbcTemplate.query("SELECT * FROM plan WHERE id = ?", scheduleRowMapperV2(), id);
    return result.stream().findAny().orElseThrow(() -> new DataNotFoundException("해당하는 일정이 존재하지 않습니다. id : " + id));
  }

  // 일정 수정
  @Override
  public int editSchedule(Long id, String task, Long authorId) {
    String sql = "UPDATE plan p " +
            "SET  task = CASE WHEN ? is not null THEN ? ELSE task END, " +
            "     authorId = CASE WHEN ? is not null THEN ? ELSE authorId END, " +
            "     updated = ? " +
            "WHERE id = ?";
    return jdbcTemplate.update(sql, task, task, authorId, authorId, changeTimestamp(), id);
  }

  // 일정 삭제
  @Override
  public void deleteSchedule(Long id) {
    jdbcTemplate.update("DELETE FROM plan WHERE id = ?", id);
  }

  // 일정 페이징 조회
  public Page<PageResponseDto> getPlan(int pageNumber, int pageSize) {
    int offset = (pageNumber - 1) * pageSize;
    List<PageResponseDto> result = jdbcTemplate.query("SELECT p.id, p.task, a.id, a.name ,p.created, p.updated " +
            "FROM plan p JOIN author a on a.id = p.authorId " +
            "ORDER BY p.id " + "LIMIT ? OFFSET ?", PageRowMapper(), pageSize, offset);
    return new Page<>(result, pageNumber, pageSize);
  }

  public boolean findScheduleByPwd(Long id, String pwd) {
    List<Plan> result = jdbcTemplate.query("SELECT * FROM plan WHERE id = ? AND pwd = ?", scheduleRowMapperV2(), id, pwd);
    return result.stream().findAny().isPresent();
  }

  private RowMapper<PageResponseDto> PageRowMapper() {
    return new RowMapper<PageResponseDto>() {
      @Override
      public PageResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PageResponseDto(rs.getLong("p.id"), rs.getString("p.task"), rs.getLong("a.id"), rs.getString("a.name"), rs.getString("p.created"), rs.getString("p.updated"));
      }
    };
  }

  private RowMapper<ScheduleShowResponseDto> scheduleRowMapper() {
    return new RowMapper<ScheduleShowResponseDto>() {
      @Override
      public ScheduleShowResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScheduleShowResponseDto(
                rs.getLong("p.id"),
                rs.getString("p.task"),
                rs.getLong("a.id"),
                rs.getString("a.name"),
                rs.getString("email"),
                rs.getString("created"),
                rs.getString("updated"));
      }
    };
  }

  private RowMapper<Plan> scheduleRowMapperV2() {
    return new RowMapper<Plan>() {
      @Override
      public Plan mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Plan(rs.getLong("id"), rs.getString("task"), rs.getLong("authorId"), rs.getString("pwd"), rs.getString("created"), rs.getString("updated"));
      }
    };
  }

  // 현재 시간 반환
  public static String changeTimestamp() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}

