package com.example.excercise.controller;

import com.example.excercise.dto.requestdto.CreateStudentRequest;
import com.example.excercise.service.StudentsService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
public class StudentsController {

  private StudentsService studentsService;

  @PostMapping
  public ResponseEntity<String> createStudent(@Valid @RequestBody CreateStudentRequest createStudentRequest){

    return ResponseEntity.status(HttpStatus.CREATED).body(createStudentRequest.getName());
  }
}
