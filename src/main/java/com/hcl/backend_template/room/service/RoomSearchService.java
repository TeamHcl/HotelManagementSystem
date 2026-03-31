package com.hcl.backend_template.room.service;

import com.hcl.backend_template.room.dto.AvailabilityResponse;
import com.hcl.backend_template.room.dto.SearchResultItem;
import com.hcl.backend_template.room.entity.PriceRule;
import com.hcl.backend_template.room.entity.RoomInventory;
import com.hcl.backend_template.room.entity.RoomType;
import com.hcl.backend_template.room.repository.PriceRuleRepository;
import com.hcl.backend_template.room.repository.RoomInventoryRepository;
import com.hcl.backend_template.room.repository.RoomTypeRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoomSearchService {

  private final RoomTypeRepository roomTypeRepository;
  private final RoomInventoryRepository roomInventoryRepository;
  private final PriceRuleRepository priceRuleRepository;

  public List<SearchResultItem> search(
      Long hotelId, LocalDate checkIn, LocalDate checkOut, int guests) {
    validateDates(checkIn, checkOut);
    List<RoomType> roomTypes = roomTypeRepository.findByHotelId(hotelId);
    List<SearchResultItem> results = new ArrayList<>();
    for (RoomType roomType : roomTypes) {
      if (roomType.getCapacity() == null || roomType.getCapacity() < guests) {
        continue;
      }
      AvailabilityResponse availability = checkAvailability(roomType.getId(), checkIn, checkOut);
      if (availability.isAvailable()) {
        SearchResultItem item = new SearchResultItem();
        item.setRoomTypeId(roomType.getId());
        item.setHotelId(roomType.getHotelId());
        item.setRoomTypeName(roomType.getName());
        item.setCapacity(roomType.getCapacity());
        item.setTotalPrice(availability.getTotalPrice());
        results.add(item);
      }
    }
    return results.stream()
        .sorted((a, b) -> a.getTotalPrice().compareTo(b.getTotalPrice()))
        .collect(Collectors.toList());
  }

  public AvailabilityResponse checkAvailability(
      Long roomTypeId, LocalDate checkIn, LocalDate checkOut) {
    validateDates(checkIn, checkOut);
    RoomType roomType =
        roomTypeRepository
            .findById(roomTypeId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room type not found"));

    LocalDate endInclusive = checkOut.minusDays(1);
    List<RoomInventory> inventories =
        roomInventoryRepository.findByRoomTypeIdAndDateBetween(roomTypeId, checkIn, endInclusive);

    long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
    if (inventories.size() != nights) {
      return buildUnavailable(roomType);
    }
    for (RoomInventory inventory : inventories) {
      if (inventory.getAvailableRooms() == null || inventory.getAvailableRooms() <= 0) {
        return buildUnavailable(roomType);
      }
    }

    List<PriceRule> rules = priceRuleRepository.findByRoomTypeId(roomTypeId);

    List<LocalDate> dates = new ArrayList<>();
    List<BigDecimal> nightlyPrices = new ArrayList<>();
    BigDecimal total = BigDecimal.ZERO;

    LocalDate date = checkIn;
    while (date.isBefore(checkOut)) {
      dates.add(date);
      BigDecimal nightly = resolveNightlyPrice(roomType.getBasePrice(), rules, date);
      nightlyPrices.add(nightly);
      total = total.add(nightly);
      date = date.plusDays(1);
    }

    AvailabilityResponse response = new AvailabilityResponse();
    response.setAvailable(true);
    response.setTotalPrice(total.setScale(2, RoundingMode.HALF_UP));
    response.setDates(dates);
    response.setNightlyPrices(nightlyPrices);
    response.setRoomTypeId(roomType.getId());
    response.setHotelId(roomType.getHotelId());
    response.setRoomTypeName(roomType.getName());
    response.setCapacity(roomType.getCapacity());
    return response;
  }

  private void validateDates(LocalDate checkIn, LocalDate checkOut) {
    if (checkIn == null || checkOut == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "checkIn and checkOut are required");
    }
    if (!checkIn.isBefore(checkOut)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "checkIn must be before checkOut");
    }
  }

  private AvailabilityResponse buildUnavailable(RoomType roomType) {
    AvailabilityResponse response = new AvailabilityResponse();
    response.setAvailable(false);
    response.setRoomTypeId(roomType.getId());
    response.setHotelId(roomType.getHotelId());
    response.setRoomTypeName(roomType.getName());
    response.setCapacity(roomType.getCapacity());
    return response;
  }

  private BigDecimal resolveNightlyPrice(
      BigDecimal basePrice, List<PriceRule> rules, LocalDate date) {
    BigDecimal price = basePrice;
    Optional<PriceRule> ruleForDate =
        rules.stream()
            .filter(r -> !date.isBefore(r.getStartDate()) && !date.isAfter(r.getEndDate()))
            .findFirst();
    if (ruleForDate.isPresent()) {
      BigDecimal multiplier = ruleForDate.get().getMultiplier();
      price = price.multiply(multiplier);
    }
    return price.setScale(2, RoundingMode.HALF_UP);
  }
}
