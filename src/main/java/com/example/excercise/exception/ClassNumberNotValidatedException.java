package com.example.excercise.exception;

public class ClassNumberNotValidatedException extends ParameterException{
  public ClassNumberNotValidatedException() {
    super("Class number should be on a scale of 1 to 20");
  }
}
