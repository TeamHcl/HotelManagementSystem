package com.hcl.backend_template.booking.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookingRequest {

  private Long roomId;

  private Long roomTypeId;

  @NotNull private LocalDate checkIn;

  @NotNull private LocalDate checkOut;

  @NotNull private BigDecimal totalPrice;

  private BigDecimal discountAmount;

  private Long promotionId;
}
