package com.example.inventoryModule.vehicle.dto;

import com.example.inventoryModule.enums.VehicleStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record VehicleResponse(
        UUID id,
        UUID dealerId,
        String model,
        BigDecimal price,
        VehicleStatus status
) {}
