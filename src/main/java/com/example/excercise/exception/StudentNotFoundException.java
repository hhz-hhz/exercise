package com.example.excercise.exception;

public class StudentNotFoundException extends ParameterException{
  public StudentNotFoundException(Integer id){
    super("Student not found with id: "+id);
  }
}
