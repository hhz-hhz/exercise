package com.example.excercise.service;

import com.example.excercise.dto.request.CreateClassroomRequest;
import com.example.excercise.entity.ClassroomEntity;
import com.example.excercise.exception.ClassNumberNotValidatedException;
import com.example.excercise.exception.GradeNotValidatedException;
import com.example.excercise.repository.ClassroomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassroomService {
  private final ClassroomsRepository classroomsRepository;

  public Integer createClassroom(CreateClassroomRequest createClassroomRequest) {
    validateCreateClassroomRequest(createClassroomRequest);
    return classroomsRepository.save(ClassroomEntity.builder()
            .grade(createClassroomRequest.getGrade())
            .classNumber(createClassroomRequest.getClassNumber())
        .build()).getId();
  }

  private void validateCreateClassroomRequest(CreateClassroomRequest createClassroomRequest){
    if(createClassroomRequest.getGrade() < 1 || createClassroomRequest.getGrade() > 9){
      throw new GradeNotValidatedException();
    }
    if(createClassroomRequest.getClassNumber() < 1 || createClassroomRequest.getClassNumber() > 20){
      throw new ClassNumberNotValidatedException();
    }
  }
}