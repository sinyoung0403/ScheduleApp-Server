package com.example.scheduleappserver.service;

import com.example.scheduleappserver.dto.PageResponseDto;
import com.example.scheduleappserver.dto.ScheduleRequestDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.dto.ScheduleShowResponseDto;
import com.example.scheduleappserver.entity.Page;
import com.example.scheduleappserver.entity.Plan;
import com.example.scheduleappserver.exception.DataNotFoundException;
import com.example.scheduleappserver.exception.InvalidInputException;
import com.example.scheduleappserver.exception.InvalidPasswordException;
import com.example.scheduleappserver.repository.AuthorRepository;
import com.example.scheduleappserver.repository.ScheduleRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
  private final ScheduleRepository scheduleRepository;
  private final AuthorRepository authorRepository;


  public ScheduleServiceImpl(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
    this.scheduleRepository = scheduleRepository;
    this.authorRepository = authorRepository;
  }

  // 일정 추가
  @Override
  public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
    // 1. 요청받은 데이터를 schedule entity 로 변환
    Plan schedule = new Plan(dto.getTask(), dto.getAuthorId(), dto.getPwd());

    // 2. 먼저 실제 id 값이 있는지 Author Table 에서 조회
    if (!authorRepository.findAuthorByIdIsEmpty(dto.getAuthorId())) {
      throw new DataNotFoundException("해당 작성자가 존재하지 않습니다. id : " + dto.getAuthorId());
    }

    // 3. Entity 를 DB 에 저장
    return scheduleRepository.saveSchedule(schedule);
  }

  // 작성자의 name 과 timestamp 로 일정 조회
  @Override
  public List<ScheduleShowResponseDto> findAllSchedule(String name, String updated) {
    if (StringUtils.isEmpty(name) && StringUtils.isEmpty(updated)) {
      throw new InvalidInputException("name 과 updated 중 하나의 값은 입력하셔야 합니다.");
    }
    return scheduleRepository.findAllSchedule(name, updated);
  }

  // 일정의 식별자로 조회
  @Override
  public ScheduleResponseDto findScheduleById(Long id) {
    Plan schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
    return new ScheduleResponseDto(schedule);
  }

  // 작성자의 식별자로 일정 조회
  @Override
  public List<ScheduleShowResponseDto> findAllAuthorSchedule(Long authorId) {
    // 작성자의 id 가 실제 author Table 에 존재하는지 확인
    if (!authorRepository.findAuthorByIdIsEmpty(authorId)) {
      throw new DataNotFoundException("작성자의 아이디가 존재하지 않습니다. id : " + authorId);
    }

    return scheduleRepository.findAllAuthorSchedule(authorId);
  }

  // 일정 수정
  @Transactional
  @Override
  public ScheduleResponseDto editSchedule(Long id, String task, Long authorId, String pwd) {
    // 1. 작성자 id 가 실제 author Table 에 존재하는지 확인
    if (!authorRepository.findAuthorByIdIsEmpty(authorId)) {
      throw new DataNotFoundException("작성자의 아이디가 존재하지 않습니다. id : " + authorId);
    }

    // 2. Edit 진행. Edit 값이 양수라면, id 존재하는 것.
    int row = scheduleRepository.editSchedule(id, task, authorId);
    if (row == 0) {
      throw new DataNotFoundException("일정의 id 가 존재하지 않습니다.");
    }

    // 3. pwd 올바른지 아닌지 확인, 틀렸을 경우 트랜잭션에 의해 수정 작업 롤백
    if (!scheduleRepository.findScheduleByPwd(id, pwd)) {
      throw new InvalidPasswordException("해당 일정의 비밀번호가 틀렸습니다. id : " + id);
    }

    // 4. 조회할 시 없으면, 트랜잭션에 의해 수정 작업 롤백
    return new ScheduleResponseDto(scheduleRepository.findScheduleByIdOrElseThrow(id));
  }

  // 일정 삭제
  @Override
  public void deleteSchedule(Long id, String pwd) {
    // 1. id 값 존재하지 않을 경우
    scheduleRepository.findScheduleByIdOrElseThrow(id);

    // 2. pwd 가 틀렸을 경우
    if (!scheduleRepository.findScheduleByPwd(id, pwd)) {
      throw new InvalidPasswordException("비밀번호를 틀렸습니다.");
    }
    scheduleRepository.deleteSchedule(id);
  }

  // 일정 페이징 조회
  public Page<PageResponseDto> getPlan(int pageNumber, int pageSize) {
    return scheduleRepository.getPlan(pageNumber, pageSize);
  }
}
