package de.zalando.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode
public class User {

  @Id
  @Column(name = "user_id")
  @GeneratedValue (strategy = GenerationType.AUTO)
  private Long userId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @JsonIgnore
  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;

  @Column(name = "is_archived")
  private boolean archived;
}
