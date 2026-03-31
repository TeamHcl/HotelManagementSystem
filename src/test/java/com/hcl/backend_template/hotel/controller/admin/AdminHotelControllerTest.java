package com.hcl.backend_template.hotel.controller.admin;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.backend_template.common.error.GlobalExceptionHandler;
import com.hcl.backend_template.hotel.dto.admin.AdminHotelDecision;
import com.hcl.backend_template.hotel.dto.admin.AdminHotelDecisionRequest;
import com.hcl.backend_template.hotel.entity.Hotel;
import com.hcl.backend_template.hotel.entity.HotelStatus;
import com.hcl.backend_template.hotel.service.admin.AdminHotelService;
import java.math.BigDecimal;
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
class AdminHotelControllerTest {

  @Mock private AdminHotelService adminHotelService;

  @InjectMocks private AdminHotelController adminHotelController;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();

    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();

    mockMvc =
        MockMvcBuilders.standaloneSetup(adminHotelController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .setValidator(validator)
            .build();
  }

  @Test
  void listForReviewReturnsHotels() throws Exception {
    Hotel hotel = new Hotel();
    hotel.setId(10L);
    hotel.setName("Hotel One");
    hotel.setLocation("Bengaluru");
    hotel.setDescription("Nice");
    hotel.setManagerId(99L);
    hotel.setStatus(HotelStatus.PENDING);
    hotel.setAverageRating(new BigDecimal("4.25"));

    when(adminHotelService.listForReview()).thenReturn(List.of(hotel));

    mockMvc
        .perform(get("/admin/hotels/review"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(10L))
        .andExpect(jsonPath("$[0].name").value("Hotel One"))
        .andExpect(jsonPath("$[0].location").value("Bengaluru"))
        .andExpect(jsonPath("$[0].status").value("PENDING"));
  }

  @Test
  void decideReturnsUpdatedHotel() throws Exception {
    Hotel hotel = new Hotel();
    hotel.setId(10L);
    hotel.setName("Hotel One");
    hotel.setLocation("Bengaluru");
    hotel.setManagerId(99L);
    hotel.setStatus(HotelStatus.ACTIVE);
    hotel.setAverageRating(new BigDecimal("4.25"));

    when(adminHotelService.decide(eq(10L), eq(AdminHotelDecision.APPROVE))).thenReturn(hotel);

    AdminHotelDecisionRequest request = new AdminHotelDecisionRequest();
    request.setDecision(AdminHotelDecision.APPROVE);

    mockMvc
        .perform(
            put("/admin/hotels/{id}", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10L))
        .andExpect(jsonPath("$.status").value("ACTIVE"));
  }

  @Test
  void decideFailsValidationWhenDecisionMissing() throws Exception {
    mockMvc
        .perform(
            put("/admin/hotels/{id}", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Validation Error"))
        .andExpect(jsonPath("$.errors.decision").exists());
  }
}
