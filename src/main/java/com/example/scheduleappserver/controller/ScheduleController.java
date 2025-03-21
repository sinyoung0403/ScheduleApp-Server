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


}
