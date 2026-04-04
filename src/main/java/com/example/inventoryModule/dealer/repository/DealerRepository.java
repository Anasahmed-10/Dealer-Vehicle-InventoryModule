package com.example.inventoryModule.dealer.repository;

import com.example.inventoryModule.dealer.model.Dealer;
import com.example.inventoryModule.enums.SubscriptionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DealerRepository extends JpaRepository<Dealer, UUID> {

    Optional<Dealer> findByIdAndTenantId(UUID id, String tenantId);

    Page<Dealer> findAllByTenantId(String tenantId, Pageable pageable);

    boolean existsByIdAndTenantId(UUID id, String tenantId);

    long countBySubscriptionType(SubscriptionType subscriptionType);

    long countByTenantIdAndSubscriptionType(String tenantId, SubscriptionType subscriptionType);
}