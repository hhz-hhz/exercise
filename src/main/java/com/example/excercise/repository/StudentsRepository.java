package com.example.excercise.repository;

import com.example.excercise.entity.StudentEntity;
import java.util.List;
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

  public List<StudentEntity> findAll(){
    return jpaStudentRepository.findAll();
  }

  public boolean existById(Integer id) {
    return jpaStudentRepository.existsById(id);
  }
  public List<StudentEntity> findAllByName(String name) {
    return jpaStudentRepository.findAllByName(name);
  }
}
