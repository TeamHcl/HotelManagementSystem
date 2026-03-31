package com.hcl.backend_template.hotel.service;

import com.hcl.backend_template.hotel.dto.UploadHotelDocumentRequest;
import com.hcl.backend_template.hotel.entity.HotelDocument;
import com.hcl.backend_template.hotel.entity.VerificationStatus;
import com.hcl.backend_template.hotel.repository.HotelDocumentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelDocumentService {

  private final HotelService hotelService;
  private final HotelDocumentRepository hotelDocumentRepository;

  public HotelDocument upsertMyHotelDocument(Long hotelId, UploadHotelDocumentRequest request) {
    hotelService.getOwnedHotel(hotelId);

    HotelDocument document =
        hotelDocumentRepository
            .findByHotelIdAndDocumentType(hotelId, request.getDocumentType())
            .orElseGet(HotelDocument::new);

    document.setHotelId(hotelId);
    document.setDocumentType(request.getDocumentType());
    document.setDocumentUrl(request.getDocumentUrl());

    // re-upload resets verification state
    document.setVerificationStatus(VerificationStatus.PENDING);
    document.setVerifiedAt(null);
    document.setVerifiedBy(null);

    return hotelDocumentRepository.save(document);
  }

  public List<HotelDocument> listMyHotelDocuments(Long hotelId) {
    hotelService.getOwnedHotel(hotelId);
    return hotelDocumentRepository.findByHotelIdOrderByUploadedAtDesc(hotelId);
  }
}
