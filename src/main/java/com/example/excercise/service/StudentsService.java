package com.example.excercise.service;


import com.example.excercise.dto.requestdto.CreateStudentRequest;
import com.example.excercise.repository.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentsService {

  public Student createStudent(CreateStudentRequest createStudentRequest){
    Student student = Student.builder()
        .name(createStudentRequest.getName())
        .grade(createStudentRequest.getGrade())
        .classNumber(createStudentRequest.getClassNumber())
        .build();
    return student;
  }
}
