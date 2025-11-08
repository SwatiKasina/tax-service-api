package com.example.tax_api.controller;

import com.example.tax_api.model.TaxConfig;
import com.example.tax_api.service.TaxConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tax/config")
@CrossOrigin(origins = "http://tax-frontend.s3-website-us-east-1.amazonaws.com")
@Slf4j
@RequiredArgsConstructor
public class TaxConfigController {

    private final TaxConfigService service;

    // ✅ GET /api/tax/config
    @GetMapping
    public List<TaxConfig> getAllConfigs() {
        return service.getAllConfigs();
    }

    // ✅ GET /api/tax/config/query
    @GetMapping("/query")
    public List<TaxConfig> getListFromQuery() {
        log.info("Getting data using custom query");
        return service.getListFromQuery();
    }

    // ✅ GET /api/tax/config/{id}
    @GetMapping("/{id}")
    public TaxConfig getConfigById(@PathVariable Long id) {
        return service.getConfigById(id);
    }

    // ✅ POST /api/tax/config
    @PostMapping
    public TaxConfig createConfig(@RequestBody TaxConfig config) {
        return service.createConfig(config);
    }

    // ✅ DELETE /api/tax/config/{id}
    @DeleteMapping("/{id}")
    public void deleteConfig(@PathVariable Long id) {
        service.deleteConfig(id);
    }
}
