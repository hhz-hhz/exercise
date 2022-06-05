package com.example.excercise.exception;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class ControllerExceptionTest {
  @InjectMocks
  private ControllerException controllerException;

  @BeforeEach
  void setUp() {
    controllerException = new ControllerException();
  }

  @Test
  void returnsValidationErrorResponseWhenMethodArgumentNotValidExceptionIsCaught() {
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("", "name", "Name is Required");
    when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
    MethodParameter methodParameter = mock(MethodParameter.class);
    MethodArgumentNotValidException methodArgumentNotValidException =
        new MethodArgumentNotValidException(methodParameter, bindingResult);

    ResponseEntity<ErrorResponse> errorResponse =
        controllerException.handleValidateException(methodArgumentNotValidException);

    assertThat(errorResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(Objects.requireNonNull(errorResponse.getBody()).getError()).isEqualTo("Bad Request");
    assertThat(errorResponse.getBody().getMessage()).isEqualTo("Name is Required");
  }
}