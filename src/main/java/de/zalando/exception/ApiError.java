package de.zalando.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ApiError {

  private LocalDateTime createdAt;
  private HttpStatus status;
  private String message;
  private List<String> errors;

  public ApiError(HttpStatus status, String message) {
    this.createdAt = LocalDateTime.now();
    this.status = status;
    this.message = message;
  }

  public ApiError(HttpStatus status, String message, List<String> errors) {
    this.createdAt = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }
}
