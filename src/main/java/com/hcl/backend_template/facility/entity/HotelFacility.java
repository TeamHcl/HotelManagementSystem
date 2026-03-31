package com.hcl.backend_template.facility.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "hotel_facilities",
    indexes = {
      @Index(name = "idx_hotel_facilities_hotel_id", columnList = "hotel_id"),
      @Index(name = "idx_hotel_facilities_facility_id", columnList = "facility_id")
    })
@Getter
@Setter
@NoArgsConstructor
public class HotelFacility {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "hotel_id", nullable = false)
  private Long hotelId;

  @Column(name = "facility_id", nullable = false)
  private Long facilityId;

  @Enumerated(EnumType.STRING)
  @Column(name = "value", nullable = false, length = 20)
  private FacilityValue value;
}
