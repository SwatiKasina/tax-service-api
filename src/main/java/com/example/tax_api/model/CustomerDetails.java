package com.example.tax_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails {

    private String customerName;
    private String customerId; //TODO later
    private String tierStatus;
}
