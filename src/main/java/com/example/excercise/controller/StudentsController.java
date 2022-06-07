package com.example.excercise.controller;

import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.service.StudentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentsController {

  private StudentsService studentsService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public StudentIdResponse createStudent(@RequestBody CreateStudentRequest createStudentRequest){
    return studentsService.createStudent(createStudentRequest);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public StudentsResponse getStudent(@PathVariable Integer id){
    return studentsService.findStudentById(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public StudentsResponse getRequiredStudents(@RequestParam(required = false, value = "name") String name){
    if(name != null){
      return studentsService.findStudentsByName(name);
    }else{
      return studentsService.findAllStudents();
    }
  }
  
}
