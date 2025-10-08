package com.example.tax_api.repository;

import com.example.tax_api.model.TaxConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxConfigRepository extends JpaRepository<TaxConfig, Long> {

    @Query(value = "SELECT * FROM dolly.tax_config", nativeQuery = true)
    List<TaxConfig> getListFromQuery();
}
