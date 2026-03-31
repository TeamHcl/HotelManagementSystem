package com.hcl.backend_template.hotel.entity;

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
    name = "hotel_documents",
    indexes = {@Index(name = "idx_hotel_documents_hotel_id", columnList = "hotel_id")})
@Getter
@Setter
@NoArgsConstructor
public class HotelDocument {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "hotel_id", nullable = false)
  private Long hotelId;

  @Enumerated(EnumType.STRING)
  @Column(name = "document_type", nullable = false, length = 50)
  private DocumentType documentType;

  @Column(name = "document_url", nullable = false, columnDefinition = "TEXT")
  private String documentUrl;

  @Enumerated(EnumType.STRING)
  @Column(name = "verification_status", nullable = false, length = 20)
  private VerificationStatus verificationStatus = VerificationStatus.PENDING;

  @Column(name = "uploaded_at", nullable = false, updatable = false, insertable = false)
  private OffsetDateTime uploadedAt;

  @Column(name = "verified_at")
  private OffsetDateTime verifiedAt;

  @Column(name = "verified_by")
  private Long verifiedBy;
}
