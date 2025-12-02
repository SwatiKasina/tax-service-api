package com.example.tax_api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDetails {

    private String cardNumber;
    private String cardHolderName;
    private String expDate;
    private String cvv;

}
