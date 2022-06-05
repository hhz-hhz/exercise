package com.example.excercise.exception;

import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {

  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleValidateException(MethodArgumentNotValidException exception) {
    BindingResult result = exception.getBindingResult();
    String message = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));

    ErrorResponse response =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(), message);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
