package com.hcl.backend_template.review.repository;

import com.hcl.backend_template.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByHotelIdOrderByCreatedAtDesc(Long hotelId);
}
