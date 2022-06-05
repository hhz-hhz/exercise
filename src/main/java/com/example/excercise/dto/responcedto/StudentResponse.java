package com.example.excercise.dto.responcedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
  private Integer id;
  private String name;
  private Integer grade;
  private Integer classNumber;
}
