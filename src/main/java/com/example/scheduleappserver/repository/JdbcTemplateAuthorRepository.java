package com.example.scheduleappserver.repository;

import com.example.scheduleappserver.dto.AuthorRequestDto;
import com.example.scheduleappserver.dto.AuthorResponseDto;
import com.example.scheduleappserver.entity.Author;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateAuthorRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  // 작성자 추가
  @Override
  public AuthorResponseDto saveAuthor(Author author) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

    jdbcInsert.withTableName("author").usingGeneratedKeyColumns("id");
    String currentTime = JdbcTemplateScheduleRepository.changeTimestamp();

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("name", author.getName());
    parameters.put("email", author.getEmail());
    parameters.put("created", currentTime);
    parameters.put("updated", currentTime);

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    return new AuthorResponseDto(key.longValue(), author.getName(), author.getEmail(), currentTime, currentTime);
  }

  // 작성자 조회
  public Author findScheduleByIdOrElseThrow(Long id) {
    List<Author> result = jdbcTemplate.query("SELECT * FROM author WHERE id = ?", authorRowMapper(), id);
    return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
  }

  // 모든 작성자 조회
  @Override
  public List<AuthorResponseDto> findAllAuthor() {
    return jdbcTemplate.query("SELECT * FROM author", authorRowMapperV2());
  }

  // 작성자 수정
  @Override
  public int editAuthor(Long id, AuthorRequestDto dto) {
    String currentTime = JdbcTemplateScheduleRepository.changeTimestamp();
    // 이메일과 작성자면 수정 가능.
    String name = dto.getName();
    String email = dto.getEmail();
    return jdbcTemplate.update("UPDATE author " +
            "SET name = CASE\n" +
            "        WHEN ? is not null THEN ?\n" +
            "        ELSE name END,\n" +
            "  email = CASE\n" +
            "        WHEN ? is not null THEN ?\n" +
            "        ELSE email END,\n" +
            "  updated = ? \n " +
            "WHERE id = ?", name, name, email, email, currentTime, id);
  }

  // 작성자 수정 2
  public int updateDate(Long id) {
    String currentTime = JdbcTemplateScheduleRepository.changeTimestamp();
    return jdbcTemplate.update("UPDATE author SET created = ?, updated = ? WHERE id = ?", currentTime, currentTime, id);
  }

  // 작성자 삭제
  @Override
  public int deleteAuthor(Long id) {
    return jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
  }

  // author Mapping
  private RowMapper<Author> authorRowMapper() {
    return new RowMapper<Author>() {
      @Override
      public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Author(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("created"),
                rs.getString("updated")
        );
      }
    };

  }

  // authorResponseDto Mapping
  private RowMapper<AuthorResponseDto> authorRowMapperV2() {
    return new RowMapper<AuthorResponseDto>() {
      @Override
      public AuthorResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AuthorResponseDto(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("created"),
                rs.getString("updated")
        );
      }
    };
  }

}
