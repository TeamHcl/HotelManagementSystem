package com.hcl.backend_template.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateHotelRequest {

  @NotBlank
  @Size(max = 150)
  private String name;

  @NotBlank
  @Size(max = 255)
  private String location;

  @Size(max = 5000)
  private String description;
}
