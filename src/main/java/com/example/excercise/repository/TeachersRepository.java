package com.example.excercise.repository;

import com.example.excercise.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachersRepository extends JpaRepository<TeacherEntity, Integer> {
}
