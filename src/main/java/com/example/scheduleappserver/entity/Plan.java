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
  private String updated;
  private String created;

  public Plan(String task, Long authorId, String pwd) {
    this.task = task;
    this.authorId = authorId;
    this.pwd = pwd;
  }

  public Plan(Long id, String task, Long authorId, String pwd) {
    this.id = id;
    this.task = task;
    this.authorId = authorId;
    this.pwd = pwd;
  }


  public void updateTaskAndAuthor(String task, Long authorId) {
    this.task = task;
    this.authorId = authorId;
  }
}
