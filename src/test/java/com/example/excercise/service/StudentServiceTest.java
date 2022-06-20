package com.example.excercise.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.dto.request.CreateStudentHomeworkRequest;
import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.request.UpdateHomeworkRequest;
import com.example.excercise.dto.responce.HomeworkResponse;
import com.example.excercise.dto.responce.StudentGroupsResponse;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.entity.ClassroomEntity;
import com.example.excercise.entity.HomeworkEntity;
import com.example.excercise.entity.StudentEntity;
import com.example.excercise.entity.StudentHomeworkEntity;
import com.example.excercise.exception.ClassNumberNotValidatedException;
import com.example.excercise.exception.ClassroomNotFoundException;
import com.example.excercise.exception.GradeNotValidatedException;
import com.example.excercise.exception.HomeworkNotFoundException;
import com.example.excercise.exception.NameIsNullException;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.repository.ClassroomsRepository;
import com.example.excercise.repository.HomeworkRepository;
import com.example.excercise.repository.StudentsRepository;
import java.util.ArrayList;
import java.util.Date;
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
class StudentServiceTest {

  @InjectMocks
  private StudentService studentService;

  @Mock
  private StudentsRepository studentsRepository;

  @Mock
  private ClassroomsRepository classroomsRepository;

  @Mock
  private HomeworkRepository homeworkRepository;

  private final CreateStudentRequest studentRequest = CreateStudentRequest.builder()
      .name("jack")
      .grade(1)
      .classNumber(8)
      .build();
  private final StudentEntity student = StudentEntity.builder()
      .id(9)
      .name(studentRequest.getName())
      .classroom(
          ClassroomEntity.builder()
              .grade(studentRequest.getGrade())
              .classNumber(studentRequest.getClassNumber())
              .build()
      )
      .build();

  @Test
  void should_return_student_when_create_a_student() {
    when(classroomsRepository.findByGradeAndClassNumber(1,8))
        .thenReturn(Optional.of(ClassroomEntity.builder()
                .grade(1)
                .classNumber(8)
            .build()));
    ArgumentCaptor<StudentEntity> captor = ArgumentCaptor.forClass(StudentEntity.class);
    when(studentsRepository.save(captor.capture())).thenReturn(student);

    StudentIdResponse actualStudent = studentService.createStudent(studentRequest);

    StudentEntity argument = captor.getValue();
    assertThat(argument.getName(), is("jack"));
    assertThat(argument.getClassroom().getGrade(), is(1));
    assertThat(argument.getClassroom().getClassNumber(), is(8));
    assertThat(actualStudent.getId(), is(9));
  }

  @Test
  void should_throw_classroom_not_found_exception_when_create_a_student() {
    when(classroomsRepository.findByGradeAndClassNumber(1,8))
        .thenReturn(Optional.empty());

    Executable executable = () -> studentService.createStudent(studentRequest);
    Exception exception = assertThrows(ClassroomNotFoundException.class, executable);

    assertThat(exception.getMessage(), is("Classroom not found with grade: 1 and class: 8"));
  }

