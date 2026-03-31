package com.hcl.backend_template.hotel.controller.admin;

import com.hcl.backend_template.hotel.dto.HotelResponse;
import com.hcl.backend_template.hotel.dto.admin.AdminHotelDecisionRequest;
import com.hcl.backend_template.hotel.service.admin.AdminHotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotels (Admin)", description = "Admin review and approval endpoints")
@SecurityRequirement(name = "BearerAuth")
public class AdminHotelController {

  private final AdminHotelService adminHotelService;

  @GetMapping("/review")
  @Operation(summary = "List hotels pending admin review")
  public List<HotelResponse> listForReview() {
    return adminHotelService.listForReview().stream().map(HotelResponse::from).toList();
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Approve or reject a hotel",
      description =
          "Submit an explicit decision for a hotel. APPROVE activates the hotel only if required documents are verified; REJECT marks it rejected.")
  public HotelResponse decide(
      @PathVariable("id") Long hotelId,
      @Valid
          @org.springframework.web.bind.annotation.RequestBody
          @RequestBody(
              required = true,
              content =
                  @Content(
                      schema =
                          @Schema(
                              implementation = AdminHotelDecisionRequest.class,
                              requiredMode = Schema.RequiredMode.REQUIRED)))
          AdminHotelDecisionRequest request) {
    return HotelResponse.from(adminHotelService.decide(hotelId, request.getDecision()));
  }
}
