package com.example.excercise.repository;

import com.example.excercise.entity.StudentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaRepository<StudentEntity, Integer> {
  List<StudentEntity> findAllByName(String name);
}
