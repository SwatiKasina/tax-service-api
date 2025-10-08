package com.example.tax_api.controller;


import com.example.tax_api.model.PaymentRequest;
import com.example.tax_api.model.PosSaleRequest;
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
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    @PostMapping(value = "/sale", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> completePayment(@RequestBody PaymentRequest request) {

        if (request.getCardDetails() == null  || request.getPaymentAmount() == 0) {
            return ResponseEntity.badRequest().body("card details and amount are mandatory");
        }
        // card number should be digits and should be 16
        boolean isCardNumberValid = PosRequestValidationsUtil.validateCardNumber(request.getCardDetails().getCardNumber());
        if (!isCardNumberValid) {
            return ResponseEntity.badRequest().body("card number should be 16");
        }
        String result = String.format("Doing Payment from card number : %s for amount : %s", request.getCardDetails().getCardNumber()
                , request.getPaymentAmount());
        log.info(result);
        return ResponseEntity.ok("Success for : " + result);

    }
}
