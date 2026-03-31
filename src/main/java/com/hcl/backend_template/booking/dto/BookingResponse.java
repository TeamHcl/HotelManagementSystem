package com.hcl.backend_template.booking.dto;

import com.hcl.backend_template.booking.entity.Booking;
import com.hcl.backend_template.booking.entity.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookingResponse {

  private final Long id;
  private final String reservationNumber;
  private final Long userId;
  private final Long roomId;
  private final Long roomTypeId;
  private final LocalDate checkIn;
  private final LocalDate checkOut;
  private final BigDecimal totalPrice;
  private final BigDecimal discountAmount;
  private final BigDecimal finalPrice;
  private final Long promotionId;
  private final BookingStatus status;
  private final Long parentBookingId;
  private final OffsetDateTime createdAt;

  public static BookingResponse from(Booking booking) {
    return new BookingResponse(
        booking.getId(),
        booking.getReservationNumber(),
        booking.getUserId(),
        booking.getRoomId(),
        booking.getRoomTypeId(),
        booking.getCheckIn(),
        booking.getCheckOut(),
        booking.getTotalPrice(),
        booking.getDiscountAmount(),
        booking.getFinalPrice(),
        booking.getPromotionId(),
        booking.getStatus(),
        booking.getParentBookingId(),
        booking.getCreatedAt());
  }
}
