package de.zalando.exception;

public class InvalidOrderStatusException extends Exception {
  public InvalidOrderStatusException(String message) {
    super(message);
  }

  public InvalidOrderStatusException() {
    super();
  }

}
