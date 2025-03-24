package com.example.scheduleappserver.service;

import com.example.scheduleappserver.dto.AuthorRequestDto;
import com.example.scheduleappserver.dto.AuthorResponseDto;

import java.util.List;

public interface AuthorService {
  AuthorResponseDto saveAuthor(AuthorRequestDto dto);

  List<AuthorResponseDto> findAllAuthor();

  AuthorResponseDto findAuthorById(Long id);

  AuthorResponseDto editAuthor(Long id, AuthorRequestDto dto);

  void deleteAuthor(Long id);
}
