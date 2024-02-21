package de.zalando.dto;

import de.zalando.model.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

  @NotEmpty(message = "Required field. Cannot be empty!")
  private String firstName;

  @NotEmpty(message = "Required field. Cannot be empty!")
  private String lastName;

  @Email(message = "Please, provide a valid email address!")
  @NotEmpty(message = "Required field. Cannot be empty!")
  private String email;

  @NotEmpty(message = "Required field. Cannot be empty!")
  @Size(min = 6, message = "Password must be at least 6 characters long")
  private String password;

  @NotEmpty(message = "Required field. Cannot be empty!")
  private Role role;
}
