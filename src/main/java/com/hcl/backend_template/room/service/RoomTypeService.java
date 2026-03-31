package com.hcl.backend_template.room.service;

import com.hcl.backend_template.room.dto.RoomTypeRequest;
import com.hcl.backend_template.room.dto.RoomTypeResponse;
import com.hcl.backend_template.room.entity.RoomType;
import com.hcl.backend_template.room.repository.RoomTypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoomTypeService {

  private final RoomTypeRepository roomTypeRepository;

  public RoomTypeResponse create(RoomTypeRequest request) {
    RoomType roomType = new RoomType();
    roomType.setHotelId(request.getHotelId());
    roomType.setName(request.getName());
    roomType.setCapacity(request.getCapacity());
    roomType.setBasePrice(request.getBasePrice());
    RoomType saved = roomTypeRepository.save(roomType);
    return toResponse(saved);
  }

  public RoomTypeResponse update(Long id, RoomTypeRequest request) {
    RoomType existing =
        roomTypeRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room type not found"));
    existing.setName(request.getName());
    existing.setCapacity(request.getCapacity());
    existing.setBasePrice(request.getBasePrice());
    // Do not allow changing hotelId via update to keep ownership stable
    RoomType saved = roomTypeRepository.save(existing);
    return toResponse(saved);
  }

  public void delete(Long id) {
    if (!roomTypeRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room type not found");
    }
    roomTypeRepository.deleteById(id);
  }

  public List<RoomTypeResponse> getByHotel(Long hotelId) {
    return roomTypeRepository.findByHotelId(hotelId).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  private RoomTypeResponse toResponse(RoomType roomType) {
    RoomTypeResponse response = new RoomTypeResponse();
    response.setId(roomType.getId());
    response.setHotelId(roomType.getHotelId());
    response.setName(roomType.getName());
    response.setCapacity(roomType.getCapacity());
    response.setBasePrice(roomType.getBasePrice());
    response.setCreatedAt(roomType.getCreatedAt());
    return response;
  }
}
