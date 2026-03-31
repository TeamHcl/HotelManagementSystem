package com.hcl.backend_template.room.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public class InventoryBulkRequest {

    @NotNull
    @Schema(example = "10", description = "Room type ID to which inventory applies")
    private Long roomTypeId;

    @NotNull
    @FutureOrPresent
    @Schema(example = "2026-04-01")
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    @Schema(example = "2026-04-10", description = "Exclusive end date (not included)")
    private LocalDate endDate;

    @NotNull
    @Min(0)
    @Schema(example = "5", description = "Total rooms available per night in the range")
    private Integer totalRooms;

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
    }
}
