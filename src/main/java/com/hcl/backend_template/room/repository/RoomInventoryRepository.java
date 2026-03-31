package com.hcl.backend_template.room.repository;

import com.hcl.backend_template.room.entity.RoomInventory;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInventoryRepository extends JpaRepository<RoomInventory, Long> {

  List<RoomInventory> findByRoomTypeIdAndDateBetween(
      Long roomTypeId, LocalDate start, LocalDate end);
}
