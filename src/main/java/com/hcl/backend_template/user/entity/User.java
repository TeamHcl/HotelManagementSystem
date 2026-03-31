package com.hcl.backend_template.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "users",
    indexes = {@Index(name = "idx_users_email", columnList = "email", unique = true)})
@Getter
@Setter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "email", nullable = false, length = 150, unique = true)
  private String email;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, length = 20)
  private UserRole role;

  @Column(name = "loyalty_points", nullable = false)
  private Integer loyaltyPoints = 0;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private OffsetDateTime createdAt;
}
