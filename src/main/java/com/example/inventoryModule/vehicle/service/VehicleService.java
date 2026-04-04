package com.example.inventoryModule.vehicle.service;

import com.example.inventoryModule.common.exception.ForbiddenException;
import com.example.inventoryModule.common.exception.ResourceNotFoundException;
import com.example.inventoryModule.common.tenant.TenantContext;
import com.example.inventoryModule.dealer.model.Dealer;
import com.example.inventoryModule.dealer.repository.DealerRepository;
import com.example.inventoryModule.vehicle.model.Vehicle;
import com.example.inventoryModule.vehicle.dto.*;
import com.example.inventoryModule.vehicle.repository.VehicleRepository;
import com.example.inventoryModule.vehicle.specification.VehicleSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DealerRepository dealerRepository;

    public VehicleResponse create(CreateVehicleRequest request) {
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null || tenantId.isBlank()) {
        throw new IllegalArgumentException("Missing X-Tenant-Id header");
    }
        Dealer dealer = dealerRepository.findByIdAndTenantId(request.dealerId(), tenantId)
                .orElseThrow(() -> new AccessDeniedException("Dealer not found or does not belong to current tenant"));

        enforceTenant(dealer.getTenantId());

        Vehicle vehicle = Vehicle.builder()
                .tenantId(tenantId)
                .dealer(dealer)
                .model(request.model())
                .price(request.price())
                .status(request.status())
                .build();

        return toResponse(vehicleRepository.save(vehicle));
    }

    public VehicleResponse getById(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        enforceTenant(vehicle.getTenantId());
        return toResponse(vehicle);
    }

    public Page<VehicleResponse> getAll(VehicleFilterRequest filter, Pageable pageable) {
        String tenantId = TenantContext.getTenantId();
        if (filter.priceMin() != null && filter.priceMin().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("priceMin must be >= 0");
        }

        if (filter.priceMax() != null && filter.priceMax().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("priceMax must be >= 0");
        }
        if (filter.priceMin() != null && filter.priceMax() != null
                && filter.priceMin().compareTo(filter.priceMax()) > 0) {
            throw new IllegalArgumentException("Minimum Price cannot be greater than Maximum Price");
        }

        Specification<Vehicle> spec = Specification
                .where(VehicleSpecifications.hasTenant(tenantId))
                .and(VehicleSpecifications.hasModel(filter.model()))
                .and(VehicleSpecifications.hasStatus(filter.status()))
                .and(VehicleSpecifications.priceMin(filter.priceMin()))
                .and(VehicleSpecifications.priceMax(filter.priceMax()));

        // If using Dealer relation, add:
        // .and(VehicleSpecifications.dealerSubscription(filter.subscription()))
        // .and(VehicleSpecifications.dealerTenant(tenantId));

        return vehicleRepository.findAll(spec, pageable)
                .map(this::toResponse);
    }

    public VehicleResponse update(UUID id, UpdateVehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        enforceTenant(vehicle.getTenantId());

        if (request.dealerId() != null) {
            Dealer dealer = dealerRepository.findById(request.dealerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dealer not found"));
            enforceTenant(dealer.getTenantId());
            vehicle.setDealer(dealer);
        }

        if (request.model() != null) vehicle.setModel(request.model());
        if (request.price() != null) vehicle.setPrice(request.price());
        if (request.status() != null) vehicle.setStatus(request.status());

        return toResponse(vehicleRepository.save(vehicle));
    }

    public void delete(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        enforceTenant(vehicle.getTenantId());
        vehicleRepository.delete(vehicle);
    }

    private void enforceTenant(String resourceTenantId) {
        if (TenantContext.getTenantId() == null || TenantContext.getTenantId().isBlank()) {
        throw new IllegalArgumentException("Missing X-Tenant-Id header");
    }
        if (!TenantContext.getTenantId().equals(resourceTenantId)) {
            throw new ForbiddenException("Cross-tenant access is forbidden");
        }
    }

    private VehicleResponse toResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getDealer().getId(),
                vehicle.getModel(),
                vehicle.getPrice(),
                vehicle.getStatus()
        );
    }
}