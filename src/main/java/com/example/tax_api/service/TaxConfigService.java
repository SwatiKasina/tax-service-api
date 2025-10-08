package com.example.tax_api.service;

import com.example.tax_api.model.TaxConfig;
import com.example.tax_api.repository.TaxConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxConfigService {

    private final TaxConfigRepository repository;

    public TaxConfigService(TaxConfigRepository repository) {
        this.repository = repository;
    }

    public List<TaxConfig> getAllConfigs() {
        return repository.findAll();
    }

    public List<TaxConfig> getListFromQuery() {
        return repository.getListFromQuery();
    }

    public TaxConfig createConfig(TaxConfig config) {
        return repository.save(config);
    }

    public TaxConfig getConfigById(Long id) {

        // fix this one for me dolly
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TaxConfig not found with id: " + id));
    }

    public void deleteConfig(Long id) {
        repository.deleteById(id);
    }
}


