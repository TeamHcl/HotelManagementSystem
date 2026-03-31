package com.hcl.backend_template.facility.repository;

import com.hcl.backend_template.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {}
