package com.example.inventoryModule.vehicle.dto;

import com.example.inventoryModule.enums.VehicleStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateVehicleRequest(
        UUID dealerId,

        @Size(max = 150, message = "Model must not exceed 150 characters")
        String model,

        @DecimalMin(value = "0.0", inclusive = true, message = "Price must be >= 0")
        BigDecimal price,

        VehicleStatus status
) {}
