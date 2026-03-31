package com.hcl.backend_template.promotion.dto;

import com.hcl.backend_template.promotion.entity.PromotionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePromotionRequest {

  @NotBlank private String code;

  @NotNull private PromotionType type;

  @NotNull
  @DecimalMin("0.01")
  private BigDecimal value;

  @NotNull private BigDecimal minBookingAmount;

  @NotNull private BigDecimal maxDiscount;

  @NotNull private LocalDate startDate;

  @NotNull private LocalDate endDate;

  @NotNull private Integer usageLimit;

  @NotNull private Boolean active;
}
