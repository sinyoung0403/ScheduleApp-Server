package com.example.scheduleappserver.controller;

import com.example.scheduleappserver.dto.ScheduleRequestDto;
import com.example.scheduleappserver.dto.ScheduleResponseDto;
import com.example.scheduleappserver.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
  private final ScheduleService scheduleService;

  public ScheduleController(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @PostMapping
  public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto requestDto) {
    return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
          @RequestParam("author") String author,
          @RequestParam("updated") String updated
  ) {
    return new ResponseEntity<>(scheduleService.findAllSchedule(author, updated), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
    return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ScheduleResponseDto> editSchedule(
          @PathVariable Long id,
          @RequestBody ScheduleRequestDto dto
  ) {
    // 그래서 이걸 둘다 서비스에 넘겨버리는 거지
    return new ResponseEntity<>(scheduleService.editSchedule(id, dto.getTask(), dto.getAuthor(), dto.getPwd()), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSchedule(
          @PathVariable Long id,
          @RequestBody ScheduleRequestDto dto
  ) {
    // 실제 DB 에 반영하도록 해야됨.
    scheduleService.deleteSchedule(id,dto.getPwd());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
