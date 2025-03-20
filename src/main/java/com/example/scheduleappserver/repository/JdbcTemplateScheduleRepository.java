package com.example.scheduleappserver.repository;

import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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

    return new ScheduleResponseDto(key.longValue(), schedule.getTask(), schedule.getAuthor(), schedule.getPwd(), nowTimestamp, nowTimestamp);
  }

  @Override
  public List<ScheduleResponseDto> findAllSchedule() {
    return jdbcTemplate.query("SELECT * FROM schedule ORDER BY updated", scheduleRowMapper());
  }

  @Override
  public Schedule findScheduleByIdOrElseThrow(Long id) {
    return null;
  }

  private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
    return new RowMapper<ScheduleResponseDto>() {
      @Override
      public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScheduleResponseDto(
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
