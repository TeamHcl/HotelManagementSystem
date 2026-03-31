package com.hcl.backend_template.room.controller;

import com.hcl.backend_template.room.dto.InventoryBulkRequest;
import com.hcl.backend_template.room.dto.InventoryUpdateRequest;
import com.hcl.backend_template.room.service.RoomInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Room & Inventory & Search", description = "Room type and inventory management")
public class RoomInventoryController {

  private final RoomInventoryService roomInventoryService;

  @PostMapping("/inventory/bulk")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Bulk create inventory for a room type over a date range")
  public void bulkCreate(@Valid @RequestBody InventoryBulkRequest request) {
    roomInventoryService.bulkCreate(request);
  }

  @PutMapping("/inventory/update")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Update inventory for a specific date")
  public void update(@Valid @RequestBody InventoryUpdateRequest request) {
    roomInventoryService.update(request);
  }
}
