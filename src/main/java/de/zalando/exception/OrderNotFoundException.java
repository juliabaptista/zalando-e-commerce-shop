package de.zalando.exception;

public class OrderNotFoundException extends Exception {
  public OrderNotFoundException(String message) {
    super(message);
  }

  public OrderNotFoundException() {

  }

}
