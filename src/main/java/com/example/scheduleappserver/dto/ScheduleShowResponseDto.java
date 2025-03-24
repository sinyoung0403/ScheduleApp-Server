package com.example.scheduleappserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleShowResponseDto {
  // 우리가 뭘 찾을 때를 생각해보자.
  // 이거는 그냥 작성자 id 값이랑 일자만 가져오는데 ? ;;
  private Long id;
  private String task;
  private Long authorId;
  private String email;
  private String created;
  private String updated;
}
