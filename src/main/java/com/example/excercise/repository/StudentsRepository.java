package com.example.excercise.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StudentsRepository {
  private final JpaStudentRepository jpaStudentRepository;

  public Student save(Student student){
    return jpaStudentRepository.save(student);
  }
}
