package com.hcl.backend_template.hotel.controller;

import com.hcl.backend_template.facility.dto.HotelFacilityResponse;
import com.hcl.backend_template.facility.dto.UpsertHotelFacilitiesRequest;
import com.hcl.backend_template.facility.service.HotelFacilityService;
import com.hcl.backend_template.hotel.dto.CreateHotelRequest;
import com.hcl.backend_template.hotel.dto.HotelResponse;
import com.hcl.backend_template.hotel.dto.UpdateHotelRequest;
import com.hcl.backend_template.hotel.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotels (Manager)", description = "Hotel onboarding endpoints for managers")
@SecurityRequirement(name = "BearerAuth")
public class HotelManagerController {

  private final HotelService hotelService;
  private final HotelFacilityService hotelFacilityService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a hotel (Manager)")
  public HotelResponse create(@Valid @RequestBody CreateHotelRequest request) {
    return HotelResponse.from(hotelService.create(request));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a hotel owned by current manager")
  public HotelResponse update(
      @PathVariable("id") Long hotelId, @Valid @RequestBody UpdateHotelRequest request) {
    return HotelResponse.from(hotelService.updateMyHotel(hotelId, request));
  }

  @GetMapping("/my")
  @Operation(summary = "List hotels owned by current manager")
  public List<HotelResponse> myHotels() {
    return hotelService.listMyHotels().stream().map(HotelResponse::from).toList();
  }

  @PostMapping("/{id}/facilities")
  @Operation(summary = "Upsert facilities for a hotel owned by current manager")
  public List<HotelFacilityResponse> upsertFacilities(
      @PathVariable("id") Long hotelId, @Valid @RequestBody UpsertHotelFacilitiesRequest request) {
    return hotelFacilityService.upsertMyHotelFacilities(hotelId, request);
  }

  @GetMapping("/{id}/facilities")
  @Operation(summary = "Get facilities for a hotel owned by current manager")
  public List<HotelFacilityResponse> listFacilities(@PathVariable("id") Long hotelId) {
    return hotelFacilityService.listMyHotelFacilities(hotelId);
  }
}
