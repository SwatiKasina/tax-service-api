package com.example.tax_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tax_config", schema = "dolly")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxConfig {

    @Id
    private Long id;
    private String displayName;
    private String keyName;
    private double percentage;

}
