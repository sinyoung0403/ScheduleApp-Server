package com.example.scheduleappserver.repository;

import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.dto.ScheduleShowResponseDto;
import com.example.scheduleappserver.entity.Plan;
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

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {
  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateScheduleRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

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

    return new ScheduleResponseDto(key.longValue(), schedule.getTask(), schedule.getAuthorId(),currentTime,currentTime);
  }

  // 작성자의 name 과 timestamp 로 일정 조회
  @Override
  public List<ScheduleShowResponseDto> findAllSchedule(String name, String updated) {
    return jdbcTemplate.query("SELECT p.id, p.task, a.id, a.email, p.created, p.updated FROM plan p JOIN author a ON p.authorId = a.id " +
            "WHERE (a.name = COALESCE(?, a.name)) OR (DATE(p.updated) = COALESCE(?, DATE(p.updated))) ORDER BY p.updated", scheduleRowMapper(), name, updated);
  }

  @Override
  public List<ScheduleShowResponseDto> findAllAuthorSchedule(Long authorId) {
    // author 테이블에 있는 아이디 값을 가져와서, 그 아이디 값과 일치하는 일정 조회.
    return jdbcTemplate.query("SELECT p.id, p.task, a.id, a.email, p.created, p.updated FROM plan p JOIN author a ON p.authorId = a.id " +
            "WHERE a.id = ?", scheduleRowMapper(), authorId);
  }

  @Override
  public Plan findScheduleByIdOrElseThrow(Long id) {
    List<Plan> result = jdbcTemplate.query("SELECT * FROM plan WHERE id = ?", scheduleRowMapperV2(), id);
    return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
  }

  @Override
  public int editSchedule(Long id, String task, String author) {
    return jdbcTemplate.update("UPDATE schedule " +
            "SET task = CASE\n" +
            "        WHEN ? is not null THEN ?\n" +
            "        ELSE task END,\n" +
            "  author = CASE\n" +
            "        WHEN ? is not null THEN ?\n" +
            "        ELSE author END,\n" +
            "  updated = ? \n " +
            "WHERE id = ?", task, task, author, author, changeTimestamp(), id);
  }

  @Override
  public void deleteSchedule(Long id) {
    jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
  }

  public boolean findScheduleByPwd(Long id, String pwd) {
    List<Plan> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ? AND pwd = ?", scheduleRowMapperV2(), id, pwd);
    return result.stream().findAny().isPresent();
  }

  private RowMapper<ScheduleShowResponseDto> scheduleRowMapper() {
    return new RowMapper<ScheduleShowResponseDto>() {
      @Override
      public ScheduleShowResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScheduleShowResponseDto(
                rs.getLong("p.id"),
                rs.getString("p.task"),
                rs.getLong("a.id"),
                rs.getString("email"),
                rs.getString("created"),
                rs.getString("updated")
        );
      }
    };
  }

  private RowMapper<Plan> scheduleRowMapperV2() {
    return new RowMapper<Plan>() {
      @Override
      public Plan mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Plan(
                rs.getLong("id"),
                rs.getString("task"),
                rs.getLong("authorId"),
                rs.getString("pwd")
        );
      }
    };
}

  // 현재 시간 반환
  public static String changeTimestamp() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
