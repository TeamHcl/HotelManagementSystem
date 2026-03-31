package com.hcl.backend_template.room.repository;

import com.hcl.backend_template.room.entity.PriceRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRuleRepository extends JpaRepository<PriceRule, Long> {

  List<PriceRule> findByRoomTypeId(Long roomTypeId);
}
