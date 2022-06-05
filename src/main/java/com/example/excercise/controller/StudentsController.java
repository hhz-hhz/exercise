package com.example.excercise.controller;

import com.example.excercise.dto.requestdto.CreateStudentRequest;
import com.example.excercise.dto.responcedto.StudentIdResponse;
import com.example.excercise.dto.responcedto.StudentsResponse;
import com.example.excercise.repository.Student;
import com.example.excercise.service.StudentsService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentsController {

  private StudentsService studentsService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public StudentIdResponse createStudent(@Valid @RequestBody CreateStudentRequest createStudentRequest){
    Student student = studentsService.createStudent(createStudentRequest);
    return StudentIdResponse.builder()
        .id(student.getId())
        .build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getStudent(@PathVariable Integer id){
    Optional<Student> studentById = studentsService.findStudentById(id);
    if(studentById.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with id: "+id);
    }
    return ResponseEntity.ok(StudentsResponse.builder()
            .data(List.of(studentById.get()))
            .build());
  }

  @GetMapping
  public ResponseEntity<StudentsResponse> getAllStudent(){
    List<Student> allStudents = studentsService.findAllStudents();
    return ResponseEntity.ok(StudentsResponse.builder()
            .data(allStudents)
        .build());
  }
}
