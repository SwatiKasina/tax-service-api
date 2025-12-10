package com.example.tax_api.controller;


import com.example.tax_api.model.ItemDetails;
import com.example.tax_api.model.PosSaleRequest;
import com.example.tax_api.service.PaymentService;
import com.example.tax_api.util.PosRequestValidationsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping("/api/pos")
@RequiredArgsConstructor
public class PosSystemController {

    private final PaymentService paymentService;
    private final RestTemplate restTemplate;


    @PostMapping(value = "/sale", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> completeSale(@RequestBody PosSaleRequest request) {

        // Validations
        // card details, customer details, item details cant be empty or null
        if (request.getCardDetails() == null || request.getCustomerDetails() == null || request.getItemDetails() == null) {
            return ResponseEntity.badRequest().body("Customer, item , card details are mandatory");
        }

        // card number should be digits and should be 16
        boolean isCardNumberValid = PosRequestValidationsUtil.validateCardNumber(request.getCardDetails().getCardNumber());
        if (!isCardNumberValid) {
            return ResponseEntity.badRequest().body("card number should be 16");
        }

        // TODO cvv number and length of 3
        boolean isCvvNumberValid = PosRequestValidationsUtil.validateCvv(request.getCardDetails().getCvv());
        if(!isCvvNumberValid) {
            return ResponseEntity.badRequest().body("Cvv should be 3 digits");
        }

        // TODO customerId should  be string and not be empty

        boolean isCustomerIdValid = PosRequestValidationsUtil.validateCustomerId(request.getCustomerDetails().getCustomerId());
        if(!isCustomerIdValid){
            return ResponseEntity.badRequest().body("Customer Id is mandatory");
        }

        // TODO\ skuNumber should be string and not be empty
        boolean isSkuNumberValid = PosRequestValidationsUtil.validateSkuNumber(request.getItemDetails().getSkuNumber());
        if(!isSkuNumberValid){
            return ResponseEntity.badRequest().body("Sku Number is Mandatory");
        }





        // Processing
         // Call the respective payment service and take teh payment.

        //TODO do a rest template call to item controller and get dummy amount

        String sku = request.getItemDetails().getSkuNumber();
        double amount;

        ResponseEntity <ItemDetails> response = restTemplate.getForEntity(
                "http://localhost:8080/api/item/" + sku,
                ItemDetails.class
        );

        log.info("Response from item serice" + response);

        if (response.getStatusCode().is2xxSuccessful()) {
            amount = response.getBody().getProdAmount();

        } else {
            return ResponseEntity.internalServerError().body("Error from item service" + response);
        }



        ResponseEntity<String> paymentServiceResponse = paymentService.callPaymentService(request.getCardDetails(),
                amount);


        // Downstream
        // send item details to inventory service
        // sales details to sales service
        // customer purchase to customer service

        // create a response

        String result = "Sale completed for sku:" + sku + "|" + "Amount:" + amount + "|" + "Payment:" + paymentServiceResponse.getBody();

        return ResponseEntity.ok(result);
    }


}
