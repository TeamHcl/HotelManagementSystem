package com.hcl.backend_template.user.service;

import com.hcl.backend_template.user.entity.User;
import com.hcl.backend_template.user.profile.dto.UpdateUserProfileRequest;
import com.hcl.backend_template.user.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User getCurrentUser() {
    return getRequiredUser(getCurrentUserId());
  }

  public User updateProfile(UpdateUserProfileRequest request) {
    User user = getCurrentUser();
    if (!Objects.equals(user.getEmail(), request.getEmail())) {
      if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
      }
      user.setEmail(request.getEmail());
    }
    user.setName(request.getName());
    return userRepository.save(user);
  }

  public int getLoyaltyPoints() {
    return getCurrentUser().getLoyaltyPoints();
  }

  private User getRequiredUser(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  private Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getPrincipal() == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
    }
    Object principal = authentication.getPrincipal();
    if (principal
        instanceof com.hcl.backend_template.user.auth.security.UserPrincipal userPrincipal) {
      return userPrincipal.getId();
    }
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authentication");
  }
}
