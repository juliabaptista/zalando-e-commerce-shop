package de.zalando.dto;

import de.zalando.model.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

  @NotNull(message = "Required field. Cannot be null!")
  @NotEmpty(message = "Required field. Cannot be empty!")
  private String firstName;

  @NotNull(message = "Required field. Cannot be null!")
  @NotEmpty(message = "Required field. Cannot be empty!")
  private String lastName;

  @Email(message = "Please, provide a valid email address!")
  @NotNull(message = "Required field. Cannot be null!")
  @NotEmpty(message = "Required field. Cannot be empty!")
  private String email;

  @NotNull(message = "Required field. Cannot be null!")
  @NotEmpty(message = "Required field. Cannot be empty!")
  @Size(min = 6, message = "Password must be at least 6 characters long")
  private String password;

  @NotNull(message = "Required field. Cannot be null!")
  private Role role;
}
