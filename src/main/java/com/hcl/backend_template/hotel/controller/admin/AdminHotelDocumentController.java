package com.hcl.backend_template.hotel.controller.admin;

import com.hcl.backend_template.hotel.dto.HotelDocumentResponse;
import com.hcl.backend_template.hotel.service.admin.AdminHotelDocumentService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/documents")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@Hidden
public class AdminHotelDocumentController {

  private final AdminHotelDocumentService adminHotelDocumentService;

  @PutMapping("/{id}/verify")
  @Operation(summary = "Verify a hotel document")
  public HotelDocumentResponse verify(@PathVariable("id") Long documentId) {
    return HotelDocumentResponse.from(adminHotelDocumentService.verify(documentId));
  }

  @PutMapping("/{id}/reject")
  @Operation(summary = "Reject a hotel document")
  public HotelDocumentResponse reject(@PathVariable("id") Long documentId) {
    return HotelDocumentResponse.from(adminHotelDocumentService.reject(documentId));
  }
}
