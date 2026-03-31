package com.hcl.backend_template.room.service;

import com.hcl.backend_template.room.dto.InventoryBulkRequest;
import com.hcl.backend_template.room.dto.InventoryUpdateRequest;
import com.hcl.backend_template.room.entity.RoomInventory;
import com.hcl.backend_template.room.repository.RoomInventoryRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoomInventoryService {

  private final RoomInventoryRepository roomInventoryRepository;

  public void bulkCreate(InventoryBulkRequest request) {
    if (!request.getStartDate().isBefore(request.getEndDate())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startDate must be before endDate");
    }
    LocalDate date = request.getStartDate();
    while (date.isBefore(request.getEndDate())) {
      RoomInventory inventory = new RoomInventory();
      inventory.setRoomTypeId(request.getRoomTypeId());
      inventory.setDate(date);
      inventory.setTotalRooms(request.getTotalRooms());
      inventory.setAvailableRooms(request.getTotalRooms());
      roomInventoryRepository.save(inventory);
      date = date.plusDays(1);
    }
  }

  public void update(InventoryUpdateRequest request) {
    List<RoomInventory> inventories =
        roomInventoryRepository.findByRoomTypeIdAndDateBetween(
            request.getRoomTypeId(), request.getDate(), request.getDate());
    RoomInventory inventory;
    if (inventories.isEmpty()) {
      inventory = new RoomInventory();
      inventory.setRoomTypeId(request.getRoomTypeId());
      inventory.setDate(request.getDate());
      inventory.setTotalRooms(request.getTotalRooms());
      inventory.setAvailableRooms(request.getTotalRooms());
    } else {
      inventory = inventories.get(0);
      int delta = request.getTotalRooms() - inventory.getTotalRooms();
      inventory.setTotalRooms(request.getTotalRooms());
      inventory.setAvailableRooms(Math.max(0, inventory.getAvailableRooms() + delta));
    }
    roomInventoryRepository.save(inventory);
  }
}
