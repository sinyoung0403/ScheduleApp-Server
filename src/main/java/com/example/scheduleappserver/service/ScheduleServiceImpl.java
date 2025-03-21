package com.example.scheduleappserver.service;

import com.example.scheduleappserver.dto.ScheduleRequestDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.entity.Schedule;
import com.example.scheduleappserver.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    Schedule schedule = new Schedule(dto.getTask(), dto.getAuthor(), dto.getPwd());
    return scheduleRepository.saveSchedule(schedule);
  }

  @Override
  public List<ScheduleResponseDto> findAllSchedule(String author, String updated) {
    return scheduleRepository.findAllSchedule(author, updated);
  }

  @Override
  public ScheduleResponseDto findScheduleById(Long id) {
    Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
    return new ScheduleResponseDto(schedule);
  }

  @Transactional
  @Override
  public ScheduleResponseDto editSchedule(Long id, String task, String author, String pwd) {
    // 제목만 수정을 해주던가. 아니면 ?
    // 둘다 수정을 해주던가 ? 아니면 ?
    // 작성자명만 수정을 해주던가. 인데 ? Set 함수를 잘 써야 돼.
    // 일단은 예외 처리는 이후 레벨 가서 할 거고,
    // Entity 를 받아와서 그걸 Dto 로 변환해주는 게 나을 듯.
    // Update 같은 경우에는 변환된 row 수를 반환해주기 때문에 int 로 받아주어야 함.
    // 1. update 기능을 위한 Dto 를 따로 만들지 ?
    // 2. 그냥 기본 dto 에서 task 와 author 를 받아올지. 근데 내 생각에는 후자가 나을 거 같음 !
    // 먼저 pwd 맞는지 확인
    if (!scheduleRepository.findScheduleByPwd(id, pwd)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not exist id =" + id);
    }

    int updatedRow = scheduleRepository.editSchedule(id, task, author, pwd);
    if (updatedRow == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id =" + id);
    }

    // 만약 업데이트가 되었다면, 해당 id 로 조회되었을 때 정상적인 조회가 이루어져야 함 !
    Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

    return new ScheduleResponseDto(schedule);
  }

  @Transactional
  @Override
  public void deleteSchedule(Long id, String pwd) {
    // 비밀번호 검증을 하고, 삭제가 가능하게 !
    if (!scheduleRepository.findScheduleByPwd(id, pwd)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not exist id =" + id);
    }

    scheduleRepository.deleteSchedule(id);
  }


}
