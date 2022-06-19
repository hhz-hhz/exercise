package com.example.excercise.repository;

import com.example.excercise.entity.ClassroomEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomsRepository extends JpaRepository<ClassroomEntity, Integer> {
  Optional<ClassroomEntity> findByGradeAndClassNumber(Integer grade, Integer classNumber);
}
