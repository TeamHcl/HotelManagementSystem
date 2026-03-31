package com.hcl.backend_template.facility.service;

import com.hcl.backend_template.facility.entity.Facility;
import com.hcl.backend_template.facility.repository.FacilityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService {

  private final FacilityRepository facilityRepository;

  public List<Facility> listAll() {
    return facilityRepository.findAll();
  }
}
