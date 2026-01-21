package com.example.tax_api.controller;

import com.example.tax_api.model.Inventory;
import com.example.tax_api.model.PosSaleRequest;
import com.example.tax_api.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j

@RequiredArgsConstructor
@RequestMapping("/api/inventory")


public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{skuNumber}")
    public ResponseEntity<?> getInventoryById(@PathVariable String skuNumber) {
        Inventory inventory = inventoryService.getBySkuNumber(skuNumber);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            String error = "Invalid Sku Number" + skuNumber;
            return ResponseEntity.status(404).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllProducts() {
        List<Inventory> inv = inventoryService.getAllInventory();
        return ResponseEntity.ok(inv);
    }

    @PostMapping
            (
                    value = "/row", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE
            )
    public ResponseEntity<?> addnewRow(@RequestBody Inventory inventory) {
        try {
            Inventory saved = inventoryService.createRow(inventory);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

        @PutMapping("/{skuNumber}")
                public ResponseEntity <Inventory> put (@PathVariable String skuNumber, @RequestBody Inventory i ){
                   Inventory update = inventoryService.putMethod(skuNumber, i );
                   return ResponseEntity.ok(update);
        }

        @DeleteMapping("/{skuNumber}")
    public ResponseEntity <?> delete (@PathVariable String skuNumber){
      inventoryService.deleteMethod(skuNumber);
      return ResponseEntity.noContent().build();
        }


}
