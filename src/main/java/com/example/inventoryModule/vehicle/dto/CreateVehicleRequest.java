package com.example.inventoryModule.vehicle.dto;

import com.example.inventoryModule.enums.VehicleStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateVehicleRequest(
        @NotNull(message = "Dealer ID is required")
        UUID dealerId,

        @NotBlank(message = "Model is required")
        @Size(max = 150, message = "Model must not exceed 150 characters")
        String model,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Price must be >= 0")
        BigDecimal price,

        @NotNull(message = "Status is required")
        VehicleStatus status
) {}
