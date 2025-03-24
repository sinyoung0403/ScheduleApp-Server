package com.example.scheduleappserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {
  private Long id;
  private String task;
  private String author;
  private String pwd;
  private String created;
  private String updated;

  public Schedule(String task, String author, String pwd) {
    this.task = task;
    this.author = author;
    this.pwd = pwd;
  }

  public void updateTaskAndAuthor(String task, String author) {
    this.task = task;
    this.author = author;
  }

}
