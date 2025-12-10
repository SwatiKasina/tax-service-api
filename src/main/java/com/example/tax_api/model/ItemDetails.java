package com.example.tax_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetails {

    private String productDesc;
    private String skuNumber;
    private String vendorName;
    private double ProdAmount;
}
