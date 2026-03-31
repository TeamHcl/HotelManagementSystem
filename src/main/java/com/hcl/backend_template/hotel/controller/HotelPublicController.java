package com.hcl.backend_template.hotel.controller;

import com.hcl.backend_template.hotel.dto.HotelPublicResponse;
import com.hcl.backend_template.hotel.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotels (Public)", description = "Public hotel read endpoints")
public class HotelPublicController {

  private final HotelService hotelService;

  @GetMapping("/{id}")
  @Operation(summary = "Get an ACTIVE hotel by id")
  public HotelPublicResponse getById(@PathVariable("id") Long hotelId) {
    return HotelPublicResponse.from(hotelService.getActiveHotelOrThrow(hotelId));
  }

  @GetMapping
  @Operation(summary = "List ACTIVE hotels")
  public List<HotelPublicResponse> listActive() {
    return hotelService.listActiveHotels().stream().map(HotelPublicResponse::from).toList();
  }
}
