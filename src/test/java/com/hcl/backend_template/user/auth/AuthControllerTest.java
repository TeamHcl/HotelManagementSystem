package com.hcl.backend_template.user.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.backend_template.user.auth.controller.AuthController;
import com.hcl.backend_template.user.auth.dto.AuthResponse;
import com.hcl.backend_template.user.auth.dto.LoginRequest;
import com.hcl.backend_template.user.auth.dto.RegisterRequest;
import com.hcl.backend_template.user.auth.service.AuthService;
import com.hcl.backend_template.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  @Mock private AuthService authService;

  @InjectMocks private AuthController authController;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
  }

  @Test
  void registerReturnsToken() throws Exception {
    RegisterRequest request = new RegisterRequest();
    request.setName("Ada Lovelace");
    request.setEmail("ada@example.com");
    request.setPassword("StrongPass123!");
    request.setRole(UserRole.CUSTOMER);

    AuthResponse response = new AuthResponse("token", 1L, "ada@example.com", UserRole.CUSTOMER);
    when(authService.register(any())).thenReturn(response);

    mockMvc
        .perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.token").value("token"))
        .andExpect(jsonPath("$.userId").value(1L))
        .andExpect(jsonPath("$.email").value("ada@example.com"))
        .andExpect(jsonPath("$.role").value("CUSTOMER"));
  }

  @Test
  void loginReturnsToken() throws Exception {
    LoginRequest request = new LoginRequest();
    request.setEmail("ada@example.com");
    request.setPassword("StrongPass123!");

    AuthResponse response = new AuthResponse("token", 1L, "ada@example.com", UserRole.CUSTOMER);
    when(authService.login(any())).thenReturn(response);

    mockMvc
        .perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("token"))
        .andExpect(jsonPath("$.userId").value(1L))
        .andExpect(jsonPath("$.email").value("ada@example.com"))
        .andExpect(jsonPath("$.role").value("CUSTOMER"));
  }
}
