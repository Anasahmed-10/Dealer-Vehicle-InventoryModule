package com.example.inventoryModule.dealer.controller;

import com.example.inventoryModule.dealer.dto.CreateDealerRequest;
import com.example.inventoryModule.dealer.dto.DealerResponse;
import com.example.inventoryModule.dealer.dto.UpdateDealerRequest;
import com.example.inventoryModule.dealer.service.DealerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/dealers")
@RequiredArgsConstructor
public class DealerController {

    private final DealerService dealerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DealerResponse create(@Valid @RequestBody CreateDealerRequest request) {
        return dealerService.create(request);
    }

    @GetMapping("/{id}")
    public DealerResponse getById(@PathVariable UUID id) {
        return dealerService.getById(id);
    }

    @GetMapping
    public Page<DealerResponse> getAll(Pageable pageable) {
        return dealerService.getAll(pageable);
    }

    @PatchMapping("/{id}")
    public DealerResponse update(@PathVariable UUID id,
                                 @RequestBody UpdateDealerRequest request) {
        return dealerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        dealerService.delete(id);
    }
}
