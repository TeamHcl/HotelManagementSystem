package com.hcl.backend_template.promotion.controller;

import com.hcl.backend_template.promotion.dto.CreatePromotionRequest;
import com.hcl.backend_template.promotion.dto.PromotionResponse;
import com.hcl.backend_template.promotion.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Promotions", description = "Promotion endpoints")
public class PromotionController {

  private final PromotionService promotionService;

  @GetMapping("/promotions")
  @Operation(summary = "List active promotions", security = {})
  public List<PromotionResponse> listActive() {
    return promotionService.listActive().stream().map(PromotionResponse::from).toList();
  }

  @GetMapping("/promotions/{id}")
  @Operation(summary = "Get promotion by id", security = {})
  public PromotionResponse getById(@PathVariable("id") Long id) {
    return PromotionResponse.from(promotionService.getById(id));
  }

  @PostMapping("/promotions")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a promotion")
  @SecurityRequirement(name = "BearerAuth")
  public PromotionResponse create(@Valid @RequestBody CreatePromotionRequest request) {
    return PromotionResponse.from(promotionService.create(request));
  }
}
