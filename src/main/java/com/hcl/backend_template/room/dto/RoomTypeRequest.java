package com.hcl.backend_template.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RoomTypeRequest {

  @NotNull
  @Schema(example = "1", description = "ID of the hotel that owns this room type")
  private Long hotelId;

  @NotBlank
  @Schema(example = "Deluxe King Room")
  private String name;

  @NotNull
  @Min(1)
  @Schema(example = "2", description = "Maximum number of guests for this room type")
  private Integer capacity;

  @NotNull
  @Schema(example = "3500.00", description = "Base price per night in the hotel's currency")
  private BigDecimal basePrice;

  public Long getHotelId() {
    return hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public BigDecimal getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(BigDecimal basePrice) {
    this.basePrice = basePrice;
  }
}
