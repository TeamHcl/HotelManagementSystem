package com.hcl.backend_template.hotel.repository;

import com.hcl.backend_template.hotel.entity.DocumentType;
import com.hcl.backend_template.hotel.entity.HotelDocument;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelDocumentRepository extends JpaRepository<HotelDocument, Long> {

  List<HotelDocument> findByHotelIdOrderByUploadedAtDesc(Long hotelId);

  Optional<HotelDocument> findByHotelIdAndDocumentType(Long hotelId, DocumentType documentType);

  List<HotelDocument> findByHotelIdAndDocumentTypeIn(Long hotelId, Collection<DocumentType> types);
}
