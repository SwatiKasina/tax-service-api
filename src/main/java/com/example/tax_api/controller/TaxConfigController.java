package com.example.tax_api.controller;

import com.example.tax_api.model.TaxConfig;
import com.example.tax_api.service.TaxConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tax/config")
@Slf4j
@RequiredArgsConstructor
public class TaxConfigController {

    private final TaxConfigService service;

    // GET /api/tax/config
    @GetMapping
    public List<TaxConfig> getAllConfigs() {
        return service.getAllConfigs();
    }

    // GET /api/tax/config/query
    @GetMapping("/query")
    public List<TaxConfig> getListFromQuery() {
        log.info("Getting data using custom query");
        return service.getListFromQuery();
    }

    // GET /api/tax/config/{id}
    @GetMapping("/{id}")
    public TaxConfig getConfigById(@PathVariable Long id) {
        return service.getConfigById(id);
    }

    // POST /api/tax/config
    @PostMapping
    public TaxConfig createConfig(@RequestBody TaxConfig config) {
        return service.createConfig(config);
    }

    // PUT /api/tax/config/{id}
    @PutMapping("/{id}")
    public TaxConfig updateConfig(@PathVariable Long id, @RequestBody TaxConfig updatedTax) {
        return service.updateConfig(id, updatedTax);
    }

    // DELETE /api/tax/config/{id}
    @DeleteMapping("/{id}")
    public void deleteConfig(@PathVariable Long id) {
        service.deleteConfig(id);
    }
}
