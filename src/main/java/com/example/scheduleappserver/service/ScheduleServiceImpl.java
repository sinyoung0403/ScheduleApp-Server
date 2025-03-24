package com.example.scheduleappserver.service;

import com.example.scheduleappserver.dto.ScheduleRequestDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.dto.ScheduleShowResponseDto;
import com.example.scheduleappserver.entity.Plan;
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
    // 요청받은 데이터를 schedule entity 로 변환
    Plan schedule = new Plan(dto.getTask(), dto.getAuthorId(), dto.getPwd());

    // 먼저 실제 id 값이 있는지 Author 에서 조회
    if (!authorRepository.findAuthorByIdIsEmpty(dto.getAuthorId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id =" + dto.getAuthorId());
    }

    // 그런 후 save 해주면 됨
    return scheduleRepository.saveSchedule(schedule);
  }

  // 작성자의 name 과 timestamp 로 일정 조회
  @Override
  public List<ScheduleShowResponseDto> findAllSchedule(String name, String updated) {
    return scheduleRepository.findAllSchedule(name, updated);
  }

  // 작성자의 식별자로 일정 조회
  @Override
  public List<ScheduleShowResponseDto> findAllAuthorSchedule(Long authorId) {
    return scheduleRepository.findAllAuthorSchedule(authorId);
  }

  // 일정의 식별자로 조회
  @Override
  public ScheduleResponseDto findScheduleById(Long id) {
    Plan schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
    return new ScheduleResponseDto(schedule);
  }

  @Transactional
  @Override
  public ScheduleResponseDto editSchedule(Long id, String task, String author, String pwd) {
    // Entity 라는 객체로 전달 받는 걸 고려. 근데 이걸 그대로 ?  > 챌린지 가져가면 될듯.

    // 제목만 수정을 해주던가. 아니면 ?
    // 둘다 수정을 해주던가 ? 아니면 ?
    // 작성자명만 수정을 해주던가. 인데 ? Set 함수를 잘 써야 돼.
    // 일단은 예외 처리는 이후 레벨 가서 할 거고,
    // Entity 를 받아와서 그걸 Dto 로 변환해주는 게 나을 듯.
    // Update 같은 경우에는 변환된 row 수를 반환해주기 때문에 int 로 받아주어야 함.
    // 1. update 기능을 위한 Dto 를 따로 만들지 ?
    // 2. 그냥 기본 dto 에서 task 와 author 를 받아올지. 근데 내 생각에는 후자가 나을 거 같음 !
    // 먼저 pwd 맞는지 확인
    if (pwd.isEmpty() || (StringUtils.isEmpty(task) && StringUtils.isEmpty(author))) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    // id 가 실제 DB 있는지 없는지 확인 = > 있으면 계속 진행. / 없으면 Notfound
    scheduleRepository.findScheduleByIdOrElseThrow(id);

    // pwd 올바른지 아닌지 확인
    if (!scheduleRepository.findScheduleByPwd(id, pwd)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not exist id =" + id);
    }

    scheduleRepository.editSchedule(id, task, author);

    return new ScheduleResponseDto(scheduleRepository.findScheduleByIdOrElseThrow(id));
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
