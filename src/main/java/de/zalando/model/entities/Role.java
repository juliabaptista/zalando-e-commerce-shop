package de.zalando.model.entities;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum Role {
  ADMIN(Set.of(UserPermissions.READ, UserPermissions.WRITE)),
  CUSTOMER(Set.of(UserPermissions.READ));

  private final Set<UserPermissions> userPermissions;
  Role(Set<UserPermissions> userPermissions) {
    this.userPermissions = userPermissions;
  }
  public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
    Set<SimpleGrantedAuthority> permissions = getUserPermissions().stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toSet());
    permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
    return permissions;
  }
}
