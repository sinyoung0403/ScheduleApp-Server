package com.example.scheduleappserver.handler;

import com.example.scheduleappserver.exception.InvalidInputException;
import com.example.scheduleappserver.exception.InvalidPasswordException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

@ControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex) {
    return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // 비밀번호가 틀렸을 경우
  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex) {
    return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  // 데이터 조회가 불가할 경우
  @ExceptionHandler(DataFormatException.class)
  public ResponseEntity<String> handleDataFormatException(DataFormatException ex) {
    return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  // Validated 값이 이상할 경우
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
    return new ResponseEntity<>("@Validated Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  // Valid 값이 이상할 경우
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  // 입력값이 이상할 경우
  @ExceptionHandler(InvalidInputException.class)
  public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
    return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
