package com.example.tax_api.controller;


import com.example.tax_api.model.PosSaleRequest;
import com.example.tax_api.service.PaymentService;
import com.example.tax_api.util.PosRequestValidationsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/pos")
@RequiredArgsConstructor
public class PosSystemController {

    private final PaymentService paymentService;

    @PostMapping(value = "/sale", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> completeSale(@RequestBody PosSaleRequest request) {

        // Validations
        // cardetails, customerdetails, itemdetails cant be empty or null
        if (request.getCardDetails() == null || request.getCustomerDetails() == null || request.getItemDetails() == null) {
            return ResponseEntity.badRequest().body("Customer, item , card details are mandatory");
        }
        // card number should be digits and should be 16
        boolean isCardNumberValid = PosRequestValidationsUtil.validateCardNumber(request.getCardDetails().getCardNumber());
        if (!isCardNumberValid) {
            return ResponseEntity.badRequest().body("card number should be 16");
        }
        // TODO cvv number and length of 3

        // TODO customerId should  be string and not be empty

        // TODO skuNumber should be string and not be empty


        // Processing
         // Call the respective payment service and take teh payment.
        double paymentAmount = 10.10d;
        //TODO do a rest template call to item controller and get dummy amount

        ResponseEntity<String> paymentServiceResponse = paymentService.callPaymentService(request.getCardDetails(),
                paymentAmount);


        // Downstream
        // send item details to inventory service
        // sales details to sales service
        // customer purchase to customer service

        // create a response

        return ResponseEntity.ok(request.toString());
    }


}
