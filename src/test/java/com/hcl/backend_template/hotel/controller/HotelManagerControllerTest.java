package com.hcl.backend_template.hotel.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.backend_template.common.error.GlobalExceptionHandler;
import com.hcl.backend_template.facility.dto.HotelFacilityResponse;
import com.hcl.backend_template.facility.dto.UpsertHotelFacilitiesRequest;
import com.hcl.backend_template.facility.entity.FacilityCategory;
import com.hcl.backend_template.facility.entity.FacilityValue;
import com.hcl.backend_template.facility.service.HotelFacilityService;
import com.hcl.backend_template.hotel.dto.CreateHotelRequest;
import com.hcl.backend_template.hotel.dto.UpdateHotelRequest;
import com.hcl.backend_template.hotel.entity.Hotel;
import com.hcl.backend_template.hotel.entity.HotelStatus;
import com.hcl.backend_template.hotel.service.HotelService;
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
class HotelManagerControllerTest {

  @Mock private HotelService hotelService;

  @Mock private HotelFacilityService hotelFacilityService;

  @InjectMocks private HotelManagerController hotelManagerController;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();

    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();

    mockMvc =
        MockMvcBuilders.standaloneSetup(hotelManagerController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .setValidator(validator)
            .build();
  }

  @Test
  void createReturnsCreatedHotel() throws Exception {
    Hotel created = new Hotel();
    created.setId(123L);
    created.setName("My Hotel");
    created.setLocation("Chennai");
    created.setDescription("Desc");
    created.setManagerId(88L);
    created.setStatus(HotelStatus.PENDING);
    created.setAverageRating(BigDecimal.ZERO);

    when(hotelService.create(any())).thenReturn(created);

    CreateHotelRequest request = new CreateHotelRequest();
    request.setName("My Hotel");
    request.setLocation("Chennai");
    request.setDescription("Desc");

    mockMvc
        .perform(
            post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(123L))
        .andExpect(jsonPath("$.name").value("My Hotel"))
        .andExpect(jsonPath("$.status").value("PENDING"));
  }

  @Test
  void createFailsValidationWhenNameMissing() throws Exception {
    mockMvc
        .perform(
            post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"location\":\"Chennai\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Validation Error"))
        .andExpect(jsonPath("$.errors.name").exists());
  }

  @Test
  void updateReturnsUpdatedHotel() throws Exception {
    Hotel updated = new Hotel();
    updated.setId(123L);
    updated.setName("Updated Hotel");
    updated.setLocation("Chennai");
    updated.setManagerId(88L);
    updated.setStatus(HotelStatus.PENDING);
    updated.setAverageRating(BigDecimal.ZERO);

    when(hotelService.updateMyHotel(eq(123L), any())).thenReturn(updated);

    UpdateHotelRequest request = new UpdateHotelRequest();
    request.setName("Updated Hotel");
    request.setLocation("Chennai");

    mockMvc
        .perform(
            put("/hotels/{id}", 123)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(123L))
        .andExpect(jsonPath("$.name").value("Updated Hotel"));
  }

  @Test
  void myHotelsReturnsList() throws Exception {
    Hotel hotel = new Hotel();
    hotel.setId(1L);
    hotel.setName("Hotel A");
    hotel.setLocation("Mumbai");
    hotel.setManagerId(88L);
    hotel.setStatus(HotelStatus.PENDING);
    hotel.setAverageRating(BigDecimal.ZERO);

    when(hotelService.listMyHotels()).thenReturn(List.of(hotel));

    mockMvc
        .perform(get("/hotels/my"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Hotel A"));
  }

  @Test
    void upsertFacilitiesFailsValidationWhenEmpty() throws Exception {
    UpsertHotelFacilitiesRequest request = new UpsertHotelFacilitiesRequest();
    request.setFacilities(List.of());

    mockMvc
        .perform(
            post("/hotels/{id}/facilities", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Validation Error"))
        .andExpect(jsonPath("$.errors.facilities").exists());
  }

  @Test
  void listFacilitiesReturnsFacilities() throws Exception {
    List<HotelFacilityResponse> response =
        List.of(
            new HotelFacilityResponse(
                7L, "WiFi", FacilityCategory.BASIC, FacilityValue.AVAILABLE));

    when(hotelFacilityService.listMyHotelFacilities(1L)).thenReturn(response);

    mockMvc
        .perform(get("/hotels/{id}/facilities", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].facilityId").value(7L))
        .andExpect(jsonPath("$[0].facilityName").value("WiFi"))
        .andExpect(jsonPath("$[0].value").value("AVAILABLE"));
  }
}
