package com.example.scheduleappserver.exception;

public class InvalidInputException extends RuntimeException {
  private String message;

  public InvalidInputException(String message) {
    super(message);
  }
}
