package util;

import com.example.excercise.dto.request.CreateClassroomRequest;
import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.exception.ClassNumberNotValidatedException;
import com.example.excercise.exception.GradeNotValidatedException;
import com.example.excercise.exception.NameIsNullException;

public class ValidationUtil {
  public static void validateCreateClassroomRequest(CreateClassroomRequest createClassroomRequest){
    if(createClassroomRequest.getGrade() < 1 || createClassroomRequest.getGrade() > 9){
      throw new GradeNotValidatedException();
    }
    if(createClassroomRequest.getClassNumber() < 1 || createClassroomRequest.getClassNumber() > 20){
      throw new ClassNumberNotValidatedException();
    }
  }

  public static void validateStudentRequest(CreateStudentRequest createStudentRequest){
    if(createStudentRequest.getName().isBlank()){
      throw new NameIsNullException();
    }
    if(createStudentRequest.getGrade() < 1 || createStudentRequest.getGrade() > 9){
      throw new GradeNotValidatedException();
    }
    if(createStudentRequest.getClassNumber() < 1 || createStudentRequest.getClassNumber() > 20){
      throw new ClassNumberNotValidatedException();
    }
  }
}
