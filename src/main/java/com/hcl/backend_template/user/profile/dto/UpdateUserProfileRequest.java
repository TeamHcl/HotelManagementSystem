package com.hcl.backend_template.user.profile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserProfileRequest {

  @NotBlank private String name;

  @NotBlank @Email private String email;
}
