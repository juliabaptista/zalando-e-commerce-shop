package de.zalando.exception;

public class AccessDeniedException extends RuntimeException {

  public AccessDeniedException(String message) {
    super(message);
  }

  public AccessDeniedException() {
    super();
  }

}
