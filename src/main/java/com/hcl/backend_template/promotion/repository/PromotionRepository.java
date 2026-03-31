package com.hcl.backend_template.promotion.repository;

import com.hcl.backend_template.promotion.entity.Promotion;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

  boolean existsByCodeIgnoreCase(String code);

  List<Promotion> findByIsActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
      LocalDate startDate, LocalDate endDate);
}
