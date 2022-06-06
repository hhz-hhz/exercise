package com.example.excercise.exception;

public class NameIsNullException extends ParameterException{
  public NameIsNullException() {
    super("Name is required");
  }
}
