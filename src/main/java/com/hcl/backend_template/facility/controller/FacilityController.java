package com.hcl.backend_template.facility.controller;

import com.hcl.backend_template.facility.dto.FacilityResponse;
import com.hcl.backend_template.facility.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
@Tag(name = "Facilities", description = "Public facilities catalog")
public class FacilityController {

  private final FacilityService facilityService;

  @GetMapping
  @Operation(summary = "List all facilities")
  public List<FacilityResponse> list() {
    return facilityService.listAll().stream().map(FacilityResponse::from).toList();
  }
}
