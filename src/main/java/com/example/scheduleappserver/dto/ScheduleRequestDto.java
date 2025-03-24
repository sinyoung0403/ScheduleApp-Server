package com.example.scheduleappserver.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
  private String task;
  private Long authorId;
  private String pwd;
}
