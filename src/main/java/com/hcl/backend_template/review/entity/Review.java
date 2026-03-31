package com.hcl.backend_template.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "reviews",
    indexes = {
      @Index(name = "idx_reviews_hotel_id", columnList = "hotel_id"),
      @Index(name = "idx_reviews_user_id", columnList = "user_id")
    })
@Getter
@Setter
@NoArgsConstructor
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "hotel_id", nullable = false)
  private Long hotelId;

  @Column(name = "rating", nullable = false)
  private Integer rating;

  @Column(name = "comment", columnDefinition = "TEXT")
  private String comment;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private OffsetDateTime createdAt;
}