  @Test
  void should_throw_name_null_exception_when_name_is_null_in_request() {
    CreateStudentRequest studentNameNull = CreateStudentRequest.builder()
        .name("")
        .grade(1)
        .classNumber(1)
        .build();

    Executable executable = () -> studentService.createStudent(studentNameNull);
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

    Executable executable = () -> studentService.createStudent(student);
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

    Executable executable = () -> studentService.createStudent(student);
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

    Executable executable = () -> studentService.createStudent(student);
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

    Executable executable = () -> studentService.createStudent(student);
    Exception exception = assertThrows(ClassNumberNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Class number should be on a scale of 1 to 20"));
  }

  @Test
  void should_return_student_when_find_exist_student() {
    when(studentsRepository.findById(9)).thenReturn(Optional.ofNullable(student));

    StudentsResponse.StudentResponse studentById = studentService.findStudentById(9).getData().get(0);

    assertThat(studentById.getName(), is("jack"));
    assertThat(studentById.getClassroom().getGrade(), is(1));
    assertThat(studentById.getClassroom().getClassNumber(), is(8));
    assertThat(studentById.getId(), is(9));
  }

  @Test
  void should_throw_exception_when_can_not_find_student() {
    when(studentsRepository.findById(10)).thenReturn(Optional.empty());

    Executable executable = () -> studentService.findStudentById(10);
    Exception exception = assertThrows(StudentNotFoundException.class, executable);

    assertThat(exception.getMessage(), is("Student not found with id: "+10));
  }

  @Test
  void should_return_2_students_when_have_2_student() {
    StudentEntity student1 = StudentEntity.builder()
        .id(1)
        .name("nana")
        .classroom(
            ClassroomEntity.builder()
                .grade(2)
                .classNumber(3)
                .build()
        )
        .build();
    StudentEntity student2 = StudentEntity.builder()
        .id(2)
        .name("po")
        .classroom(
            ClassroomEntity.builder()
                .grade(3)
                .classNumber(3)
                .build()
        )
        .build();
    when(studentsRepository.findAll()).thenReturn(List.of(student1, student2));

    List<StudentsResponse.StudentResponse>allStudents = studentService.findAllStudents().getData();

    assertThat(allStudents.size(), is(2));
    assertThat(allStudents.get(0).getId(), is(1));
    assertThat(allStudents.get(0).getName(), is("nana"));
    assertThat(allStudents.get(0).getClassroom().getGrade(), is(2));
    assertThat(allStudents.get(0).getClassroom().getClassNumber(), is(3));
    assertThat(allStudents.get(1).getId(), is(2));
    assertThat(allStudents.get(1).getName(), is("po"));
    assertThat(allStudents.get(1).getClassroom().getGrade(), is(3));
    assertThat(allStudents.get(1).getClassroom().getClassNumber(), is(3));

  }

  @Test
  void should_return_students_when_name_is_required() {
    when(studentsRepository.findAllByName("jack")).thenReturn(List.of(student));

    List<StudentsResponse.StudentResponse> requiredStudents = studentService.findStudentsByName("jack").getData();

    assertThat(requiredStudents.size(), is(1));
    assertThat(requiredStudents.get(0).getName(), is("jack"));
    assertThat(requiredStudents.get(0).getClassroom().getGrade(), is(1));
    assertThat(requiredStudents.get(0).getClassroom().getClassNumber(), is(8));
    assertThat(requiredStudents.get(0).getId(), is(9));
  }

//  @Test
//  void should_return_studentGroups_when_using_group_by_homework_endpoint_and_queried_a_topic() {
//
//    List<HomeworkEntity> homework = List.of(HomeworkEntity.builder()
//        .topic("homework")
//            .student(List.of(StudentEntity
//                    .builder()
//                    .id(1)
//                    .name("nana")
//                    .classroom(
//                        ClassroomEntity.builder()
//                            .grade(1)
//                            .classNumber(1)
//                            .build()
//                    )
//                    .build(),
//                StudentEntity.builder()
//                    .id(2)
//                    .name("jack")
//                    .classroom(
//                        ClassroomEntity.builder()
//                            .grade(2)
//                            .classNumber(3)
//                            .build()
//                    )
//                    .build()))
//        .build());
//    when(studentsRepository.findAll())
//        .thenReturn(List.of(StudentEntity
//            .builder()
//                .id(1)
//                .name("nana")
//                .classroom(
//                    ClassroomEntity.builder()
//                        .grade(1)
//                        .classNumber(1)
//                        .build()
//                )
//                .homework(homework)
//            .build(),
//            StudentEntity.builder()
//                .id(2)
//                .name("jack")
//                .classroom(
//                    ClassroomEntity.builder()
//                        .grade(2)
//                        .classNumber(3)
//                        .build()
//                )
//                .homework(homework)
//            .build(),
//            StudentEntity.builder()
//                .id(3)
//                .name("momo")
//                .classroom(
//                    ClassroomEntity.builder()
//                        .grade(2)
//                        .classNumber(3)
//                        .build()
//                )
//                .homework(List.of(HomeworkEntity.builder()
//                    .topic("ui")
//                    .build()))
//            .build()));
//
//    List<List<StudentGroupsResponse.StudentResponse>> groups = studentService.findStudentGroupsByTopic("homework").getGroups();
//
//    assertThat(groups.size(), is(1));
//    List<StudentGroupsResponse.StudentResponse> students = groups.get(0);
//    assertThat(students.size(), is(2));
//    assertThat(students.get(0).getId(), is(1));
//    assertThat(students.get(0).getName(), is("nana"));
//    assertThat(students.get(0).getClassroom().getGrade(), is(1));
//    assertThat(students.get(0).getClassroom().getClassNumber(), is(1));
//
//    assertThat(students.get(1).getId(), is(2));
//    assertThat(students.get(1).getName(), is("jack"));
//    assertThat(students.get(1).getClassroom().getGrade(), is(2));
//    assertThat(students.get(1).getClassroom().getClassNumber(), is(3));
//  }

  @Test
  void should_throw_not_found_exception_when_student_is_not_exist() {
    when(studentsRepository.findById(100)).thenReturn(Optional.empty());

    Executable executable = () -> studentService.submitHomework(1, CreateStudentHomeworkRequest.builder().student_id(100).build());
    Exception exception = assertThrows(StudentNotFoundException.class, executable);

    assertThat(exception.getMessage(), is("Student not found with id: "+100));
  }
  @Test
  void should_throw_not_found_exception_when_homework_is_not_exist() {
    when(homeworkRepository.findById(100)).thenReturn(Optional.empty());
    when(studentsRepository.findById(1)).thenReturn(Optional.of(StudentEntity.builder().id(1).build()));

    Executable executable = () -> studentService.submitHomework(100, CreateStudentHomeworkRequest.builder()
        .student_id(1).build());
    Exception exception = assertThrows(HomeworkNotFoundException.class, executable);

    assertThat(exception.getMessage(), is("Homework not found with id: "+100));
  }

  @Test
  void should_return_homework_id_when_student_submit_homework() {
    ArgumentCaptor<StudentEntity> captor = ArgumentCaptor.forClass(StudentEntity.class);
    StudentEntity studentById = StudentEntity.builder()
        .id(7)
        .name("nana")
        .studentHomework(new ArrayList<>())
        .build();
    Optional<StudentEntity> optionalStudentEntity = Optional.of(studentById);
    HomeworkEntity homeworkById = HomeworkEntity.builder()
        .id(1)
        .build();
    when(studentsRepository.findById(7))
        .thenReturn(optionalStudentEntity);
    when(homeworkRepository.findById(1)).thenReturn(Optional.of(homeworkById));
    when(studentsRepository.save(captor.capture()))
        .thenReturn(StudentEntity
            .builder()
            .studentHomework(List.of(StudentHomeworkEntity
                .builder()
                    .id(1)
                    .content("homework")
                    .student(studentById)
                    .homework(homeworkById)
                .build()))
            .build());

    Integer homeworkId = studentService.submitHomework(1, CreateStudentHomeworkRequest.builder()
            .student_id(7)
            .content("homework")
        .build());

    assertThat(homeworkId, is(1));
    StudentEntity studentEntity = captor.getValue();
    List<StudentHomeworkEntity> homework = studentEntity.getStudentHomework();
    assertThat(homework.size(), is(1));
    assertThat(homework.get(0).getContent(),is("homework"));
    assertThat(homework.get(0).getStudent().getId(),is(7));
    assertThat(homework.get(0).getStudent().getName(),is("nana"));
    assertThat(homework.get(0).getHomework().getId(), is(1));
  }

//  @Test
//  void should_throw_not_found_exception_when_student_updates_homework_and_student_is_not_exist() {
//    when(studentsRepository.findById(100)).thenReturn(Optional.empty());
//
//    Executable executable = () -> studentService.updateHomework(100, UpdateHomeworkRequest.builder().build());
//    Exception exception = assertThrows(StudentNotFoundException.class, executable);
//
//    assertThat(exception.getMessage(), is("Student not found with id: "+100));
//  }
//  @Test
//  void should_throw_not_found_exception_when_student_updates_homework_and_homework_is_not_exist() {
//    when(studentsRepository.findById(100))
//        .thenReturn(Optional.of(StudentEntity
//        .builder()
//        .homework(List.of())
//        .build()));
//
//    Executable executable = () -> studentService.updateHomework(100, UpdateHomeworkRequest.builder().id(1).build());
//    Exception exception = assertThrows(HomeworkNotFoundException.class, executable);
//
//    assertThat(exception.getMessage(), is("Homework not found with id: "+1));
//  }
//
//  @Test
//  void should_return_student_when_student_homework_update_successfully() {
//    ArgumentCaptor<StudentEntity> captor = ArgumentCaptor.forClass(StudentEntity.class);
//    StudentEntity studentById = StudentEntity
//        .builder()
//        .id(1)
//        .homework(new ArrayList<>(List.of(HomeworkEntity.builder()
//            .id(2)
//            .topic("homework")
//            .content("hello")
//            .build())))
//        .build();
//    when(studentsRepository.findById(1))
//        .thenReturn(Optional.of(studentById));
//    when(studentsRepository.save(captor.capture()))
//        .thenReturn(StudentEntity
//            .builder()
//            .id(1)
//            .homework(List.of(HomeworkEntity
//                .builder()
//                .id(2)
//                .topic("homework")
//                .content("update")
//                .student(new ArrayList<>(List.of(studentById)))
//                .build()))
//            .build());
//
//
//    List<StudentsResponse.StudentResponse> actual = studentService.updateHomework(1, UpdateHomeworkRequest.builder()
//            .id(2)
//            .content("update")
//            .build())
//        .getData();
//
//    StudentEntity studentEntity = captor.getValue();
//    List<HomeworkEntity> homework = studentEntity.getHomework();
//    assertThat(homework.size(), is(1));
//    assertThat(homework.get(0).getId(), is(2));
//    assertThat(homework.get(0).getTopic(), is("homework"));
//    assertThat(homework.get(0).getContent(),is("update"));
//
//    assertThat(actual.size(), is(1));
//    assertThat(actual.get(0).getId(), is(1));
//    List<HomeworkResponse> homeworkList = actual.get(0).getHomework();
//    assertThat(homeworkList.size(), is(1));
//    assertThat(homeworkList.get(0).getId(), is(2));
//    assertThat(homeworkList.get(0).getTopic(), is("homework"));
//    assertThat(homeworkList.get(0).getContent(), is("update"));
//  }
}