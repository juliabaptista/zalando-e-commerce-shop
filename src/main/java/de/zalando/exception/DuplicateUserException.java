package de.zalando.exception;


public class DuplicateUserException extends Throwable {
  public DuplicateUserException(String message) {
    super(message);
  }

  public DuplicateUserException() {
    super();
  }
}
