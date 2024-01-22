package de.zalando.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
public class LoginRequest {

  @Email(message = "Please, provide a valid email address!")
  @NotEmpty(message = "Required field. Cannot be empty!")
  private String email;

  @NotEmpty(message = "Required field. Cannot be empty!")
  @Size(min = 6, message = "Password must be at least 6 characters long")
  private String password;

}
