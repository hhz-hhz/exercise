package com.example.excercise.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.entity.StudentEntity;
import com.example.excercise.exception.ClassNumberNotValidatedException;
import com.example.excercise.exception.GradeNotValidatedException;
import com.example.excercise.exception.NameIsNullException;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.repository.StudentsRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
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

  private final CreateStudentRequest studentRequest = CreateStudentRequest.builder()
      .name("jack")
      .grade(1)
      .classNumber(8)
      .build();
  private final StudentEntity student = StudentEntity.builder()
      .id(9)
      .name(studentRequest.getName())
      .grade(studentRequest.getGrade())
      .classNumber(studentRequest.getClassNumber())
      .build();

  @Test
  void should_return_student_when_create_a_student() {
    ArgumentCaptor<StudentEntity> captor = ArgumentCaptor.forClass(StudentEntity.class);
    when(studentsRepository.save(captor.capture())).thenReturn(student);

    StudentIdResponse actualStudent = studentsService.createStudent(studentRequest);

    StudentEntity argument = captor.getValue();
    assertThat(argument.getName(), is("jack"));
    assertThat(argument.getGrade(), is(1));
    assertThat(argument.getClassNumber(), is(8));
    assertThat(actualStudent.getId(), is(9));
  }

  @Test
  void should_throw_name_null_exception_when_name_is_null_in_request() {
    CreateStudentRequest studentNameNull = CreateStudentRequest.builder()
        .name("")
        .grade(1)
        .classNumber(1)
        .build();

    Executable executable = () -> studentsService.createStudent(studentNameNull);
    Exception exception = assertThrows(NameIsNullException.class, executable);

    assertThat(exception.getMessage(), is("Name is required"));
  }

  @Test
  void should_throw_grade_not_validated_exception_when_garde_is_bigger_than_9() {
    CreateStudentRequest student = CreateStudentRequest.builder()
        .name("nana")
        .grade(100)
        .classNumber(1)
        .build();

    Executable executable = () -> studentsService.createStudent(student);
    Exception exception = assertThrows(GradeNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Grade should be on a scale of 1 to 9"));
  }

  @Test
  void should_throw_grade_not_validated_exception_when_garde_is_smaller_than_1() {
    CreateStudentRequest student = CreateStudentRequest.builder()
        .name("nana")
        .grade(0)
        .classNumber(1)
        .build();

    Executable executable = () -> studentsService.createStudent(student);
    Exception exception = assertThrows(GradeNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Grade should be on a scale of 1 to 9"));
  }

  @Test
  void should_throw_class_number_not_validated_exception_when_class_is_bigger_than_20() {
    CreateStudentRequest student = CreateStudentRequest.builder()
        .name("nana")
        .grade(1)
        .classNumber(200)
        .build();

    Executable executable = () -> studentsService.createStudent(student);
    Exception exception = assertThrows(ClassNumberNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Class number should be on a scale of 1 to 20"));
  }

  @Test
  void should_throw_class_number_not_validated_exception_when_class_is_small_than_1() {
    CreateStudentRequest student = CreateStudentRequest.builder()
        .name("nana")
        .grade(1)
        .classNumber(0)
        .build();

    Executable executable = () -> studentsService.createStudent(student);
    Exception exception = assertThrows(ClassNumberNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Class number should be on a scale of 1 to 20"));
  }

  @Test
  void should_return_student_when_find_exist_student() {
    when(studentsRepository.findById(9)).thenReturn(Optional.ofNullable(student));

    StudentsResponse.StudentResponse studentById = studentsService.findStudentById(9).getData().get(0);

    assertThat(studentById.getName(), is("jack"));
    assertThat(studentById.getGrade(), is(1));
    assertThat(studentById.getClassNumber(), is(8));
    assertThat(studentById.getId(), is(9));
  }

  @Test
  void should_throw_exception_when_can_not_find_student() {
    when(studentsRepository.findById(10)).thenReturn(Optional.empty());

    Executable executable = () -> studentsService.findStudentById(10);
    Exception exception = assertThrows(StudentNotFoundException.class, executable);

    assertThat(exception.getMessage(), is("Student not found with id: "+10));
  }

  @Test
  void should_return_2_students_when_have_2_student() {
    StudentEntity student1 = StudentEntity.builder()
        .id(1)
        .name("nana")
        .grade(2)
        .classNumber(3)
        .build();
    StudentEntity student2 = StudentEntity.builder()
        .id(2)
        .name("po")
        .grade(3)
        .classNumber(3)
        .build();
    when(studentsRepository.findAll()).thenReturn(List.of(student1, student2));

    List<StudentsResponse.StudentResponse>allStudents = studentsService.findAllStudents().getData();

    assertThat(allStudents.size(), is(2));
    assertThat(allStudents.get(0).getId(), is(1));
    assertThat(allStudents.get(0).getName(), is("nana"));
    assertThat(allStudents.get(0).getGrade(), is(2));
    assertThat(allStudents.get(0).getClassNumber(), is(3));
    assertThat(allStudents.get(1).getId(), is(2));
    assertThat(allStudents.get(1).getName(), is("po"));
    assertThat(allStudents.get(1).getGrade(), is(3));
    assertThat(allStudents.get(1).getClassNumber(), is(3));

  }

  @Test
  void should_return_students_when_name_is_required() {
    when(studentsRepository.findAllByName("jack")).thenReturn(List.of(student));

    List<StudentsResponse.StudentResponse> requiredStudents = studentsService.findStudentsByName("jack").getData();

    assertThat(requiredStudents.size(), is(1));
    assertThat(requiredStudents.get(0).getName(), is("jack"));
    assertThat(requiredStudents.get(0).getGrade(), is(1));
    assertThat(requiredStudents.get(0).getClassNumber(), is(8));
    assertThat(requiredStudents.get(0).getId(), is(9));
  }

  @Test
  void should_throw_not_found_exception_when_student_is_not_exist() {
    when(studentsRepository.existById(100)).thenReturn(false);

    Executable executable = () -> studentsService.submitHomework(100, CreateHomeworkRequest.builder().build());
    Exception exception = assertThrows(StudentNotFoundException.class, executable);

    assertThat(exception.getMessage(), is("Student not found with id: "+100));
  }

}