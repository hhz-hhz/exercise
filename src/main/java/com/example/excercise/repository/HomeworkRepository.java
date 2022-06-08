package com.example.excercise.repository;

import com.example.excercise.entity.HomeworkEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class HomeworkRepository {
  private JpaHomeworkRepository jpaHomeworkRepository;

  public HomeworkEntity save(HomeworkEntity homeworkEntity){
    return jpaHomeworkRepository.save(homeworkEntity);
  }
}
