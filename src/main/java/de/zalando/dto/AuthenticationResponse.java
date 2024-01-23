package de.zalando.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
public class AuthenticationResponse {

  private String jwt;
  private String message;

}
