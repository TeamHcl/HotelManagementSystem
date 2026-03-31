package com.hcl.backend_template.hotel.dto;

import com.hcl.backend_template.hotel.entity.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadHotelDocumentRequest {

  @NotNull private DocumentType documentType;

  @NotBlank
  @Size(max = 5000)
  private String documentUrl;
}
