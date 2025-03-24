package com.example.scheduleappserver.repository;

import com.example.scheduleappserver.dto.AuthorRequestDto;
import com.example.scheduleappserver.dto.AuthorResponseDto;
import com.example.scheduleappserver.entity.Author;

import java.util.List;

public interface AuthorRepository {
  AuthorResponseDto saveAuthor(Author author);

  Author findAuthorByIdOrElseThrow(Long id);

  int updateDate(Long id);

  List<AuthorResponseDto> findAllAuthor();

  int editAuthor(Long id, AuthorRequestDto dto);

  int deleteAuthor(Long id);

  boolean findAuthorByIdIsEmpty(Long id);
}
