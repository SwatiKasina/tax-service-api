package com.example.tax_api.service;

import com.example.tax_api.model.Inventory;
import com.example.tax_api.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory getBySkuNumber(String skuNumber) {
        return inventoryRepository.findById(skuNumber)
                .orElseThrow(() ->
                        new RuntimeException("Invalid Sku Number"));

    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    //Create a row
    // Create a row
    public Inventory createRow(Inventory item) {
        try {
            return inventoryRepository.save(item);

        } catch (Exception e) {
            throw new RuntimeException("Sku Number Exists");
        }

    }

    public Inventory putMethod(String skuNumber, Inventory i) {
        inventoryRepository.findById(skuNumber)
                .orElseThrow(() ->
                        new RuntimeException("error"));

        return inventoryRepository.save(i);
    }


    //Delete
    public void deleteMethod(String skuNumber) {
        inventoryRepository.findById(skuNumber)
                        .orElseThrow(() -> new
                                RuntimeException("Sku Not Found"));
        inventoryRepository.deleteById(skuNumber);
    }


}
