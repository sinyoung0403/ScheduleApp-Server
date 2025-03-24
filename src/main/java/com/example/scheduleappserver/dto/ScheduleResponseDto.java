package com.example.scheduleappserver.dto;

import com.example.scheduleappserver.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
  private Long id;
  private String task;
  private Long authorId;
  private String created;
  private String updated;

  public ScheduleResponseDto(Plan schedule) {
    this.id = schedule.getId();
    this.task = schedule.getTask();
    this.authorId = schedule.getAuthorId();
  }

}
