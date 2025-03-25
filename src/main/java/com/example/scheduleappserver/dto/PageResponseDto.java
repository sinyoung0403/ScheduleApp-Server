package com.example.scheduleappserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponseDto {
  private Long id;
  private String task;
  private Long authorId;
  private String name;
  private String created;
  private String updated;
}
