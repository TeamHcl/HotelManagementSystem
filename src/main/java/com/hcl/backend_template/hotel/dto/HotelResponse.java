package com.hcl.backend_template.hotel.dto;

import com.hcl.backend_template.hotel.entity.Hotel;
import com.hcl.backend_template.hotel.entity.HotelStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HotelResponse {

  private final Long id;
  private final String name;
  private final String location;
  private final String description;
  private final Long managerId;
  private final HotelStatus status;
  private final BigDecimal averageRating;
  private final OffsetDateTime createdAt;

  public static HotelResponse from(Hotel hotel) {
    return new HotelResponse(
        hotel.getId(),
        hotel.getName(),
        hotel.getLocation(),
        hotel.getDescription(),
        hotel.getManagerId(),
        hotel.getStatus(),
        hotel.getAverageRating(),
        hotel.getCreatedAt());
  }
}
