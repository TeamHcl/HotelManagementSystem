package com.hcl.backend_template.hotel.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Admin decision for a hotel review")
public enum AdminHotelDecision {
  @Schema(description = "Approve hotel (becomes ACTIVE only if all documents are VERIFIED)")
  APPROVE,

  @Schema(description = "Reject hotel")
  REJECT
}
