package com.example.excercise.service;


import com.example.excercise.dto.requestdto.CreateStudentRequest;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.repository.Student;
import com.example.excercise.repository.StudentsRepository;
import java.util.ArrayList;
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

  public Student createStudent(CreateStudentRequest createStudentRequest){
    Student student = Student.builder()
        .name(createStudentRequest.getName())
        .grade(createStudentRequest.getGrade())
        .classNumber(createStudentRequest.getClassNumber())
        .build();
    return studentsRepository.save(student);
  }

  public Student findStudentById(Integer id) {
    Optional<Student> student = studentsRepository.findById(id);
    return student.orElseThrow(() -> new StudentNotFoundException(id));
  }

  public List<Student> findAllStudents(){
    Iterable<Student> allStudents = studentsRepository.findAll();
    return StreamSupport.stream(allStudents.spliterator(), false)
        .collect(Collectors.toList());
  }
}
