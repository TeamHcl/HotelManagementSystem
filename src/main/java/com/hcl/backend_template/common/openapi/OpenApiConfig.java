package com.hcl.backend_template.common.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Hotel Management API",
            version = "v1",
            description =
                "Modular hotel booking platform: Auth/User, Hotel & Facilities, Room Types & Inventory & Search, Booking & Reviews & Promotions & Email.",
            contact = @Contact(name = "Hotel Platform Team", email = "support@example.com"),
            license = @License(name = "Proprietary")),
    servers = @Server(url = "/"),
    security = {@SecurityRequirement(name = "BearerAuth")},
    tags = {
      @Tag(name = "Auth & User", description = "Registration, login, JWT, user profile, loyalty"),
      @Tag(
          name = "Hotel & Facility",
          description = "Hotel onboarding, facilities, documents, admin review"),
      @Tag(
          name = "Room & Inventory & Search",
          description = "Room types, inventory management, availability engine, search"),
      @Tag(
          name = "Booking & Reviews & Promotions",
          description = "Bookings, cancellations, reviews, coupons, email notifications")
    })
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT")
public class OpenApiConfig {}
