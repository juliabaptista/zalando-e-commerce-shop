package de.zalando.exception;

public class InsufficientStockException extends Exception {
  public InsufficientStockException(String message) {
    super(message);
  }

  public InsufficientStockException() {
    super();
  }
}
