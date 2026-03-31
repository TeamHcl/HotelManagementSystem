package com.hcl.backend_template.hotel.dto;

import com.hcl.backend_template.hotel.entity.Hotel;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HotelPublicResponse {

  private final Long id;
  private final String name;
  private final String location;
  private final String description;
  private final BigDecimal averageRating;

  public static HotelPublicResponse from(Hotel hotel) {
    return new HotelPublicResponse(
        hotel.getId(),
        hotel.getName(),
        hotel.getLocation(),
        hotel.getDescription(),
        hotel.getAverageRating());
  }
}
