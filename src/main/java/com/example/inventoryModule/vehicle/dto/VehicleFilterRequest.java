package com.example.inventoryModule.vehicle.dto;

import com.example.inventoryModule.enums.SubscriptionType;
import com.example.inventoryModule.enums.VehicleStatus;

import java.math.BigDecimal;

public record VehicleFilterRequest(
        String model,
        VehicleStatus status,
        BigDecimal priceMin,
        BigDecimal priceMax,
        SubscriptionType subscription
) {}
