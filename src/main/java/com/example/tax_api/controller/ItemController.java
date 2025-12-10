package com.example.tax_api.controller;

import com.example.tax_api.model.ItemDetails;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("api/item")

public class ItemController {



    @GetMapping("/{skuNumber}")
            public ResponseEntity<ItemDetails> getItem(@PathVariable String skuNumber) {

        // Store multiple items using map

        Map<String, ItemDetails> item = new HashMap<>();
        item.put("DOVE1", new ItemDetails("Dove shampoo", "DOVE1", "Dove", 22.5d));
        item.put("SUNSILK123", new ItemDetails("Sunsilk shampoo", "SUNSILK123","Sunsilk",15.67d));
        item.put("CLINICPLUS123", new ItemDetails("clinicplus shampoo", "CLINICPLUS123","clinicplus",35.67d));


       ItemDetails result = item.get(skuNumber);


       // store itemdetails once only
       /*ItemDetails id = new ItemDetails();
        id.setSkuNumber("DOVE123");
        id.setProductDesc("Dove Shampoo");
        id.setVendorName("Dove");
        id.setProdAmount(22.5d); */

        return ResponseEntity.ok(result);

    }
}
