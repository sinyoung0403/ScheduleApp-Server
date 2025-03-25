package com.example.scheduleappserver.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
  @NotBlank(message = "할일은 필수값 입니다.")
  @Size(max = 200, message = "할일은 200글자 이내로 제한됩니다.")
  private String task;

  @NotNull(message = "작성자의 id 값은 필수값 입니다.")
  private Long authorId;

  @NotBlank(message = "비밀번호는 필수값 입니다.")
  private String pwd;
}
