package com.hcl.backend_template.hotel.service;

import com.hcl.backend_template.common.security.CurrentUser;
import com.hcl.backend_template.hotel.dto.CreateHotelRequest;
import com.hcl.backend_template.hotel.dto.UpdateHotelRequest;
import com.hcl.backend_template.hotel.entity.Hotel;
import com.hcl.backend_template.hotel.entity.HotelStatus;
import com.hcl.backend_template.hotel.repository.HotelRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class HotelService {

  private final HotelRepository hotelRepository;
  private final CurrentUser currentUser;

  public Hotel create(CreateHotelRequest request) {
    Hotel hotel = new Hotel();
    hotel.setName(request.getName());
    hotel.setLocation(request.getLocation());
    hotel.setDescription(request.getDescription());
    hotel.setManagerId(currentUser.requireUserId());
    hotel.setStatus(HotelStatus.PENDING);
    return hotelRepository.save(hotel);
  }

  public Hotel updateMyHotel(Long hotelId, UpdateHotelRequest request) {
    Hotel hotel = getOwnedHotel(hotelId);
    hotel.setName(request.getName());
    hotel.setLocation(request.getLocation());
    hotel.setDescription(request.getDescription());
    return hotelRepository.save(hotel);
  }

  public List<Hotel> listMyHotels() {
    return hotelRepository.findByManagerIdOrderByCreatedAtDesc(currentUser.requireUserId());
  }

  public List<Hotel> listActiveHotels() {
    return hotelRepository.findByStatusInOrderByCreatedAtDesc(List.of(HotelStatus.ACTIVE));
  }

  public Hotel getActiveHotelOrThrow(Long hotelId) {
    Hotel hotel =
        hotelRepository
            .findById(hotelId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
    if (hotel.getStatus() != HotelStatus.ACTIVE) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
    }
    return hotel;
  }

  public Hotel getOwnedHotel(Long hotelId) {
    Hotel hotel =
        hotelRepository
            .findById(hotelId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
    if (!currentUser.requireUserId().equals(hotel.getManagerId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed");
    }
    return hotel;
  }
}
