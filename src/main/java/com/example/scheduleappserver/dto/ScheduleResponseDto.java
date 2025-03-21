package com.example.scheduleappserver.dto;

import com.example.scheduleappserver.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
  private Long id;
  private String task;
  private String author;
  private String created;
  private String updated;

  public ScheduleResponseDto(Schedule schedule) {
    this.id = schedule.getId();
    this.task = schedule.getTask();
    this.author = schedule.getAuthor();
    this.created = schedule.getCreated();
    this.updated = schedule.getUpdated();
  }




}
