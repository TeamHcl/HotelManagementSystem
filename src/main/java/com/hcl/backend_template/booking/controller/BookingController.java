package com.hcl.backend_template.booking.controller;

import com.hcl.backend_template.booking.dto.BookingResponse;
import com.hcl.backend_template.booking.dto.CreateBookingRequest;
import com.hcl.backend_template.booking.dto.RebookBookingRequest;
import com.hcl.backend_template.booking.service.BookingService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Booking endpoints for customers")
@SecurityRequirement(name = "BearerAuth")
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a booking")
  public BookingResponse create(@Valid @RequestBody CreateBookingRequest request) {
    return BookingResponse.from(bookingService.create(request));
  }

  @GetMapping("/my")
  @Operation(summary = "List bookings for current customer")
  public List<BookingResponse> myBookings() {
    return bookingService.listMyBookings().stream().map(BookingResponse::from).toList();
  }

  @PutMapping("/{id}/cancel")
  @Operation(summary = "Cancel a booking owned by current customer")
  public BookingResponse cancel(@PathVariable("id") Long bookingId) {
    return BookingResponse.from(bookingService.cancelMyBooking(bookingId));
  }

  @PostMapping("/{id}/rebook")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Rebook based on a previous booking")
  public BookingResponse rebook(
      @PathVariable("id") Long bookingId, @Valid @RequestBody RebookBookingRequest request) {
    return BookingResponse.from(bookingService.rebookMyBooking(bookingId, request));
  }
}
