package com.hcl.backend_template.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AvailabilityResponse {

  @Schema(
      description = "Whether the room type is available for the full date range",
      example = "true")
  private boolean available;

  @Schema(description = "Total price for the full stay", example = "15750.00")
  private BigDecimal totalPrice;

  @Schema(
      description = "Dates in the requested range",
      example = "[2026-04-01, 2026-04-02, 2026-04-03]")
  private List<LocalDate> dates;

  @Schema(
      description = "Nightly prices matching the dates list",
      example = "[3500.00, 5250.00, 7000.00]")
  private List<BigDecimal> nightlyPrices;

  @Schema(example = "10")
  private Long roomTypeId;

  @Schema(example = "1")
  private Long hotelId;

  @Schema(example = "Deluxe King Room")
  private String roomTypeName;

  @Schema(example = "2")
  private Integer capacity;

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public List<LocalDate> getDates() {
    return dates;
  }

  public void setDates(List<LocalDate> dates) {
    this.dates = dates;
  }

  public List<BigDecimal> getNightlyPrices() {
    return nightlyPrices;
  }

  public void setNightlyPrices(List<BigDecimal> nightlyPrices) {
    this.nightlyPrices = nightlyPrices;
  }

  public Long getRoomTypeId() {
    return roomTypeId;
  }

  public void setRoomTypeId(Long roomTypeId) {
    this.roomTypeId = roomTypeId;
  }

  public Long getHotelId() {
    return hotelId;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public String getRoomTypeName() {
    return roomTypeName;
  }

  public void setRoomTypeName(String roomTypeName) {
    this.roomTypeName = roomTypeName;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }
}
