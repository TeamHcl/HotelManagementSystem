package com.hcl.backend_template.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "email_logs",
    indexes = {@Index(name = "idx_email_logs_booking_id", columnList = "booking_id")})
@Getter
@Setter
@NoArgsConstructor
public class EmailLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "booking_id", nullable = false)
  private Long bookingId;

  @Column(name = "email", nullable = false, length = 150)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private EmailStatus status;

  @Column(name = "sent_at")
  private OffsetDateTime sentAt;
}
