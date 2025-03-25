package com.example.scheduleappserver.service;

import com.example.scheduleappserver.dto.AuthorRequestDto;
import com.example.scheduleappserver.dto.AuthorResponseDto;
import com.example.scheduleappserver.entity.Author;
import com.example.scheduleappserver.exception.DataNotFoundException;
import com.example.scheduleappserver.repository.AuthorRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
  private final AuthorRepository authorRepository;

  public AuthorServiceImpl(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  // 데이터 추가
  @Override
  public AuthorResponseDto saveAuthor(AuthorRequestDto dto) {
    Author author = new Author(dto.getName(), dto.getEmail());
    return authorRepository.saveAuthor(author);
  }

  // 작성자 모두 조회
  @Override
  public List<AuthorResponseDto> findAllAuthor() {
    return authorRepository.findAllAuthor();
  }

  // 작성자 조회
  @Override
  public AuthorResponseDto findAuthorById(Long id) {
    return new AuthorResponseDto(authorRepository.findAuthorByIdOrElseThrow(id));
  }

  // 작성자 수정
  @Transactional
  @Override
  public AuthorResponseDto editAuthor(Long id, AuthorRequestDto dto) {

    // 1. 업데이트
    int editRow = authorRepository.editAuthor(id, dto);

    // 2. 업데이트 된 row 가 없을 시 에러 발생
    if (editRow == 0) {
      throw new DataNotFoundException("해당하는 작성자가 존재하지 않습니다. id : " + id);
    }

    // 3. 변경된 Data 를 entity 로 받기 / 조회 실패 시 Not_found 에러
    Author author = authorRepository.findAuthorByIdOrElseThrow(id);

    return new AuthorResponseDto(author);
  }

  // 작성자 삭제
  @Override
  public void deleteAuthor(Long id) {
    int deleteRow = authorRepository.deleteAuthor(id);

    if (deleteRow == 0) {
      throw new DataNotFoundException("해당하는 작성자가 존재하지 않습니다. id : " + id);
    }
  }
}
