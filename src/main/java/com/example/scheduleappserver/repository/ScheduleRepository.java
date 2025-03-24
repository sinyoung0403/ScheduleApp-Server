package com.example.scheduleappserver.repository;

import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.dto.ScheduleShowResponseDto;
import com.example.scheduleappserver.entity.Plan;

import java.util.List;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Plan schedule);

  List<ScheduleShowResponseDto> findAllSchedule(String name, String updated);

  Plan findScheduleByIdOrElseThrow(Long id);

  int editSchedule(Long id, String task, Long authorId);

  boolean findScheduleByPwd(Long id, String pwd);

  void deleteSchedule(Long id);

  List<ScheduleShowResponseDto> findAllAuthorSchedule(Long authorId);
}