package com.hcl.backend_template.room.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "room_inventory",
    indexes = {
      @Index(name = "idx_room_inventory_room_type_id", columnList = "room_type_id"),
      @Index(name = "idx_room_inventory_date", columnList = "date")
    })
@Getter
@Setter
@NoArgsConstructor
public class RoomInventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "room_type_id", nullable = false)
  private Long roomTypeId;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "total_rooms", nullable = false)
  private Integer totalRooms;

  @Column(name = "available_rooms", nullable = false)
  private Integer availableRooms;
}
