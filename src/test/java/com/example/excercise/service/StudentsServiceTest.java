package com.example.excercise.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.excercise.dto.requestdto.CreateStudentRequest;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.repository.Student;
import com.example.excercise.repository.StudentsRepository;
import org.junit.jupiter.api.function.Executable;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentsServiceTest {

  @InjectMocks
  private StudentsService studentsService;

  @Mock
  private StudentsRepository studentsRepository;

  private final CreateStudentRequest studentRequest = new CreateStudentRequest("jack", 1, 8);
  private final Student student = Student.builder()
      .id(9)
      .name(studentRequest.getName())
      .grade(studentRequest.getGrade())
      .classNumber(studentRequest.getClassNumber())
      .build();
  @Test
  void should_return_student_when_create_a_student() {
    ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
    when(studentsRepository.save(captor.capture())).thenReturn(student);

    Student actualStudent = studentsService.createStudent(studentRequest);

    Student argument = captor.getValue();
    assertThat(argument.getName(), is("jack"));
    assertThat(argument.getGrade(), is(1));
    assertThat(argument.getClassNumber(), is(8));
    assertThat(actualStudent.getId(), is(9));
  }

  @Test
  void should_return_student_when_find_exist_student() {
    when(studentsRepository.findById(9)).thenReturn(Optional.ofNullable(student));
    Student studentById = studentsService.findStudentById(9);
    assertThat(studentById.getName(), is("jack"));
    assertThat(studentById.getGrade(), is(1));
    assertThat(studentById.getClassNumber(), is(8));
    assertThat(studentById.getId(), is(9));
  }

  @Test
  void should_return_student_when_can_not_find_student() {
    when(studentsRepository.findById(10)).thenReturn(Optional.empty());

    Executable executable = () -> studentsService.findStudentById(10);
    Exception exception = assertThrows(StudentNotFoundException.class, executable);
    assertThat(exception.getMessage(), is("Student not found with id: "+10));
  }
}