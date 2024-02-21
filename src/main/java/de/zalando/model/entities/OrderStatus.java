package de.zalando.model.entities;

import java.util.Arrays;

public enum OrderStatus {
  PROCESSING,
  SHIPPED,
  DELIVERED;

  public static boolean containsIgnoreCase(String value) {
    return Arrays.stream(values())
        .anyMatch(status -> status.name().equalsIgnoreCase(value));
  }

  public static OrderStatus fromStringIgnoreCase(String value) {
    if (value == null) {
      return null;
    }
    for (OrderStatus status : values()) {
      if (status.name().equalsIgnoreCase(value)) {
        return status;
      }
    }
    return null;
  }

  public static String toUpperCase() {
    return toUpperCase();
  }
}
