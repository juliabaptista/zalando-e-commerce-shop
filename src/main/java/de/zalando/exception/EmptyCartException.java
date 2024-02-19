package de.zalando.exception;

public class EmptyCartException extends Exception {
  public EmptyCartException(String message) {
    super(message);
  }

  public EmptyCartException() {
    super();
  }
}
