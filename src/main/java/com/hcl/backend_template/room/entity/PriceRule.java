package com.hcl.backend_template.room.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "price_rules",
    indexes = {@Index(name = "idx_price_rules_room_type_id", columnList = "room_type_id")})
@Getter
@Setter
@NoArgsConstructor
public class PriceRule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "room_type_id", nullable = false)
  private Long roomTypeId;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "multiplier", nullable = false, precision = 5, scale = 2)
  private BigDecimal multiplier;
}
