package com.hcl.backend_template.facility.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hcl.backend_template.facility.entity.Facility;
import com.hcl.backend_template.facility.entity.FacilityCategory;
import com.hcl.backend_template.facility.service.FacilityService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class FacilityControllerTest {

  @Mock private FacilityService facilityService;

  @InjectMocks private FacilityController facilityController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(facilityController).build();
  }

  @Test
  void listReturnsFacilities() throws Exception {
    Facility facility = new Facility();
    facility.setId(1L);
    facility.setName("WiFi");
    facility.setCategory(FacilityCategory.BASIC);

    when(facilityService.listAll()).thenReturn(List.of(facility));

    mockMvc
        .perform(get("/facilities"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("WiFi"))
        .andExpect(jsonPath("$[0].category").value("BASIC"));
  }
}
