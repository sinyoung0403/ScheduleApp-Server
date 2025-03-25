package com.example.scheduleappserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Plan {
  private Long id;
  private String task;
  private Long authorId;
  private String pwd;
  private String created;
  private String updated;

  public Plan(String task, Long authorId, String pwd) {
    this.task = task;
    this.authorId = authorId;
    this.pwd = pwd;
  }
}
