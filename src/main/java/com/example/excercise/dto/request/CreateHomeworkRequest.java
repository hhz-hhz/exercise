package com.example.excercise.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHomeworkRequest {
  private List<Integer> student;
  private String topic;
  private String content;
}
