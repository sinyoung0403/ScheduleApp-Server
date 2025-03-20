package com.example.scheduleappserver.repository;

import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Schedule schedule);

  List<ScheduleResponseDto> findAllSchedule();

  Schedule findScheduleByIdOrElseThrow(Long id);
}