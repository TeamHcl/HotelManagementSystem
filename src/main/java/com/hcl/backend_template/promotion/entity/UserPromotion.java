package com.hcl.backend_template.promotion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "user_promotions",
    indexes = {
      @Index(name = "idx_user_promotions_user_id", columnList = "user_id"),
      @Index(name = "idx_user_promotions_promotion_id", columnList = "promotion_id")
    })
@Getter
@Setter
@NoArgsConstructor
public class UserPromotion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "promotion_id", nullable = false)
  private Long promotionId;

  @Column(name = "used_count", nullable = false)
  private Integer usedCount = 0;
}
