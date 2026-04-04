package com.example.inventoryModule.vehicle.controller;

import com.example.inventoryModule.enums.SubscriptionType;
import com.example.inventoryModule.enums.VehicleStatus;
import com.example.inventoryModule.vehicle.dto.*;
import com.example.inventoryModule.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse create(@Valid @RequestBody CreateVehicleRequest request) {
        return vehicleService.create(request);
    }

    @GetMapping("/{id}")
    public VehicleResponse getById(@PathVariable UUID id) {
        return vehicleService.getById(id);
    }

    @GetMapping
    public Page<VehicleResponse> getAll(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) VehicleStatus status,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) SubscriptionType subscription,
            Pageable pageable
    ) {
        VehicleFilterRequest filter = new VehicleFilterRequest(
                model, status, priceMin, priceMax, subscription
        );

        return vehicleService.getAll(filter, pageable);
    }

    @PatchMapping("/{id}")
    public VehicleResponse update(@PathVariable UUID id,
                                  @RequestBody UpdateVehicleRequest request) {
        return vehicleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        vehicleService.delete(id);
    }
}