package com.example.excercise.dto.responcedto;

import com.example.excercise.entity.StudentEntity;
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
  private List<StudentEntity> data;
}
