package com.example.inventoryModule.dealer.dto;

import com.example.inventoryModule.enums.SubscriptionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateDealerRequest(
        @Size(max = 150, message = "Name must not exceed 150 characters")
        String name,

        @Email(message = "Email must be valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        SubscriptionType subscriptionType
) {}
