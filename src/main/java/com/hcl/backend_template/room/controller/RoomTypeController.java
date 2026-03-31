package com.hcl.backend_template.room.controller;

import com.hcl.backend_template.room.dto.RoomTypeRequest;
import com.hcl.backend_template.room.dto.RoomTypeResponse;
import com.hcl.backend_template.room.service.RoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @PostMapping("/room-types")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new room type")
    public RoomTypeResponse create(@Valid @RequestBody RoomTypeRequest request) {
        return roomTypeService.create(request);
    }

    @PutMapping("/room-types/{id}")
    @Operation(summary = "Update an existing room type")
    public RoomTypeResponse update(
            @PathVariable("id") Long id, @Valid @RequestBody RoomTypeRequest request) {
        return roomTypeService.update(id, request);
    }

    @DeleteMapping("/room-types/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a room type")
    public void delete(@PathVariable("id") Long id) {
        roomTypeService.delete(id);
    }

    @GetMapping("/hotels/{hotelId}/room-types")
    @Operation(summary = "List room types for a hotel")
    public List<RoomTypeResponse> getByHotel(@PathVariable("hotelId") Long hotelId) {
        return roomTypeService.getByHotel(hotelId);
    }
}
