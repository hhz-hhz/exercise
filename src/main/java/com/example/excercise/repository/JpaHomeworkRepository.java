package com.example.excercise.repository;

import com.example.excercise.entity.HomeworkEntity;
import com.example.excercise.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaHomeworkRepository extends JpaRepository<HomeworkEntity, Integer> {
}
