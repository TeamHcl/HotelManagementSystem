package com.hcl.backend_template.user.profile.dto;

import com.hcl.backend_template.user.entity.User;
import com.hcl.backend_template.user.entity.UserRole;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {

  private final Long id;
  private final String name;
  private final String email;
  private final UserRole role;
  private final Integer loyaltyPoints;
  private final OffsetDateTime createdAt;

  public static UserProfileResponse from(User user) {
    return new UserProfileResponse(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getRole(),
        user.getLoyaltyPoints(),
        user.getCreatedAt());
  }
}
