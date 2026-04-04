package com.example.inventoryModule.dealer.dto;

import com.example.inventoryModule.enums.SubscriptionType;

import java.util.UUID;

public record DealerResponse(
        UUID id,
        String name,
        String email,
        SubscriptionType subscriptionType
) {}

