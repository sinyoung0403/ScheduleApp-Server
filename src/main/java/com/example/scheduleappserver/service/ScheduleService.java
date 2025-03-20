package com.example.scheduleappserver.service;

import com.example.scheduleappserver.dto.ScheduleRequestDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
  ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

  List<ScheduleResponseDto> findAllSchedule();

  ScheduleResponseDto findScheduleById(Long id);
}
