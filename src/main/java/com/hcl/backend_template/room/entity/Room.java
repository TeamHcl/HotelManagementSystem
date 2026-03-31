package com.hcl.backend_template.room.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "rooms",
    indexes = {
      @Index(name = "idx_rooms_hotel_id", columnList = "hotel_id"),
      @Index(name = "idx_rooms_room_number", columnList = "room_number")
    })
@Getter
@Setter
@NoArgsConstructor
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "hotel_id", nullable = false)
  private Long hotelId;

  @Column(name = "room_number", nullable = false, length = 20)
  private String roomNumber;

  @Column(name = "type", length = 50)
  private String type;

  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "capacity", nullable = false)
  private Integer capacity;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private RoomStatus status = RoomStatus.AVAILABLE;
}
