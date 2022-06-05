package com.example.excercise.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.example.excercise.dto.requestdto.CreateStudentRequest;
import com.example.excercise.repository.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentsServiceTest {

  @InjectMocks
  private StudentsService studentsService;

  @Test
  void should_return_student_when_create_a_student() {
    CreateStudentRequest student = new CreateStudentRequest("jack", 1, 8);

    Student actualStudent = studentsService.createStudent(student);

    assertThat(actualStudent.getName(), is("jack"));
    assertThat(actualStudent.getGrade(), is(1));
    assertThat(actualStudent.getClassNumber(), is(8));
  }
}