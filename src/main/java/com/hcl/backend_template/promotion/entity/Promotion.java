package com.hcl.backend_template.promotion.entity;

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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "promotions",
    indexes = {@Index(name = "idx_promotions_code", columnList = "code", unique = true)})
@Getter
@Setter
@NoArgsConstructor
public class Promotion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "code", nullable = false, length = 100, unique = true)
  private String code;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, length = 20)
  private PromotionType type;

  @Column(name = "value", nullable = false, precision = 10, scale = 2)
  private BigDecimal value;

  @Column(name = "min_booking_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal minBookingAmount = BigDecimal.ZERO;

  @Column(name = "max_discount", nullable = false, precision = 10, scale = 2)
  private BigDecimal maxDiscount = BigDecimal.ZERO;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "usage_limit", nullable = false)
  private Integer usageLimit = 0;

  @Column(name = "used_count", nullable = false)
  private Integer usedCount = 0;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = Boolean.TRUE;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private OffsetDateTime createdAt;
}
