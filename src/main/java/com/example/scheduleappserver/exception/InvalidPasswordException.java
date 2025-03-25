package com.example.scheduleappserver.exception;

public class InvalidPasswordException extends RuntimeException {
  private String message;

  public InvalidPasswordException(String message) {
    super(message);
  }
}
