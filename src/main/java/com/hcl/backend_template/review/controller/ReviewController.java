package com.hcl.backend_template.review.controller;

import com.hcl.backend_template.review.dto.CreateReviewRequest;
import com.hcl.backend_template.review.dto.ReviewResponse;
import com.hcl.backend_template.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Hotel review endpoints")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping("/reviews")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a review")
  @SecurityRequirement(name = "BearerAuth")
  public ReviewResponse create(@Valid @RequestBody CreateReviewRequest request) {
    return ReviewResponse.from(reviewService.create(request));
  }

  @GetMapping("/hotels/{id}/reviews")
  @Operation(
      summary = "List reviews for a hotel",
      description = "Public endpoint.",
      security = {})
  public List<ReviewResponse> listByHotel(@PathVariable("id") Long hotelId) {
    return reviewService.listByHotel(hotelId).stream().map(ReviewResponse::from).toList();
  }
}
