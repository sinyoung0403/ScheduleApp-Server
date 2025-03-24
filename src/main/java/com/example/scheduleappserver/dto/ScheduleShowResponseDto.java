package com.example.scheduleappserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleShowResponseDto {
  private Long id;
  private String task;
  private Long authorId;
  private String email;
  private String created;
  private String updated;
}
