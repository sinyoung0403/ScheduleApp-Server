package com.example.scheduleappserver.service;

import com.example.scheduleappserver.dto.PageResponseDto;
import com.example.scheduleappserver.dto.ScheduleRequestDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.dto.ScheduleShowResponseDto;
import com.example.scheduleappserver.entity.Page;

import java.util.List;

public interface ScheduleService {
  ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

  List<ScheduleShowResponseDto> findAllSchedule(String name, String Updated);

  List<ScheduleShowResponseDto> findAllAuthorSchedule(Long authorId);

  ScheduleResponseDto findScheduleById(Long id);

  ScheduleResponseDto editSchedule(Long id, String task, Long authorId, String pwd);

  void deleteSchedule(Long id, String pwd);

  Page<PageResponseDto> getPlan(int pageNumber, int pageSize);
}
