package com.example.tax_api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PosSaleRequest {

    private CardDetails cardDetails;
    private CustomerDetails customerDetails;
    private ItemDetail itemDetails;

}
