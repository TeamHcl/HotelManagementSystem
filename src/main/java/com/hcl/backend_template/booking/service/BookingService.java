package com.hcl.backend_template.booking.service;

import com.hcl.backend_template.booking.dto.CreateBookingRequest;
import com.hcl.backend_template.booking.dto.RebookBookingRequest;
import com.hcl.backend_template.booking.entity.Booking;
import com.hcl.backend_template.booking.entity.BookingStatus;
import com.hcl.backend_template.booking.repository.BookingRepository;
import com.hcl.backend_template.common.security.CurrentUser;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BookingService {

  private final BookingRepository bookingRepository;
  private final CurrentUser currentUser;

  public Booking create(CreateBookingRequest request) {
    validateBookingInput(
        request.getRoomId(),
        request.getRoomTypeId(),
        request.getCheckIn(),
        request.getCheckOut(),
        request.getTotalPrice());

    BigDecimal discountAmount = defaultMoney(request.getDiscountAmount());
    BigDecimal finalPrice = request.getTotalPrice().subtract(discountAmount);
    if (finalPrice.signum() < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Final price cannot be negative");
    }

    Booking booking = new Booking();
    booking.setReservationNumber(generateReservationNumber());
    booking.setUserId(currentUser.requireUserId());
    booking.setRoomId(request.getRoomId());
    booking.setRoomTypeId(request.getRoomTypeId());
    booking.setCheckIn(request.getCheckIn());
    booking.setCheckOut(request.getCheckOut());
    booking.setTotalPrice(request.getTotalPrice());
    booking.setDiscountAmount(discountAmount);
    booking.setFinalPrice(finalPrice);
    booking.setPromotionId(request.getPromotionId());
    booking.setStatus(BookingStatus.CONFIRMED);

    return bookingRepository.save(booking);
  }

  public List<Booking> listMyBookings() {
    return bookingRepository.findByUserIdOrderByCreatedAtDesc(currentUser.requireUserId());
  }

  public Booking cancelMyBooking(Long bookingId) {
    Booking booking =
        bookingRepository
            .findByIdAndUserId(bookingId, currentUser.requireUserId())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

    if (booking.getStatus() == BookingStatus.COMPLETED) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Completed booking cannot be cancelled");
    }

    booking.setStatus(BookingStatus.CANCELLED);
    return bookingRepository.save(booking);
  }

  public Booking rebookMyBooking(Long bookingId, RebookBookingRequest request) {
    Booking parent =
        bookingRepository
            .findByIdAndUserId(bookingId, currentUser.requireUserId())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

    validateBookingInput(
        parent.getRoomId(),
        parent.getRoomTypeId(),
        request.getCheckIn(),
        request.getCheckOut(),
        request.getTotalPrice() == null ? parent.getTotalPrice() : request.getTotalPrice());

    BigDecimal totalPrice =
        request.getTotalPrice() == null ? parent.getTotalPrice() : request.getTotalPrice();
    BigDecimal discountAmount =
        request.getDiscountAmount() == null
            ? parent.getDiscountAmount()
            : request.getDiscountAmount();
    BigDecimal finalPrice = totalPrice.subtract(defaultMoney(discountAmount));
    if (finalPrice.signum() < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Final price cannot be negative");
    }

    Booking booking = new Booking();
    booking.setReservationNumber(generateReservationNumber());
    booking.setUserId(currentUser.requireUserId());
    booking.setRoomId(parent.getRoomId());
    booking.setRoomTypeId(parent.getRoomTypeId());
    booking.setCheckIn(request.getCheckIn());
    booking.setCheckOut(request.getCheckOut());
    booking.setTotalPrice(totalPrice);
    booking.setDiscountAmount(defaultMoney(discountAmount));
    booking.setFinalPrice(finalPrice);
    booking.setPromotionId(
        request.getPromotionId() == null ? parent.getPromotionId() : request.getPromotionId());
    booking.setStatus(BookingStatus.CONFIRMED);
    booking.setParentBookingId(parent.getId());

    return bookingRepository.save(booking);
  }

  private void validateBookingInput(
      Long roomId,
      Long roomTypeId,
      java.time.LocalDate checkIn,
      java.time.LocalDate checkOut,
      BigDecimal totalPrice) {
    if (roomId == null && roomTypeId == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Either roomId or roomTypeId must be provided");
    }
    if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "checkOut must be after checkIn");
    }
    if (totalPrice == null || totalPrice.signum() < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "totalPrice must be >= 0");
    }
  }

  private BigDecimal defaultMoney(BigDecimal value) {
    return value == null ? BigDecimal.ZERO : value;
  }

  private String generateReservationNumber() {
    String reservationNumber;
    int attempts = 0;
    do {
      if (attempts++ > 5) {
        throw new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "Could not generate reservation number");
      }
      reservationNumber = "RSV-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    } while (bookingRepository.existsByReservationNumber(reservationNumber));

    return reservationNumber;
  }
}
