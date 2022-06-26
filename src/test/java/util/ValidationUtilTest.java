package util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.example.excercise.dto.request.CreateClassroomRequest;
import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.exception.ClassNumberNotValidatedException;
import com.example.excercise.exception.GradeNotValidatedException;
import com.example.excercise.exception.NameIsNullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ValidationUtilTest {

  @Test
  void should_throw_grade_not_validate_exception_when_create_a_classroom_with_garde_bigger_than_9() {
    Executable executable = () -> ValidationUtil.validateCreateClassroomRequest(CreateClassroomRequest.builder()
        .grade(100)
        .classNumber(1)
        .build());
    Exception exception = assertThrows(GradeNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Grade should be on a scale of 1 to 9"));
  }


  @Test
  void should_throw_grade_not_validate_exception_when_create_a_classroom_with_garde_smaller_than_1() {
    Executable executable = () -> ValidationUtil.validateCreateClassroomRequest(CreateClassroomRequest.builder()
        .grade(0)
        .classNumber(1)
        .build());
    Exception exception = assertThrows(GradeNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Grade should be on a scale of 1 to 9"));
  }

  @Test
  void should_throw_classNumber_not_validate_exception_when_create_a_classroom_with_classNumber_smaller_than_1() {
    Executable executable = () -> ValidationUtil.validateCreateClassroomRequest(CreateClassroomRequest.builder()
        .grade(1)
        .classNumber(0)
        .build());
    Exception exception = assertThrows(ClassNumberNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Class number should be on a scale of 1 to 20"));
  }

  @Test
  void should_throw_classNumber_not_validate_exception_when_create_a_classroom_with_classNumber_bigger_than_9() {
    Executable executable = () -> ValidationUtil.validateCreateClassroomRequest(CreateClassroomRequest.builder()
        .grade(1)
        .classNumber(200)
        .build());
    Exception exception = assertThrows(ClassNumberNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Class number should be on a scale of 1 to 20"));
  }

  @Test
  void should_throw_name_null_exception_when_name_is_null_in_request() {
    CreateStudentRequest studentNameNull = CreateStudentRequest.builder()
        .name("")
        .grade(1)
        .classNumber(1)
        .build();

    Executable executable = () -> ValidationUtil.validateStudentRequest(studentNameNull);
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

    Executable executable = () -> ValidationUtil.validateStudentRequest(student);
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

    Executable executable = () -> ValidationUtil.validateStudentRequest(student);
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

    Executable executable = () -> ValidationUtil.validateStudentRequest(student);
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

    Executable executable = () -> ValidationUtil.validateStudentRequest(student);
    Exception exception = assertThrows(ClassNumberNotValidatedException.class, executable);

    assertThat(exception.getMessage(), is("Class number should be on a scale of 1 to 20"));
  }

}