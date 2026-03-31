package com.hcl.backend_template.facility.dto;

import com.hcl.backend_template.facility.entity.FacilityValue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertHotelFacilityItem {

  @NotNull private Long facilityId;

  @NotNull private FacilityValue value;
}
