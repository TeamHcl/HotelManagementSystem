package com.hcl.backend_template.facility.dto;

import com.hcl.backend_template.facility.entity.Facility;
import com.hcl.backend_template.facility.entity.FacilityCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FacilityResponse {

  private final Long id;
  private final String name;
  private final FacilityCategory category;

  public static FacilityResponse from(Facility facility) {
    return new FacilityResponse(facility.getId(), facility.getName(), facility.getCategory());
  }
}
