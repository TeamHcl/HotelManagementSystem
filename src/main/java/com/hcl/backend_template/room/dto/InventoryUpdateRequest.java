package com.hcl.backend_template.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class InventoryUpdateRequest {

  @NotNull
  @Schema(example = "10", description = "Room type ID whose inventory is being updated")
  private Long roomTypeId;

  @NotNull
  @Schema(example = "2026-04-05", description = "Date for which inventory is updated")
  private LocalDate date;

  @NotNull
  @Min(0)
  @Schema(example = "8", description = "New total number of rooms for that day")
  private Integer totalRooms;

  public Long getRoomTypeId() {
    return roomTypeId;
  }

  public void setRoomTypeId(Long roomTypeId) {
    this.roomTypeId = roomTypeId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Integer getTotalRooms() {
    return totalRooms;
  }

  public void setTotalRooms(Integer totalRooms) {
    this.totalRooms = totalRooms;
  }
}
