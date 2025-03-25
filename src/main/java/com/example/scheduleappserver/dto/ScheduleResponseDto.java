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

  public ScheduleResponseDto(Plan plan) {
    this.id = plan.getId();
    this.task = plan.getTask();
    this.authorId = plan.getAuthorId();
    this.created = plan.getCreated();
    this.updated = plan.getUpdated();
  }
}
