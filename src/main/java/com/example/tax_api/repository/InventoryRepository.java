package com.example.tax_api.repository;

import com.example.tax_api.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

    @Query(value = "SELECT * FROM dolly.inventory", nativeQuery = true)
    List<Inventory> getListFromQuery();
}

