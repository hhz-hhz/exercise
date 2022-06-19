package com.example.excercise.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.excercise.dto.request.CreateClassroomRequest;
import com.example.excercise.entity.ClassroomEntity;
import com.example.excercise.exception.ClassNumberNotValidatedException;
import com.example.excercise.exception.GradeNotValidatedException;
import com.example.excercise.repository.ClassroomsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceTest {
  @Mock
  private ClassroomsRepository classroomsRepository;

  @InjectMocks
  private ClassroomService classroomService;

  @Test
  void should_throw_grade_not_validate_exception_when_create_a_classroom_with_garde_bigger_than_9() {
    Executable executable = () -> classroomService.createClassroom(CreateClassroomRequest.builder()
        .grade(100)
        .classNumber(1)
        .build());
    Exception exception = assertThrows(GradeNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Grade should be on a scale of 1 to 9"));
  }


  @Test
  void should_throw_grade_not_validate_exception_when_create_a_classroom_with_garde_smaller_than_1() {
    Executable executable = () -> classroomService.createClassroom(CreateClassroomRequest.builder()
        .grade(0)
        .classNumber(1)
        .build());
    Exception exception = assertThrows(GradeNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Grade should be on a scale of 1 to 9"));
  }

  @Test
  void should_throw_classNumber_not_validate_exception_when_create_a_classroom_with_classNumber_smaller_than_1() {
    Executable executable = () -> classroomService.createClassroom(CreateClassroomRequest.builder()
        .grade(1)
        .classNumber(0)
        .build());
    Exception exception = assertThrows(ClassNumberNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Class number should be on a scale of 1 to 20"));
  }

  @Test
  void should_throw_classNumber_not_validate_exception_when_create_a_classroom_with_classNumber_bigger_than_9() {
    Executable executable = () -> classroomService.createClassroom(CreateClassroomRequest.builder()
        .grade(1)
        .classNumber(200)
        .build());
    Exception exception = assertThrows(ClassNumberNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Class number should be on a scale of 1 to 20"));
  }

  @Test
  void should_return_classroom_id_when_create_a_classroom_successfully() {
    ArgumentCaptor<ClassroomEntity> captor = ArgumentCaptor.forClass(ClassroomEntity.class);
    when(classroomsRepository.save(captor.capture())).thenReturn(ClassroomEntity.builder()
            .id(1)
            .grade(3)
            .classNumber(4)
        .build());

    Integer classroomId = classroomService.createClassroom(CreateClassroomRequest.builder()
        .grade(3)
        .classNumber(4)
        .build());

    ClassroomEntity classroom = captor.getValue();
    assertThat(classroom.getGrade(), is(3));
    assertThat(classroom.getClassNumber(), is(4));
    assertThat(classroomId, is(1));
  }
}