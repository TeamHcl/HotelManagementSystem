package com.hcl.backend_template.hotel.service.admin;

import com.hcl.backend_template.common.security.CurrentUser;
import com.hcl.backend_template.hotel.entity.HotelDocument;
import com.hcl.backend_template.hotel.entity.VerificationStatus;
import com.hcl.backend_template.hotel.repository.HotelDocumentRepository;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AdminHotelDocumentService {

  private final HotelDocumentRepository hotelDocumentRepository;
  private final CurrentUser currentUser;

  public HotelDocument verify(Long documentId) {
    HotelDocument document = getDocumentOrThrow(documentId);
    document.setVerificationStatus(VerificationStatus.VERIFIED);
    document.setVerifiedAt(OffsetDateTime.now());
    document.setVerifiedBy(currentUser.requireUserId());
    return hotelDocumentRepository.save(document);
  }

  public HotelDocument reject(Long documentId) {
    HotelDocument document = getDocumentOrThrow(documentId);
    document.setVerificationStatus(VerificationStatus.REJECTED);
    document.setVerifiedAt(OffsetDateTime.now());
    document.setVerifiedBy(currentUser.requireUserId());
    return hotelDocumentRepository.save(document);
  }

  private HotelDocument getDocumentOrThrow(Long documentId) {
    return hotelDocumentRepository
        .findById(documentId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel document not found"));
  }
}
