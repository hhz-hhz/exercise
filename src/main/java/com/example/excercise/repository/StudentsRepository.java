package com.example.excercise.repository;

import com.example.excercise.entity.StudentEntity;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StudentsRepository {
  private final JpaStudentRepository jpaStudentRepository;

  public StudentEntity save(StudentEntity student){
    return jpaStudentRepository.save(student);
  }

  public Optional<StudentEntity> findById(Integer id) {return jpaStudentRepository.findById(id);}

  public Iterable<StudentEntity> findAll(){
    return jpaStudentRepository.findAll();
  }
}
