package com.hcl.backend_template.facility.bootstrap;

import com.hcl.backend_template.facility.entity.Facility;
import com.hcl.backend_template.facility.entity.FacilityCategory;
import com.hcl.backend_template.facility.repository.FacilityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DevFacilitySeeder implements ApplicationRunner {

  private final FacilityRepository facilityRepository;

  @Value("${app.environment:local}")
  private String appEnvironment;

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    if (!"local".equalsIgnoreCase(appEnvironment)) {
      return;
    }

    if (facilityRepository.count() > 0) {
      return;
    }

    List<Facility> facilities =
        List.of(
            build("Wifi", FacilityCategory.BASIC),
            build("Breakfast", FacilityCategory.BASIC),
            build("Parking", FacilityCategory.BASIC),
            build("Pool", FacilityCategory.PREMIUM),
            build("Gym", FacilityCategory.PREMIUM),
            build("Spa", FacilityCategory.PREMIUM),
            build("Room Service", FacilityCategory.ROOM),
            build("Air Conditioning", FacilityCategory.ROOM),
            build("Mini Bar", FacilityCategory.ROOM));

    facilityRepository.saveAll(facilities);
  }

  private Facility build(String name, FacilityCategory category) {
    Facility facility = new Facility();
    facility.setName(name);
    facility.setCategory(category);
    return facility;
  }
}
