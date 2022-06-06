package com.example.excercise.service;


import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.entity.StudentEntity;
import com.example.excercise.exception.ClassNumberNotValidatedException;
import com.example.excercise.exception.GradeNotValidatedException;
import com.example.excercise.exception.NameIsNullException;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.mapper.StudentMapper;
import com.example.excercise.repository.StudentsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentsService {

  private StudentsRepository studentsRepository;

  public StudentIdResponse createStudent(CreateStudentRequest createStudentRequest){
    validateStudentRequest(createStudentRequest);
    StudentEntity student = StudentEntity.builder()
        .name(createStudentRequest.getName())
        .grade(createStudentRequest.getGrade())
        .classNumber(createStudentRequest.getClassNumber())
        .build();
    return StudentMapper.INSTANCE
        .toStudentIdResponse(studentsRepository.save(student));
  }

  private void validateStudentRequest(CreateStudentRequest createStudentRequest){
    if(createStudentRequest.getName().isBlank()){
      throw new NameIsNullException();
    }
    if(createStudentRequest.getGrade() < 1 || createStudentRequest.getGrade() > 9){
      throw new GradeNotValidatedException();
    }
    if(createStudentRequest.getClassNumber() < 1 || createStudentRequest.getClassNumber() > 20){
      throw new ClassNumberNotValidatedException();
    }
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
