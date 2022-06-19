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
public class StudentsResponse {
  private List<StudentResponse> data;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class StudentResponse{
    private Integer id;
    private String name;
    private ClassroomResponse classroom;
    private List<HomeworkResponse> homework;
  }
}
