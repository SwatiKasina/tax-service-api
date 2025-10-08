package com.example.tax_api.service;


import com.example.tax_api.model.CardDetails;
import com.example.tax_api.model.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {


    private final RestTemplate restTemplate;


    public ResponseEntity<String> callPaymentService(CardDetails cardDetails,
                                                     double paymentAmount) {

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCardDetails(cardDetails);
        paymentRequest.setPaymentAmount(paymentAmount);

        //TODO override method
        log.info (String.format("Calling payment service using : %s", paymentRequest.toString()));

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // TODO cal authnservice or iam and fet the bearer token
        headers.set("Authorization", "Bearer token123");
        // authorization
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity("http://localhost:8080/api/payment/sale",
                        entity, String.class);
        log.info("Response from Payment Service {}",response);
        if(response.getStatusCode().is2xxSuccessful()){
            return response;
        }
        return ResponseEntity.internalServerError().body("Error from the payment service"+response);
    }


}
