package com.hcl.backend_template.facility.repository;

import com.hcl.backend_template.facility.entity.HotelFacility;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelFacilityRepository extends JpaRepository<HotelFacility, Long> {

  List<HotelFacility> findByHotelId(Long hotelId);

  Optional<HotelFacility> findByHotelIdAndFacilityId(Long hotelId, Long facilityId);
}
