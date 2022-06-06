package com.example.excercise.mapper;

import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
  StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

  StudentsResponse.StudentResponse toStudentResponse (StudentEntity studentEntity);

  StudentEntity toStudentEntity (StudentsResponse.StudentResponse studentResponse);

  StudentIdResponse toStudentIdResponse(StudentEntity studentEntity);
}
