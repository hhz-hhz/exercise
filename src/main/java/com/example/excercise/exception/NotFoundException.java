package com.example.excercise.exception;

public class NotFoundException  extends RuntimeException{
  public NotFoundException (String message){
    super(message);
  }
}
