package com.example.inventoryModule.admin.controller;

import com.example.inventoryModule.admin.service.AdminInventoryService;
//import com.example.inventoryModule.enums.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.inventoryModule.admin.dto.DealerSubscriptionCountResponse;

//import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminInventoryController {

    private final AdminInventoryService adminInventoryService;

    @GetMapping("/dealers/countBySubscription")
    /*public Map<SubscriptionType, Long> countBySubscription() {
        return adminInventoryService.countDealersBySubscriptionOverall();
    }*/
    public DealerSubscriptionCountResponse countBySubscription() {
        System.out.println(">>> Admin controller reached");
        return adminInventoryService.countDealersBySubscriptionOverall();
    }

}
