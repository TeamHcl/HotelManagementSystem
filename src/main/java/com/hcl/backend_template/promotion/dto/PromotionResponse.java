package com.hcl.backend_template.promotion.dto;

import com.hcl.backend_template.promotion.entity.Promotion;
import com.hcl.backend_template.promotion.entity.PromotionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PromotionResponse {

  private final Long id;
  private final String code;
  private final PromotionType type;
  private final BigDecimal value;
  private final BigDecimal minBookingAmount;
  private final BigDecimal maxDiscount;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final Integer usageLimit;
  private final Integer usedCount;
  private final boolean active;
  private final OffsetDateTime createdAt;

  public static PromotionResponse from(Promotion promotion) {
    return new PromotionResponse(
        promotion.getId(),
        promotion.getCode(),
        promotion.getType(),
        promotion.getValue(),
        promotion.getMinBookingAmount(),
        promotion.getMaxDiscount(),
        promotion.getStartDate(),
        promotion.getEndDate(),
        promotion.getUsageLimit(),
        promotion.getUsedCount(),
        Boolean.TRUE.equals(promotion.getIsActive()),
        promotion.getCreatedAt());
  }
}
