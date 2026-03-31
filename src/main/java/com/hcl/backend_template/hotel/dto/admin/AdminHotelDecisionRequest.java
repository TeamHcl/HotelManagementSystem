package com.hcl.backend_template.hotel.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHotelDecisionRequest {

  @NotNull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private AdminHotelDecision decision;
}
