package com.hcl.backend_template.user.init;

import com.hcl.backend_template.user.entity.User;
import com.hcl.backend_template.user.entity.UserRole;
import com.hcl.backend_template.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements ApplicationRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${app.admin.email:admin@luxestay.local}")
  private String adminEmail;

  @Value("${app.admin.password:Admin@12345}")
  private String adminPassword;

  @Value("${app.admin.name:Super Admin}")
  private String adminName;

  @Override
  public void run(ApplicationArguments args) {
    if (userRepository.existsByEmailIgnoreCase(adminEmail)) {
      return;
    }
    User admin = new User();
    admin.setName(adminName);
    admin.setEmail(adminEmail);
    admin.setPassword(passwordEncoder.encode(adminPassword));
    admin.setRole(UserRole.ADMIN);
    userRepository.save(admin);
  }
}
