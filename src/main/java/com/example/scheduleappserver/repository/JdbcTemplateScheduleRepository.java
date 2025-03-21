package com.example.scheduleappserver.repository;

import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.entity.Schedule;
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
  public ScheduleResponseDto saveSchedule(Schedule schedule) {

    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

    jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");
    String nowTimestamp = changeTimestamp();

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("task", schedule.getTask());
    parameters.put("author", schedule.getAuthor());
    parameters.put("pwd", schedule.getPwd());
    parameters.put("created", nowTimestamp);
    parameters.put("updated", nowTimestamp);

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return new ScheduleResponseDto(key.longValue(), schedule.getTask(), schedule.getAuthor(), nowTimestamp, nowTimestamp);
  }

  @Override
  public List<ScheduleResponseDto> findAllSchedule(String author, String updated) {
    return jdbcTemplate.query("SELECT * FROM schedule WHERE (author = ?) OR (DATE(updated) = ?) ORDER BY updated", scheduleRowMapper(), author, updated);
  }

  @Override
  public Schedule findScheduleByIdOrElseThrow(Long id) {
    List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapperV2(), id);
    return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
  }

  private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
    return new RowMapper<ScheduleResponseDto>() {
      @Override
      public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("task"),
                rs.getString("author"),
                rs.getString("created"),
                rs.getString("updated")
        );
      }
    };
  }

  private RowMapper<Schedule> scheduleRowMapperV2() {
    return new RowMapper<Schedule>() {
      @Override
      public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Schedule(
                rs.getLong("id"),
                rs.getString("task"),
                rs.getString("author"),
                rs.getString("pwd"),
                rs.getString("created"),
                rs.getString("updated")
        );
      }
    };
  }


  // 현재 시간 반환
  public String changeTimestamp() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
