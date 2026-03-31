package com.hcl.backend_template.user.auth.dto;

import com.hcl.backend_template.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {

  private final String token;
  private final Long userId;
  private final String email;
  private final UserRole role;
}
