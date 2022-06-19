package com.example.excercise.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.excercise.entity.HomeworkEntity;
import com.example.excercise.entity.TeacherEntity;
import com.example.excercise.repository.TeachersRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TeacherServiceTest {
  @Mock
  private TeachersRepository teachersRepository;
  
  @InjectMocks
  private TeacherService teacherService;

  @Test
  void should_return_homework_id_when_create_homework_successfully() {
//    ArgumentCaptor<TeacherEntity> captor = ArgumentCaptor.forClass(TeacherEntity.class);
//    when(teachersRepository.save(captor.capture()))
//        .thenReturn(TeacherEntity.builder()
//            .homework(List.of(HomeworkEntity
//                .builder()
//                .id(1)
//                .content("homework")
//                    .
//                    .created_at("nana")
//                .build()))
//            .build());

  }
}