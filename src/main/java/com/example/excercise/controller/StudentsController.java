package com.example.excercise.controller;

import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.dto.request.CreateStudentHomeworkRequest;
import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.request.UpdateHomeworkRequest;
import com.example.excercise.dto.responce.StudentGroupsResponse;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.service.StudentService;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentsController {

  private StudentService studentService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public StudentIdResponse createStudent(@RequestBody CreateStudentRequest createStudentRequest){
    return studentService.createStudent(createStudentRequest);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public StudentsResponse getStudent(@PathVariable Integer id){
    return studentService.findStudentById(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public StudentsResponse getRequiredStudents(@RequestParam(required = false)Map<String, String> query){
    if(query.containsKey("name")){
      return studentService.findStudentsByName(query.get("name"));
    }else{
      return studentService.findAllStudents();
    }
  }

  @PostMapping("/homework/{homeworkId}")
  @ResponseStatus(HttpStatus.OK)
  public String createStudentHomework(@PathVariable Integer homeworkId, @RequestBody CreateStudentHomeworkRequest createStudentHomeworkRequest){
    return "{\nid :" + studentService.submitHomework(homeworkId, createStudentHomeworkRequest)+"\n}";
  }

//  @PutMapping("/{studentId}/homework")
//  @ResponseStatus(HttpStatus.OK)
//  public StudentsResponse updateStudentHomework(@PathVariable Integer studentId, @RequestBody UpdateHomeworkRequest updateHomeworkRequest){
//    return studentService.updateHomework(studentId, updateHomeworkRequest);
//  }
//
//  @GetMapping("/group-by-homework")
//  @ResponseStatus(HttpStatus.OK)
//  public StudentGroupsResponse getRequiredStudentGroups(@RequestParam(required = false)Map<String, String> query){
//    if(query.containsKey("topic")){
//      return studentService.findStudentGroupsByTopic(query.get("topic"));
//    }else{
//      return StudentGroupsResponse.builder().build();
//    }
//  }
}
