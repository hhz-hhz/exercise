package com.example.excercise.controller;

import com.example.excercise.dto.request.CreateHomeworkRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/teachers")
public class TeachersController {



  @PostMapping("/homework")
  @ResponseStatus(HttpStatus.OK)
  public String createHomework(@RequestBody CreateHomeworkRequest createHomeworkRequest){
    return null;
  }
}
