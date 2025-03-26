package com.example.scheduleappserver.controller;

import com.example.scheduleappserver.dto.*;
import com.example.scheduleappserver.service.ScheduleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/schedules")
public class ScheduleController {
  private final ScheduleService scheduleService;

  public ScheduleController(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  // 일정 추가
  @PostMapping
  public ResponseEntity<ScheduleResponseDto> saveSchedule(@Valid @RequestBody ScheduleRequestDto requestDto) {
    return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
  }

  // 작성자의 name 과 timestamp 로 일정 조회
  @GetMapping
  public ResponseEntity<List<ScheduleShowResponseDto>> findAllSchedule(
          @RequestParam("authorName") String name,
          @RequestParam("updated") String updated
  ) {
    return new ResponseEntity<>(scheduleService.findAllSchedule(name, updated), HttpStatus.OK);
  }

  // 일정의 식별자로 일정 조회
  @GetMapping("/{id}")
  public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
    return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
  }

  // 작성자의 식별자로 일정 조회
  @GetMapping("/authors/{id}")
  public ResponseEntity<List<ScheduleShowResponseDto>> findAllAuthorSchedule(@PathVariable Long id) {
    return new ResponseEntity<>(scheduleService.findAllAuthorSchedule(id), HttpStatus.OK);
  }

  // 일정 수정
  @PatchMapping("/{id}")
  public ResponseEntity<ScheduleResponseDto> editSchedule(
          @PathVariable Long id,
          @Valid @RequestBody ScheduleRequestDto dto
  ) {
    return new ResponseEntity<>(scheduleService.editSchedule(id, dto.getTask(), dto.getAuthorId(), dto.getPwd()), HttpStatus.OK);
  }

  // 일정 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSchedule(
          @PathVariable Long id,
          @Valid @RequestBody ScheduleDeleteRequestDto dto
  ) {
    scheduleService.deleteSchedule(id, dto.getPwd());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  //페이징
  @GetMapping("/page")
  public ResponseEntity<List<PageResponseDto>> page(
          @Min(1) @RequestParam int pageNumber,
          @Min(1) @RequestParam int pageSize
  ) {
    return new ResponseEntity<>(scheduleService.getPlan(pageNumber, pageSize).getPlanList(), HttpStatus.OK);
  }
}
