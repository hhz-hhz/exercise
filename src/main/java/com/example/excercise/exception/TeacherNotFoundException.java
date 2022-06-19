package com.example.excercise.exception;

public class TeacherNotFoundException extends NotFoundException{
  public TeacherNotFoundException(Integer id){
    super("Teacher not found with id: "+id);
  }
}
