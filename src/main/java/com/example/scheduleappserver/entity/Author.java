package com.example.scheduleappserver.entity;

import com.example.scheduleappserver.dto.AuthorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Author {
  private Long id;
  private String name;
  private String email;
  private String created;
  private String updated;

  public Author(String name, String email) {
    this.name = name;
    this.email = email;
  }
}
