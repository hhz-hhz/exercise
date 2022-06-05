package com.example.excercise.dto.responcedto;

import com.example.excercise.repository.Student;
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
  private List<Student> data;
}
