package com.hcl.backend_template.booking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.backend_template.booking.entity.Booking;
import com.hcl.backend_template.booking.entity.BookingStatus;
import com.hcl.backend_template.booking.service.BookingService;
import com.hcl.backend_template.common.error.GlobalExceptionHandler;
import java.math.BigDecimal;
import java.time.LocalDate;
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
class BookingControllerTest {

  @Mock private BookingService bookingService;

  @InjectMocks private BookingController bookingController;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();

    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();

    mockMvc =
        MockMvcBuilders.standaloneSetup(bookingController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .setValidator(validator)
            .build();
  }

  @Test
  void createReturnsCreatedBooking() throws Exception {
    Booking booking = new Booking();
    booking.setId(1L);
    booking.setReservationNumber("RSV-123456789012");
    booking.setUserId(10L);
    booking.setRoomId(5L);
    booking.setCheckIn(LocalDate.parse("2026-04-10"));
    booking.setCheckOut(LocalDate.parse("2026-04-12"));
    booking.setTotalPrice(new BigDecimal("2000.00"));
    booking.setDiscountAmount(BigDecimal.ZERO);
    booking.setFinalPrice(new BigDecimal("2000.00"));
    booking.setStatus(BookingStatus.CONFIRMED);

    when(bookingService.create(any())).thenReturn(booking);

    mockMvc
        .perform(
            post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"roomId\":5,\"checkIn\":\"2026-04-10\",\"checkOut\":\"2026-04-12\",\"totalPrice\":2000}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.reservationNumber").value("RSV-123456789012"))
        .andExpect(jsonPath("$.status").value("CONFIRMED"));
  }

  @Test
  void myBookingsReturnsList() throws Exception {
    Booking booking = new Booking();
    booking.setId(1L);
    booking.setReservationNumber("RSV-123456789012");
    booking.setUserId(10L);
    booking.setRoomId(5L);
    booking.setCheckIn(LocalDate.parse("2026-04-10"));
    booking.setCheckOut(LocalDate.parse("2026-04-12"));
    booking.setTotalPrice(new BigDecimal("2000.00"));
    booking.setDiscountAmount(BigDecimal.ZERO);
    booking.setFinalPrice(new BigDecimal("2000.00"));
    booking.setStatus(BookingStatus.CONFIRMED);

    when(bookingService.listMyBookings()).thenReturn(List.of(booking));

    mockMvc
        .perform(get("/bookings/my"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].reservationNumber").value("RSV-123456789012"));
  }

  @Test
  void cancelReturnsUpdatedBooking() throws Exception {
    Booking booking = new Booking();
    booking.setId(1L);
    booking.setReservationNumber("RSV-123456789012");
    booking.setUserId(10L);
    booking.setStatus(BookingStatus.CANCELLED);

    when(bookingService.cancelMyBooking(eq(1L))).thenReturn(booking);

    mockMvc
        .perform(put("/bookings/{id}/cancel", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("CANCELLED"));
  }

  @Test
  void rebookReturnsCreatedBooking() throws Exception {
    Booking booking = new Booking();
    booking.setId(2L);
    booking.setReservationNumber("RSV-REBOOKED1234");
    booking.setUserId(10L);
    booking.setParentBookingId(1L);
    booking.setStatus(BookingStatus.CONFIRMED);

    when(bookingService.rebookMyBooking(eq(1L), any())).thenReturn(booking);

    mockMvc
        .perform(
            post("/bookings/{id}/rebook", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"checkIn\":\"2026-05-01\",\"checkOut\":\"2026-05-03\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(2L))
        .andExpect(jsonPath("$.parentBookingId").value(1L));
  }

  @Test
  void rebookFailsValidationWhenDatesMissing() throws Exception {
    mockMvc
        .perform(
            post("/bookings/{id}/rebook", 1).contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Validation Error"));
  }
}
