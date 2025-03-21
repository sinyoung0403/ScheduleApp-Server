package com.example.scheduleappserver.repository;

import com.example.scheduleappserver.dto.ScheduleRequestDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Schedule schedule);

  List<ScheduleResponseDto> findAllSchedule(String author, String updated);

  Schedule findScheduleByIdOrElseThrow(Long id);

  int editSchedule(Long id, String task, String author, String pwd);

  boolean findScheduleByPwd(Long id, String pwd);

  void deleteSchedule(Long id);
}