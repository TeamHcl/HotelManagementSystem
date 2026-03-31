package com.hcl.backend_template.room.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "room_types",
    indexes = {@Index(name = "idx_room_types_hotel_id", columnList = "hotel_id")})
@Getter
@Setter
@NoArgsConstructor
public class RoomType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "hotel_id", nullable = false)
  private Long hotelId;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal basePrice;

  @Column(name = "capacity", nullable = false)
  private Integer capacity;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private OffsetDateTime createdAt;
}
