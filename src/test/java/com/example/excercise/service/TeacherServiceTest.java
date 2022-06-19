package com.example.excercise.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.entity.ClassroomEntity;
import com.example.excercise.entity.HomeworkEntity;
import com.example.excercise.entity.TeacherEntity;
import com.example.excercise.exception.ClassroomNotFoundException;
import com.example.excercise.exception.TeacherNotFoundException;
import com.example.excercise.repository.TeachersRepository;
import java.util.ArrayList;
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
class TeacherServiceTest {
  @Mock
  private TeachersRepository teachersRepository;
  
  @InjectMocks
  private TeacherService teacherService;

  @Test
  void should_throw_teacher_not_found_exception_when_create_a_homework() {
    when(teachersRepository.findById(1))
        .thenReturn(Optional.empty());

    Executable executable = () -> teacherService.createHomework(CreateHomeworkRequest.builder()
            .teacher_id(1)
        .build());
    Exception exception = assertThrows(TeacherNotFoundException.class, executable);

    assertThat(exception.getMessage(), is("Teacher not found with id: 1"));

  }
  @Test
  void should_throw_classroom_not_found_exception_when_create_a_homework() {
    when(teachersRepository.findById(1))
        .thenReturn(Optional.of(TeacherEntity.builder().classrooms(List.of()).build()));

    Executable executable = () -> teacherService.createHomework(CreateHomeworkRequest.builder()
            .teacher_id(1)
            .classroom_id(2)
        .build());
    Exception exception = assertThrows(ClassroomNotFoundException.class, executable);

    assertThat(exception.getMessage(), is("Classroom not found with id: 2"));

  }

  @Test
  void should_return_homework_id_when_create_homework_successfully() {
    ArgumentCaptor<TeacherEntity> captor = ArgumentCaptor.forClass(TeacherEntity.class);
    ClassroomEntity classroom = ClassroomEntity.builder().id(1).build();
    TeacherEntity teacherById = TeacherEntity.builder()
        .id(2)
        .name("nana")
        .classrooms(List.of(classroom))
        .homework(new ArrayList<>())
        .build();
    when(teachersRepository.findById(2)).thenReturn(Optional.of(teacherById));
    when(teachersRepository.save(captor.capture()))
        .thenReturn(TeacherEntity.builder()
            .id(2)
            .name("nana")
            .homework(List.of(HomeworkEntity
                .builder()
                    .id(1)
                    .content("homework")
                    .created_at("nana")
                    .classroom(classroom)
                .build()))
            .build());

    CreateHomeworkRequest homework = CreateHomeworkRequest.builder()
        .content("homework")
        .teacher_id(2)
        .classroom_id(1)
        .build();
    Integer homeworkId = teacherService.createHomework(homework);

    TeacherEntity teacher = captor.getValue();
    assertThat(teacher.getId(), is(2));
    assertThat(teacher.getName(), is("nana"));
    List<HomeworkEntity> homeworkList = teacher.getHomework();
    assertThat(homeworkList.size(), is(1));
    assertThat(homeworkList.get(0).getContent(), is("homework"));
    assertThat(homeworkList.get(0).getCreated_at(), is("nana"));
    assertThat(homeworkList.get(0).getTeacher().getId(), is(2));
    assertThat(homeworkList.get(0).getTeacher().getName(), is("nana"));
    assertThat(homeworkList.get(0).getClassroom().getId(), is(1));
    assertThat(homeworkId, is(1));
  }
}