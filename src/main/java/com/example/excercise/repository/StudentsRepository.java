package com.example.excercise.repository;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StudentsRepository {
  private final JpaStudentRepository jpaStudentRepository;

  public Student save(Student student){
    return jpaStudentRepository.save(student);
  }

  public Optional<Student> findById(Integer id) {return jpaStudentRepository.findById(id);}
}
