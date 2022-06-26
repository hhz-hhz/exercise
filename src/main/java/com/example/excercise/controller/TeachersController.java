package com.example.excercise.controller;

import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.dto.responce.StudentsHomeworkResponse;
import com.example.excercise.service.TeacherService;
import java.sql.Date;
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
@RequestMapping("/teachers")
public class TeachersController {

  private TeacherService teacherService;

  @PostMapping("/homework")
  @ResponseStatus(HttpStatus.OK)
  public String createHomework(@RequestBody CreateHomeworkRequest createHomeworkRequest){
    return "id: " + teacherService.createHomework(createHomeworkRequest);
  }

  @GetMapping("/{id}/homework")
  @ResponseStatus(HttpStatus.OK)
  public StudentsHomeworkResponse getStudentHomework(@PathVariable Integer id,  @RequestParam(required = false) Map<String, String> query){
    Integer grade = null;
    Integer clazz = null;
    if(query.containsKey("grade")){
      grade = Integer.valueOf(query.get("grade"));
    }
    if(query.containsKey("clazz")){
      clazz = Integer.valueOf(query.get("clazz"));
    }

    Date created_at = null;
    if(query.containsKey("created_at")){
      created_at = Date.valueOf(query.get("created_at"));
    }
    if(grade != null && clazz != null && created_at != null){
      return teacherService.getStudentsHomework(id, grade, clazz, created_at);
    }
    return StudentsHomeworkResponse.builder().build();
  }
}
