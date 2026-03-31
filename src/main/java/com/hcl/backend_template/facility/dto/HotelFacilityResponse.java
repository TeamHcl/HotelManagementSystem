package com.hcl.backend_template.facility.dto;

import com.hcl.backend_template.facility.entity.FacilityCategory;
import com.hcl.backend_template.facility.entity.FacilityValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HotelFacilityResponse {

  private final Long facilityId;
  private final String facilityName;
  private final FacilityCategory category;
  private final FacilityValue value;
}
