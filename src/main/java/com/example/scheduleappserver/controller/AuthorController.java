package com.example.scheduleappserver.controller;

import com.example.scheduleappserver.dto.AuthorRequestDto;
import com.example.scheduleappserver.dto.AuthorResponseDto;
import com.example.scheduleappserver.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  // 작성자 생성
  @PostMapping
  public ResponseEntity<AuthorResponseDto> saveAuthor(@RequestBody AuthorRequestDto authorRequestDto) {
    return new ResponseEntity<>(authorService.saveAuthor(authorRequestDto), HttpStatus.CREATED);
  }

  // 작성자 모두 조회
  @GetMapping
  public ResponseEntity<List<AuthorResponseDto>> findAllAuthor() {
    return new ResponseEntity<>(authorService.findAllAuthor(), HttpStatus.OK);
  }

  // 작성자 조회
  @GetMapping("/{id}")
  public ResponseEntity<AuthorResponseDto> findAuthorById(@PathVariable Long id) {
    return new ResponseEntity<>(authorService.findAuthorById(id), HttpStatus.OK);
  }

  // 작성자 수정
  @PatchMapping("/{id}")
  public ResponseEntity<AuthorResponseDto> editAuthor(
          @PathVariable Long id,
          @RequestBody AuthorRequestDto dto
  ) {
    return new ResponseEntity<>(authorService.editAuthor(id, dto), HttpStatus.OK);
  }

  // 작성자 삭제
  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
    authorService.deleteAuthor(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
