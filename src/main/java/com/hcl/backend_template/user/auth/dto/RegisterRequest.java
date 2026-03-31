package com.hcl.backend_template.user.auth.dto;

import com.hcl.backend_template.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank private String name;

  @NotBlank @Email private String email;

  @NotBlank
  @Size(min = 8)
  private String password;

  private UserRole role;
}
