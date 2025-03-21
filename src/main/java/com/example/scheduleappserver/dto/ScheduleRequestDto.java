package com.example.scheduleappserver.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
  private String task;
  private String author;
  private String pwd;
  private String created;
  private String updated;


}
