package com.example.scheduleappserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthorRequestDto {
  @NotBlank(message = "필수 입력 값입니다.")
  private String name;

  @Email(message = "이메일 형식으로 입력하셔야 합니다.")
  private String email;
}
