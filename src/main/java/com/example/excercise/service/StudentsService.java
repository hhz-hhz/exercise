package com.example.excercise.service;


import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.entity.StudentEntity;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.mapper.StudentMapper;
import com.example.excercise.repository.StudentsRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentsService {

  private StudentsRepository studentsRepository;

  public StudentIdResponse createStudent(CreateStudentRequest createStudentRequest){
    StudentEntity student = StudentEntity.builder()
        .name(createStudentRequest.getName())
        .grade(createStudentRequest.getGrade())
        .classNumber(createStudentRequest.getClassNumber())
        .build();
    return StudentMapper.INSTANCE
        .toStudentIdResponse(studentsRepository.save(student));
  }

  public StudentsResponse findStudentById(Integer id) {
    StudentEntity studentEntity = studentsRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException(id));
    return StudentsResponse.builder()
        .data(List.of(StudentMapper
            .INSTANCE
            .toStudentResponse(studentEntity)))
        .build();
  }

  public StudentsResponse findAllStudents(){
    List<StudentEntity> allStudents = studentsRepository.findAll();
    return StudentsResponse.builder()
        .data(allStudents.stream()
            .map(StudentMapper.INSTANCE::toStudentResponse)
            .collect(Collectors.toList()))
        .build();
  }
}
