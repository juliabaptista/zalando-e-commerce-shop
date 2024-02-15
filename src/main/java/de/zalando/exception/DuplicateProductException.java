package de.zalando.exception;

public class DuplicateProductException extends Throwable {

  public DuplicateProductException(String message) {
    super(message);
  }

  public DuplicateProductException() {
    super();
  }
}
