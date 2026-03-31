package com.hcl.backend_template.facility.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertHotelFacilitiesRequest {

  @NotEmpty @Valid private List<UpsertHotelFacilityItem> facilities;
}
