package com.example.excercise.controller;

import com.example.excercise.dto.requestdto.CreateStudentRequest;
import com.example.excercise.repository.Student;
import com.example.excercise.service.StudentsService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@AllArgsConstructor
public class StudentsController {

  private StudentsService studentsService;

  @PostMapping
  public ResponseEntity<Integer> createStudent(@Valid @RequestBody CreateStudentRequest createStudentRequest){
    Student student = studentsService.createStudent(createStudentRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(student.getId());
  }
}
