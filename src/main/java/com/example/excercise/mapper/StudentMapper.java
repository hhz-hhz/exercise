package com.example.excercise.mapper;

import com.example.excercise.dto.responce.StudentGroupsResponse;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.entity.StudentEntity;
import org.mapstruct.Mapper;

@Mapper

public interface StudentMapper {
   StudentsResponse.StudentResponse toStudentResponseInStudentsResponse(StudentEntity studentEntity);

  StudentIdResponse toStudentIdResponse(StudentEntity studentEntity);
}
