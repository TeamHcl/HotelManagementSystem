package com.hcl.backend_template.hotel.dto;

import com.hcl.backend_template.hotel.entity.DocumentType;
import com.hcl.backend_template.hotel.entity.HotelDocument;
import com.hcl.backend_template.hotel.entity.VerificationStatus;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HotelDocumentResponse {

  private final Long id;
  private final Long hotelId;
  private final DocumentType documentType;
  private final String documentUrl;
  private final VerificationStatus verificationStatus;
  private final OffsetDateTime uploadedAt;
  private final OffsetDateTime verifiedAt;
  private final Long verifiedBy;

  public static HotelDocumentResponse from(HotelDocument document) {
    return new HotelDocumentResponse(
        document.getId(),
        document.getHotelId(),
        document.getDocumentType(),
        document.getDocumentUrl(),
        document.getVerificationStatus(),
        document.getUploadedAt(),
        document.getVerifiedAt(),
        document.getVerifiedBy());
  }
}
