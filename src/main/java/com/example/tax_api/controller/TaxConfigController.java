package com.example.tax_api.controller;

import com.example.tax_api.model.TaxConfig;
import com.example.tax_api.service.TaxConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tax")
@Slf4j
@RequiredArgsConstructor
public class TaxConfigController {

    private final TaxConfigService service;

    @GetMapping("/config")
    public List<TaxConfig> getAllConfigs() {
        return service.getAllConfigs();
    }

    @GetMapping("/config/query")
    public List<TaxConfig> getListFromQuery() {
        log.info("Getting data using custom query");
        return service.getListFromQuery();
    }

    @GetMapping("/config/{id}")
    public TaxConfig getConfigById(@PathVariable Long id) {
        return service.getConfigById(id);
    }

    @PostMapping("/config")
    public TaxConfig createConfig(@RequestBody TaxConfig config) {
        return service.createConfig(config);
    }

    @DeleteMapping("/config/{id}")
    public void deleteConfig(@PathVariable Long id) {
        service.deleteConfig(id);
    }


}
