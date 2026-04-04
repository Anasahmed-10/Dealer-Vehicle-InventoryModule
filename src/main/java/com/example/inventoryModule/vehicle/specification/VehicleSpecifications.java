package com.example.inventoryModule.vehicle.specification;


import com.example.inventoryModule.enums.SubscriptionType;
import com.example.inventoryModule.enums.VehicleStatus;
import com.example.inventoryModule.vehicle.model.Vehicle;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.math.BigDecimal;
import java.util.UUID;

public class VehicleSpecifications {

    public static Specification<Vehicle> hasTenant(String tenantId) {
        return (root, query, cb) -> {
        if (tenantId == null || tenantId.isBlank()) {
            return null; // no tenant restriction (admin case)
        }
        return cb.equal(root.get("tenantId"), tenantId);
    };
    }

    public static Specification<Vehicle> hasModel(String model) {
        return (root, query, cb) ->
                model == null ? null :
                cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%");
    }

    public static Specification<Vehicle> hasStatus(VehicleStatus status) {
        return (root, query, cb) ->{
            if (status == null) {
                return null;
            }
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Vehicle> priceMin(BigDecimal min) {
        return (root, query, cb) ->
                min == null ? null :
                cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Vehicle> priceMax(BigDecimal max) {
        return (root, query, cb) ->
                max == null ? null :
                cb.lessThanOrEqualTo(root.get("price"), max);
    }
    public static Specification<Vehicle> dealerSubscription(SubscriptionType subscriptionType) {
        return (root, query, cb) -> {
            if (subscriptionType == null) {
                return null;
            }
            return cb.equal(root.join("dealer").get("subscriptionType"), subscriptionType);
        };
    }
}
