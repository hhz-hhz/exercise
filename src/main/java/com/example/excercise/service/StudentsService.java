package com.example.excercise.service;


import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.entity.HomeworkEntity;
import com.example.excercise.entity.StudentEntity;
import com.example.excercise.exception.ClassNumberNotValidatedException;
import com.example.excercise.exception.GradeNotValidatedException;
import com.example.excercise.exception.NameIsNullException;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.mapper.StudentMapper;
import com.example.excercise.repository.StudentsRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentsService {


  private final StudentsRepository studentsRepository;

  private final StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);

  public StudentIdResponse createStudent(CreateStudentRequest createStudentRequest){
    validateStudentRequest(createStudentRequest);
    StudentEntity student = StudentEntity.builder()
        .name(createStudentRequest.getName())
        .grade(createStudentRequest.getGrade())
        .classNumber(createStudentRequest.getClassNumber())
        .build();
    return studentMapper.toStudentIdResponse(studentsRepository.save(student));
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
        .data(List.of(studentMapper.toStudentResponse(studentEntity)))
        .build();
  }

  public StudentsResponse findAllStudents(){
    List<StudentEntity> allStudents = studentsRepository.findAll();
    return StudentsResponse.builder()
        .data(allStudents.stream()
            .map(studentMapper::toStudentResponse)
            .collect(Collectors.toList()))
        .build();
  }

  public StudentsResponse findStudentsByName(String name) {
    List<StudentEntity> requiredStudents = studentsRepository.findAllByName(name);
    return StudentsResponse.builder()
        .data(requiredStudents.stream()
            .map(studentMapper::toStudentResponse)
            .collect(Collectors.toList()))
        .build();
  }

  public Integer submitHomework(Integer studentId, CreateHomeworkRequest createHomeworkRequest) {
    StudentEntity student = studentsRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    HomeworkEntity homeworkEntity = HomeworkEntity.builder()
        .content(createHomeworkRequest.getContent())
        .build();
    return student.addHomework(homeworkEntity);
  }
}
