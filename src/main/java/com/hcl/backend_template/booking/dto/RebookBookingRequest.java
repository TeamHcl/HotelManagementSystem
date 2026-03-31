package com.hcl.backend_template.booking.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RebookBookingRequest {

  @NotNull private LocalDate checkIn;

  @NotNull private LocalDate checkOut;

  private BigDecimal totalPrice;

  private BigDecimal discountAmount;

  private Long promotionId;
}
