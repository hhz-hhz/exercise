package com.example.excercise.dto.requestdto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequestBody {

  @NotBlank(message = "Name is required")
  private String name;

  @Min(value = 1, message = "Grade should not be less than 1")
  @Max(value = 9, message = "Grade should not be more than 9")
  private int grade;

  @Min(value = 1, message = "classNumber should not be less than 1")
  @Max(value = 20, message = "classNumber should not be more than 20")
  private int classNumber;
}