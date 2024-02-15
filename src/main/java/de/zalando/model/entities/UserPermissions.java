package de.zalando.model.entities;

import lombok.Getter;

@Getter
public enum UserPermissions {
  READ("product:read"),
  WRITE("product:write");

  private String permission;

  public void setPermission(String permission) {
    this.permission = permission;
  }

  UserPermissions(String permission) {
    this.permission = permission;
  }
}
