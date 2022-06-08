package com.example.excercise.controller;

import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.service.StudentsService;
import java.util.Map;
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
  public StudentsResponse getRequiredStudents(@RequestParam(required = false)Map<String, String> query){
    if(query.containsKey("name")){
      return studentsService.findStudentsByName(query.get("name"));
    }else{
      return studentsService.findAllStudents();
    }
  }

  @PostMapping("/{studentId}/homework")
  @ResponseStatus(HttpStatus.OK)
  public String createStudentHomework(@PathVariable Integer studentId, @RequestBody CreateHomeworkRequest createHomeworkRequest){
    return "id :" + studentsService.submitHomework(studentId, createHomeworkRequest);
  }


}
