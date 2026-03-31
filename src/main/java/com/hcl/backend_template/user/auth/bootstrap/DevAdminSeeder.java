package com.hcl.backend_template.user.auth.bootstrap;

import com.hcl.backend_template.user.entity.User;
import com.hcl.backend_template.user.entity.UserRole;
import com.hcl.backend_template.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DevAdminSeeder implements ApplicationRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${app.environment:local}")
  private String appEnvironment;

  @Value("${app.dev.admin.email:}")
  private String adminEmail;

  @Value("${app.dev.admin.password:}")
  private String adminPassword;

  @Value("${app.dev.admin.name:Local Admin}")
  private String adminName;

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    if (!"local".equalsIgnoreCase(appEnvironment)) {
      return;
    }

    if (adminEmail == null
        || adminEmail.isBlank()
        || adminPassword == null
        || adminPassword.isBlank()) {
      return;
    }

    User user =
        userRepository
            .findByEmailIgnoreCase(adminEmail)
            .orElseGet(
                () -> {
                  User created = new User();
                  created.setName(adminName);
                  created.setEmail(adminEmail);
                  created.setPassword(passwordEncoder.encode(adminPassword));
                  created.setRole(UserRole.ADMIN);
                  created.setLoyaltyPoints(0);
                  return created;
                });

    if (user.getRole() != UserRole.ADMIN) {
      user.setRole(UserRole.ADMIN);
    }

    if (user.getName() == null || user.getName().isBlank()) {
      user.setName(adminName);
    }

    if (user.getPassword() == null || !passwordEncoder.matches(adminPassword, user.getPassword())) {
      user.setPassword(passwordEncoder.encode(adminPassword));
    }

    userRepository.save(user);
  }
}
