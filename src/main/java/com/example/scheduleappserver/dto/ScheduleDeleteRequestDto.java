package com.example.scheduleappserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleDeleteRequestDto {
  @NotBlank(message = "비밀번호는 필수값 입니다.")
  private String pwd;
}
