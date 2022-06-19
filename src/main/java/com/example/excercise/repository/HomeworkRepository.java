package com.example.excercise.repository;

import com.example.excercise.entity.HomeworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends JpaRepository<HomeworkEntity, Integer> {
}
