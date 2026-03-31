package com.hcl.backend_template.room.controller;

import com.hcl.backend_template.room.dto.AvailabilityResponse;
import com.hcl.backend_template.room.dto.SearchResultItem;
import com.hcl.backend_template.room.service.RoomSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Room & Inventory & Search", description = "Room search and availability")
public class RoomSearchController {

  private final RoomSearchService roomSearchService;

  @GetMapping("/search")
  @Operation(
      summary = "Search available room types for a hotel",
      description =
          "Searches room types for a given hotel and date range, filtering by guest capacity and availability.")
  public List<SearchResultItem> search(
      @Parameter(example = "1") @RequestParam("hotelId") Long hotelId,
      @Parameter(example = "2026-04-01")
          @RequestParam("checkIn")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate checkIn,
      @Parameter(example = "2026-04-04")
          @RequestParam("checkOut")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate checkOut,
      @Parameter(example = "2") @RequestParam(value = "guests", defaultValue = "1") int guests) {
    return roomSearchService.search(hotelId, checkIn, checkOut, guests);
  }

  @GetMapping("/availability")
  @Operation(summary = "Check availability and price for a room type")
  public AvailabilityResponse availability(
      @Parameter(example = "10") @RequestParam("roomTypeId") Long roomTypeId,
      @Parameter(example = "2026-04-01")
          @RequestParam("checkIn")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate checkIn,
      @Parameter(example = "2026-04-04")
          @RequestParam("checkOut")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate checkOut) {
    return roomSearchService.checkAvailability(roomTypeId, checkIn, checkOut);
  }
}
