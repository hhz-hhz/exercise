package com.example.excercise.controller;

import com.example.excercise.dto.request.CreateClassroomRequest;
import com.example.excercise.service.ClassroomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/classrooms")
public class ClassroomsController {
  private ClassroomService classroomService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String createClassroom(@RequestBody CreateClassroomRequest createClassroomRequest){
    return "id: " + classroomService.createClassroom(createClassroomRequest);
  }

}
