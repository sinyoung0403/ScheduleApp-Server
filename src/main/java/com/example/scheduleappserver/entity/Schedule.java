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

  // Save 즉. Created 할 경우
  // id 값을 빼고, schedule 을 만들 수 있어야 함.
  public Schedule(String task, String author, String pwd) {
    this.task = task;
    this.author = author;
    this.pwd = pwd;
  }


}
