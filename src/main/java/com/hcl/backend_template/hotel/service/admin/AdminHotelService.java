package com.hcl.backend_template.hotel.service.admin;

import com.hcl.backend_template.hotel.dto.admin.AdminHotelDecision;
import com.hcl.backend_template.hotel.entity.DocumentType;
import com.hcl.backend_template.hotel.entity.Hotel;
import com.hcl.backend_template.hotel.entity.HotelStatus;
import com.hcl.backend_template.hotel.entity.VerificationStatus;
import com.hcl.backend_template.hotel.repository.HotelDocumentRepository;
import com.hcl.backend_template.hotel.repository.HotelRepository;
import java.util.EnumMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AdminHotelService {

  private final HotelRepository hotelRepository;
  private final HotelDocumentRepository hotelDocumentRepository;

  public List<Hotel> listForReview() {
    return hotelRepository.findByStatusInOrderByCreatedAtDesc(
        List.of(HotelStatus.PENDING, HotelStatus.UNDER_REVIEW));
  }

  public Hotel decide(Long hotelId, AdminHotelDecision decision) {
    return switch (decision) {
      case APPROVE -> approve(hotelId);
      case REJECT -> reject(hotelId);
    };
  }

  public Hotel approve(Long hotelId) {
    Hotel hotel = getHotelOrThrow(hotelId);
    if (hotel.getStatus() == HotelStatus.REJECTED) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Rejected hotel cannot be approved");
    }
    if (!allRequiredDocumentsVerified(hotelId)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "Cannot approve hotel until all documents are VERIFIED");
    }
    hotel.setStatus(HotelStatus.ACTIVE);
    return hotelRepository.save(hotel);
  }

  public Hotel reject(Long hotelId) {
    Hotel hotel = getHotelOrThrow(hotelId);
    hotel.setStatus(HotelStatus.REJECTED);
    return hotelRepository.save(hotel);
  }

  private Hotel getHotelOrThrow(Long hotelId) {
    return hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
  }

  private boolean allRequiredDocumentsVerified(Long hotelId) {
    var required = List.of(DocumentType.values());
    var docs = hotelDocumentRepository.findByHotelIdAndDocumentTypeIn(hotelId, required);

    EnumMap<DocumentType, VerificationStatus> statusByType = new EnumMap<>(DocumentType.class);
    for (var doc : docs) {
      statusByType.put(doc.getDocumentType(), doc.getVerificationStatus());
    }

    for (var type : required) {
      if (statusByType.get(type) != VerificationStatus.VERIFIED) {
        return false;
      }
    }
    return true;
  }
}
