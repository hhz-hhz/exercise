package com.example.excercise.dto.responce;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentsHomeworkResponse {
  private List<StudentHomeworkResponse> homework;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class StudentHomeworkResponse{
    private Integer id;
    private String content;
  }
}
