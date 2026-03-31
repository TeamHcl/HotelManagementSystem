package com.hcl.backend_template.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public class SearchResultItem {

  @Schema(example = "10")
  private Long roomTypeId;

  @Schema(example = "1")
  private Long hotelId;

  @Schema(example = "Deluxe King Room")
  private String roomTypeName;

  @Schema(example = "2")
  private Integer capacity;

  @Schema(description = "Total price for the full stay", example = "10500.00")
  private BigDecimal totalPrice;

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

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }
}
