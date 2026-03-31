package com.hcl.backend_template.promotion.service;

import com.hcl.backend_template.promotion.dto.CreatePromotionRequest;
import com.hcl.backend_template.promotion.entity.Promotion;
import com.hcl.backend_template.promotion.repository.PromotionRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PromotionService {

  private final PromotionRepository promotionRepository;

  public List<Promotion> listActive() {
    LocalDate today = LocalDate.now();
    return promotionRepository.findByIsActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        today, today);
  }

  public Promotion getById(Long id) {
    return promotionRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found"));
  }

  public Promotion create(CreatePromotionRequest request) {
    if (promotionRepository.existsByCodeIgnoreCase(request.getCode())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Promotion code already exists");
    }
    if (request.getEndDate().isBefore(request.getStartDate())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endDate must be after startDate");
    }
    Promotion promotion = new Promotion();
    promotion.setCode(request.getCode().trim());
    promotion.setType(request.getType());
    promotion.setValue(request.getValue());
    promotion.setMinBookingAmount(request.getMinBookingAmount());
    promotion.setMaxDiscount(request.getMaxDiscount());
    promotion.setStartDate(request.getStartDate());
    promotion.setEndDate(request.getEndDate());
    promotion.setUsageLimit(request.getUsageLimit());
    promotion.setUsedCount(0);
    promotion.setIsActive(Boolean.TRUE.equals(request.getActive()));
    return promotionRepository.save(promotion);
  }
}
