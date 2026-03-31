package com.hcl.backend_template.user.profile.controller;

import com.hcl.backend_template.user.profile.dto.LoyaltyResponse;
import com.hcl.backend_template.user.profile.dto.UpdateUserProfileRequest;
import com.hcl.backend_template.user.profile.dto.UserProfileResponse;
import com.hcl.backend_template.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Authenticated user profile endpoints")
@SecurityRequirement(name = "BearerAuth")
public class UserProfileController {

  private final UserService userService;

  @GetMapping
  @Operation(summary = "Get current user profile")
  public UserProfileResponse getProfile() {
    return UserProfileResponse.from(userService.getCurrentUser());
  }

  @PutMapping
  @Operation(summary = "Update current user profile")
  public UserProfileResponse updateProfile(@Valid @RequestBody UpdateUserProfileRequest request) {
    return UserProfileResponse.from(userService.updateProfile(request));
  }

  @GetMapping("/loyalty")
  @Operation(summary = "Get current user loyalty points")
  public LoyaltyResponse getLoyalty() {
    return new LoyaltyResponse(userService.getLoyaltyPoints());
  }
}
