package com.example.excercise.service;


import com.example.excercise.dto.requestdto.CreateStudentRequest;
import com.example.excercise.entity.StudentEntity;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.repository.StudentsRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentsService {

  private StudentsRepository studentsRepository;

  public StudentEntity createStudent(CreateStudentRequest createStudentRequest){
    StudentEntity student = StudentEntity.builder()
        .name(createStudentRequest.getName())
        .grade(createStudentRequest.getGrade())
        .classNumber(createStudentRequest.getClassNumber())
        .build();
    return studentsRepository.save(student);
  }

  public StudentEntity findStudentById(Integer id) {
    return studentsRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException(id));
  }

  public List<StudentEntity> findAllStudents(){
    Iterable<StudentEntity> allStudents = studentsRepository.findAll();
    return StreamSupport.stream(allStudents.spliterator(), false)
        .collect(Collectors.toList());
  }
}
