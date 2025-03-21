package com.example.scheduleappserver.service;

import com.example.scheduleappserver.dto.ScheduleRequestDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
  ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

  List<ScheduleResponseDto> findAllSchedule(String author, String Updated);

  ScheduleResponseDto findScheduleById(Long id);

  ScheduleResponseDto editSchedule(Long id, String task, String author, String pwd);

  void deleteSchedule(Long id, String pwd);
}
