package com.example.scheduleappserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Page<T> {
  private List<T> planList;
  private int pageNumber;
  private int pageSize;
}
