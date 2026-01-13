package com.example.tax_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "inventory", schema = "dolly")
@Data
@NoArgsConstructor
@AllArgsConstructor

        public class Inventory {
    @Id

    private String skuNumber;
    private String itemDesc;
    private String vendor;
    private double cost;
}
