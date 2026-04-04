package com.example.inventoryModule.admin.service;

import com.example.inventoryModule.admin.dto.DealerSubscriptionCountResponse;
import com.example.inventoryModule.dealer.repository.DealerRepository;
import com.example.inventoryModule.enums.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminInventoryService {

    private final DealerRepository dealerRepository;

    /*public Map<SubscriptionType, Long> countDealersBySubscriptionOverall() {
        Map<SubscriptionType, Long> result = new EnumMap<>(SubscriptionType.class);
        result.put(SubscriptionType.BASIC, dealerRepository.countBySubscriptionType(SubscriptionType.BASIC));
        result.put(SubscriptionType.PREMIUM, dealerRepository.countBySubscriptionType(SubscriptionType.PREMIUM));
        return result;
    }*/
    public DealerSubscriptionCountResponse countDealersBySubscriptionOverall() {
        long basic = dealerRepository.countBySubscriptionType(SubscriptionType.BASIC);
        long premium = dealerRepository.countBySubscriptionType(SubscriptionType.PREMIUM);

        return new DealerSubscriptionCountResponse(basic, premium);
    }
}