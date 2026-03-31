package com.hcl.backend_template.user.auth.controller;

import com.hcl.backend_template.user.auth.dto.AuthResponse;
import com.hcl.backend_template.user.auth.dto.LoginRequest;
import com.hcl.backend_template.user.auth.dto.RegisterRequest;
import com.hcl.backend_template.user.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Registration and login endpoints")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Register a new user")
  public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
    return authService.register(request);
  }

  @PostMapping("/login")
  @Operation(summary = "Login with email and password")
  public AuthResponse login(@Valid @RequestBody LoginRequest request) {
    return authService.login(request);
  }
}
