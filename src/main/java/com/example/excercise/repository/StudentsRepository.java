package com.example.excercise.repository;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StudentsRepository {
  private final JpaStudentRepository jpaStudentRepository;

  public Student save(Student student){
    return jpaStudentRepository.save(student);
  }
}
