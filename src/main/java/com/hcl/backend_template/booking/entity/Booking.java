package com.hcl.backend_template.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "bookings",
    indexes = {
      @Index(name = "idx_bookings_user_id", columnList = "user_id"),
      @Index(name = "idx_bookings_room_id", columnList = "room_id"),
      @Index(name = "idx_bookings_room_type_id", columnList = "room_type_id")
    })
@Getter
@Setter
@NoArgsConstructor
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "reservation_number", nullable = false, length = 50, unique = true)
  private String reservationNumber;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "room_id")
  private Long roomId;

  @Column(name = "room_type_id")
  private Long roomTypeId;

  @Column(name = "check_in", nullable = false)
  private LocalDate checkIn;

  @Column(name = "check_out", nullable = false)
  private LocalDate checkOut;

  @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal totalPrice;

  @Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal discountAmount = BigDecimal.ZERO;

  @Column(name = "final_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal finalPrice = BigDecimal.ZERO;

  @Column(name = "promotion_id")
  private Long promotionId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private BookingStatus status = BookingStatus.PENDING;

  @Column(name = "parent_booking_id")
  private Long parentBookingId;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private OffsetDateTime createdAt;
}
