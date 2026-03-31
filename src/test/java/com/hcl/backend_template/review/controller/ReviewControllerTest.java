package com.hcl.backend_template.review.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hcl.backend_template.common.error.GlobalExceptionHandler;
import com.hcl.backend_template.review.entity.Review;
import com.hcl.backend_template.review.service.ReviewService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

  @Mock private ReviewService reviewService;

  @InjectMocks private ReviewController reviewController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();

    mockMvc =
        MockMvcBuilders.standaloneSetup(reviewController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .setValidator(validator)
            .build();
  }

  @Test
  void createReturnsCreatedReview() throws Exception {
    Review review = new Review();
    review.setId(1L);
    review.setUserId(10L);
    review.setHotelId(7L);
    review.setRating(5);
    review.setComment("Great stay");

    when(reviewService.create(any())).thenReturn(review);

    mockMvc
        .perform(
            post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"hotelId\":7,\"rating\":5,\"comment\":\"Great stay\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.hotelId").value(7L))
        .andExpect(jsonPath("$.rating").value(5));
  }

  @Test
  void listByHotelReturnsReviews() throws Exception {
    Review review = new Review();
    review.setId(1L);
    review.setUserId(10L);
    review.setHotelId(7L);
    review.setRating(5);
    review.setComment("Great stay");

    when(reviewService.listByHotel(eq(7L))).thenReturn(List.of(review));

    mockMvc
        .perform(get("/hotels/{id}/reviews", 7))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].hotelId").value(7L));
  }

  @Test
  void createFailsValidationWhenRatingMissing() throws Exception {
    mockMvc
        .perform(
            post("/reviews").contentType(MediaType.APPLICATION_JSON).content("{\"hotelId\":7}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Validation Error"));
  }
}
