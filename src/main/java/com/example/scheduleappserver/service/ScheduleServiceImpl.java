package com.example.scheduleappserver.service;

import com.example.scheduleappserver.dto.ScheduleRequestDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.entity.Schedule;
import com.example.scheduleappserver.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
  private final ScheduleRepository scheduleRepository;


  public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
    this.scheduleRepository = scheduleRepository;
  }

  @Override
  public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
    // 요청받은 데이터를 schedule entity 로 변환
    Schedule schedule = new Schedule(dto.getTask(),dto.getAuthor(),dto.getPwd());

    return scheduleRepository.saveSchedule(schedule);
  }

  @Override
  public List<ScheduleResponseDto> findAllSchedule() {
    return scheduleRepository.findAllSchedule();
  }

  @Override
  public ScheduleResponseDto findScheduleById(Long id) {
    Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
    return new ScheduleResponseDto(Schedule);
  }
}
