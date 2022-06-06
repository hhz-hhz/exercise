package com.example.excercise.exception;

public class GradeNotValidatedException extends ParameterException{

  public GradeNotValidatedException() {
    super("Grade should be on a scale of 1 to 9");
  }
}
