package com.example.excercise.controller;

import com.example.excercise.dto.requestdto.CreateStudentRequestBody;
import javax.validation.Valid;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class StudentsController {

  @GetMapping("/hellos")
  public String hellos(){
    return "hello spring";
  }

  @PostMapping
  public ResponseEntity<String> createStudent(@Valid @RequestBody CreateStudentRequestBody createStudentRequestBody){
      return ResponseEntity.status(HttpStatus.CREATED).body(createStudentRequestBody.getName());
  }
}
