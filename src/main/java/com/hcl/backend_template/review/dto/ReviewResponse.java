package com.hcl.backend_template.review.dto;

import com.hcl.backend_template.review.entity.Review;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponse {

  private final Long id;
  private final Long userId;
  private final Long hotelId;
  private final Integer rating;
  private final String comment;
  private final OffsetDateTime createdAt;

  public static ReviewResponse from(Review review) {
    return new ReviewResponse(
        review.getId(),
        review.getUserId(),
        review.getHotelId(),
        review.getRating(),
        review.getComment(),
        review.getCreatedAt());
  }
}
