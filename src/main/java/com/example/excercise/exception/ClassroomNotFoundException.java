package com.example.excercise.exception;

public class ClassroomNotFoundException extends NotFoundException{
  public ClassroomNotFoundException(Integer grade, Integer classNumber){
    super("Classroom not found with grade: " + grade + " and class: " + classNumber);
  }
  public ClassroomNotFoundException(Integer id){
    super("Classroom not found with id: " + id);
  }
}
