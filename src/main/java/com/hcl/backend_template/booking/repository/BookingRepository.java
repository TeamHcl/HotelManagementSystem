package com.hcl.backend_template.booking.repository;

import com.hcl.backend_template.booking.entity.Booking;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  boolean existsByReservationNumber(String reservationNumber);

  List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

  Optional<Booking> findByIdAndUserId(Long id, Long userId);
}
