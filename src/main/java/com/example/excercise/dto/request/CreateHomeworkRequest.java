package com.example.excercise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHomeworkRequest {
  private String content;
  private Integer classroom_id;
  private Integer teacher_id;
}
