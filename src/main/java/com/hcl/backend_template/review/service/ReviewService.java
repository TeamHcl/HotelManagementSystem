package com.hcl.backend_template.review.service;

import com.hcl.backend_template.common.security.CurrentUser;
import com.hcl.backend_template.review.dto.CreateReviewRequest;
import com.hcl.backend_template.review.entity.Review;
import com.hcl.backend_template.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final CurrentUser currentUser;

  public Review create(CreateReviewRequest request) {
    Review review = new Review();
    review.setUserId(currentUser.requireUserId());
    review.setHotelId(request.getHotelId());
    review.setRating(request.getRating());
    review.setComment(request.getComment());
    return reviewRepository.save(review);
  }

  public List<Review> listByHotel(Long hotelId) {
    return reviewRepository.findByHotelIdOrderByCreatedAtDesc(hotelId);
  }
}
