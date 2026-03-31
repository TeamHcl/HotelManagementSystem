package com.hcl.backend_template.hotel.repository;

import com.hcl.backend_template.hotel.entity.Hotel;
import com.hcl.backend_template.hotel.entity.HotelStatus;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

  List<Hotel> findByManagerIdOrderByCreatedAtDesc(Long managerId);

  List<Hotel> findByStatusInOrderByCreatedAtDesc(Collection<HotelStatus> statuses);
}
