package com.example.excercise.exception;

public class StudentNotFoundException extends RuntimeException{
  public StudentNotFoundException(Integer id){
    super("Student not found with id: "+id);
  }
}
