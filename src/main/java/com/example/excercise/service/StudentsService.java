package com.example.excercise.service;


import com.example.excercise.dto.requestdto.CreateStudentRequest;
import com.example.excercise.repository.Student;
import com.example.excercise.repository.StudentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentsService {

  private StudentsRepository studentsRepository;

  public Student createStudent(CreateStudentRequest createStudentRequest){
    Student student = Student.builder()
        .name(createStudentRequest.getName())
        .grade(createStudentRequest.getGrade())
        .classNumber(createStudentRequest.getClassNumber())
        .build();
    return studentsRepository.save(student);
  }
}
