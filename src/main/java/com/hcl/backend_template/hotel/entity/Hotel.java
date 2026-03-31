package com.hcl.backend_template.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "hotels",
    indexes = {@Index(name = "idx_hotels_manager_id", columnList = "manager_id")})
@Getter
@Setter
@NoArgsConstructor
public class Hotel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false, length = 150)
  private String name;

  @Column(name = "location", nullable = false, length = 255)
  private String location;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "manager_id")
  private Long managerId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private HotelStatus status = HotelStatus.PENDING;

  @Column(name = "average_rating", nullable = false, precision = 3, scale = 2)
  private BigDecimal averageRating = BigDecimal.ZERO;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private OffsetDateTime createdAt;
}
