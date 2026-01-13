package com.example.tax_api.controller;

import com.example.tax_api.model.Inventory;
import com.example.tax_api.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

@RequiredArgsConstructor
@RequestMapping("/inventory")


public class InventoryController {

    private  final InventoryService inventoryService;

    @GetMapping("/{skuNumber}")
 public ResponseEntity<?> getInventoryById(@PathVariable String skuNumber){
        Inventory inventory = inventoryService.getBySkuNumber(skuNumber);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            String error = "Invalid Sku Number" + skuNumber;
            return ResponseEntity.status(404).body(error);
        }
    }
}
