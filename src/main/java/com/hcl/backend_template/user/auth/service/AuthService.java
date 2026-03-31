package com.hcl.backend_template.user.auth.service;

import com.hcl.backend_template.user.auth.dto.AuthResponse;
import com.hcl.backend_template.user.auth.dto.LoginRequest;
import com.hcl.backend_template.user.auth.dto.RegisterRequest;
import com.hcl.backend_template.user.auth.security.JwtService;
import com.hcl.backend_template.user.entity.User;
import com.hcl.backend_template.user.entity.UserRole;
import com.hcl.backend_template.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthResponse register(RegisterRequest request) {
    if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
    }
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(resolveRole(request));
    User saved = userRepository.save(user);
    return buildAuthResponse(saved);
  }

  public AuthResponse login(LoginRequest request) {
    User user =
        userRepository
            .findByEmailIgnoreCase(request.getEmail())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
    return buildAuthResponse(user);
  }

  private AuthResponse buildAuthResponse(User user) {
    String token = jwtService.generateToken(user);
    return new AuthResponse(token, user.getId(), user.getEmail(), user.getRole());
  }

  private UserRole resolveRole(RegisterRequest request) {
    if (request.getRole() != null) {
      if (request.getRole() == UserRole.ADMIN) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin registration is disabled");
      }
      return request.getRole();
    }
    return UserRole.CUSTOMER;
  }
}
