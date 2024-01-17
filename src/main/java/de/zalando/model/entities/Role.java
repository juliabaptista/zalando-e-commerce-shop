package de.zalando.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  ADMIN("Admin"),
  CUSTOMER("Customer");

  private final String ROLE;
}
