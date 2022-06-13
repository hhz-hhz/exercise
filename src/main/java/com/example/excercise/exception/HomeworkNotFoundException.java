package com.example.excercise.exception;

public class HomeworkNotFoundException extends NotFoundException{
  public HomeworkNotFoundException (Integer id){
    super("Homework not found with id: "+id);
  }
}
