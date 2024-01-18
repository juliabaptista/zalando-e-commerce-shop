package de.zalando.dto;

import de.zalando.model.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
public class UserRegistrationResponse {
  private Long userId;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
}
