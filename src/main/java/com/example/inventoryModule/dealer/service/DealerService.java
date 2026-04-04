package com.example.inventoryModule.dealer.service;

import com.example.inventoryModule.common.exception.ForbiddenException;
import com.example.inventoryModule.common.exception.ResourceNotFoundException;
import com.example.inventoryModule.common.tenant.TenantContext;
import com.example.inventoryModule.dealer.model.Dealer;
import com.example.inventoryModule.dealer.dto.*;
import com.example.inventoryModule.dealer.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealerService {

    private final DealerRepository dealerRepository;

    public DealerResponse create(CreateDealerRequest request) {
        Dealer dealer = Dealer.builder()
                .tenantId(TenantContext.getTenantId())
                .name(request.name())
                .email(request.email())
                .subscriptionType(request.subscriptionType())
                .build();

        try {
            return toResponse(dealerRepository.save(dealer));
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Dealer email must be unique within the tenant");
        }
    }

    public DealerResponse getById(UUID id) {
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dealer not found"));

        enforceTenant(dealer.getTenantId());
        return toResponse(dealer);
    }

    public Page<DealerResponse> getAll(Pageable pageable) {
        return dealerRepository.findAllByTenantId(TenantContext.getTenantId(), pageable)
                .map(this::toResponse);
    }

    public DealerResponse update(UUID id, UpdateDealerRequest request) {
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dealer not found"));

        enforceTenant(dealer.getTenantId());

        if (request.name() != null) dealer.setName(request.name());
        if (request.email() != null) dealer.setEmail(request.email());
        if (request.subscriptionType() != null) dealer.setSubscriptionType(request.subscriptionType());

       try {
            return toResponse(dealerRepository.save(dealer));
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Dealer email must be unique within the tenant");
        }
    }

    public void delete(UUID id) {
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dealer not found"));

        enforceTenant(dealer.getTenantId());
        dealerRepository.delete(dealer);
    }

    private void enforceTenant(String resourceTenantId) {
        if (!TenantContext.getTenantId().equals(resourceTenantId)) {
            throw new ForbiddenException("Cross-tenant access is forbidden");
        }
    }

    private DealerResponse toResponse(Dealer dealer) {
        return new DealerResponse(
                dealer.getId(),
                dealer.getName(),
                dealer.getEmail(),
                dealer.getSubscriptionType()
        );
    }
}