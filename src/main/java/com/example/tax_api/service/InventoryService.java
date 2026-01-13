package com.example.tax_api.service;

import com.example.tax_api.model.Inventory;
import com.example.tax_api.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory getBySkuNumber (String skuNumber){
        return inventoryRepository.findById(skuNumber)
                .orElseThrow(() ->
                        new RuntimeException("Invalid Sku Number"));

    }
}
