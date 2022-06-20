package com.example.excercise.mapper;

import com.example.excercise.dto.responce.StudentsHomeworkResponse;
import com.example.excercise.entity.StudentHomeworkEntity;
import org.mapstruct.Mapper;

@Mapper

public interface StudentHomeworkMapper {
  StudentsHomeworkResponse.StudentHomeworkResponse toHomeworkResponse(StudentHomeworkEntity homework);
}
