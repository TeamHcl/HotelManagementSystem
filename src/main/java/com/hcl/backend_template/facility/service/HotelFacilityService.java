package com.hcl.backend_template.facility.service;

import com.hcl.backend_template.facility.dto.HotelFacilityResponse;
import com.hcl.backend_template.facility.dto.UpsertHotelFacilitiesRequest;
import com.hcl.backend_template.facility.entity.Facility;
import com.hcl.backend_template.facility.entity.HotelFacility;
import com.hcl.backend_template.facility.repository.FacilityRepository;
import com.hcl.backend_template.facility.repository.HotelFacilityRepository;
import com.hcl.backend_template.hotel.service.HotelService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class HotelFacilityService {

  private final HotelService hotelService;
  private final FacilityRepository facilityRepository;
  private final HotelFacilityRepository hotelFacilityRepository;

  public List<HotelFacilityResponse> upsertMyHotelFacilities(
      Long hotelId, UpsertHotelFacilitiesRequest request) {
    hotelService.getOwnedHotel(hotelId);

    Set<Long> facilityIds =
        request.getFacilities().stream().map(f -> f.getFacilityId()).collect(Collectors.toSet());

    Map<Long, Facility> facilitiesById =
        facilityRepository.findAllById(facilityIds).stream()
            .collect(Collectors.toMap(Facility::getId, Function.identity()));

    if (facilitiesById.size() != facilityIds.size()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more facilities not found");
    }

    for (var item : request.getFacilities()) {
      HotelFacility mapping =
          hotelFacilityRepository
              .findByHotelIdAndFacilityId(hotelId, item.getFacilityId())
              .orElseGet(HotelFacility::new);
      mapping.setHotelId(hotelId);
      mapping.setFacilityId(item.getFacilityId());
      mapping.setValue(item.getValue());
      hotelFacilityRepository.save(mapping);
    }

    return listHotelFacilities(hotelId);
  }

  public List<HotelFacilityResponse> listMyHotelFacilities(Long hotelId) {
    hotelService.getOwnedHotel(hotelId);
    return listHotelFacilities(hotelId);
  }

  public List<HotelFacilityResponse> listHotelFacilities(Long hotelId) {
    List<HotelFacility> mappings = hotelFacilityRepository.findByHotelId(hotelId);
    Set<Long> facilityIds =
        mappings.stream().map(HotelFacility::getFacilityId).collect(Collectors.toSet());

    Map<Long, Facility> facilitiesById =
        facilityIds.isEmpty()
            ? Map.of()
            : facilityRepository.findAllById(facilityIds).stream()
                .collect(Collectors.toMap(Facility::getId, Function.identity()));

    return mappings.stream()
        .map(
            mapping -> {
              Facility facility = facilitiesById.get(mapping.getFacilityId());
              String name = facility == null ? null : facility.getName();
              var category = facility == null ? null : facility.getCategory();
              return new HotelFacilityResponse(
                  mapping.getFacilityId(), name, category, mapping.getValue());
            })
        .toList();
  }
}
