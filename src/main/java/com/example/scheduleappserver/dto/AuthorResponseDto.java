package com.example.scheduleappserver.dto;

import com.example.scheduleappserver.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorResponseDto {
  private Long id;
  private String name;
  private String email;
  private String created;
  private String updated;

  public AuthorResponseDto(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public AuthorResponseDto(Author author) {
    this.id = author.getId();
    this.name = author.getName();
    this.email = author.getEmail();
    this.created = author.getCreated();
    this.updated = author.getUpdated();
  }
}
