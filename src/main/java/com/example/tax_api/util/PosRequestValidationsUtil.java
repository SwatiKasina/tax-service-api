package com.example.tax_api.util;

import com.example.tax_api.model.CardDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class PosRequestValidationsUtil {

    public static boolean validateCardNumber(String cardNumber) {
        try {
            return StringUtils.hasLength(cardNumber) && cardNumber.length() == 16;
        } catch (Exception e) {
            log.error("Error from validateCard number due to:" + e.getMessage());
            return false;
        }


    }

    public static boolean validateCvv(String cvv) {
        try {
            return StringUtils.hasLength(cvv) && cvv.length() == 3;
        } catch (Exception e) {
            log.error("Error from validating Cvv number due to:" + e.getMessage());
            return false;
        }
    }

    public static boolean validateCustomerId(String customerId) {
        try {
            return StringUtils.hasLength(customerId);
        } catch (Exception e) {
            log.error("Error from validating Customer Id due to " + e.getMessage());
            return false;
        }
    }

    public static boolean validateSkuNumber(String skuNumber) {
        try {
           return StringUtils.hasLength(skuNumber);
        } catch (Exception e) {
            log.error("Error from validating Sku Number" + e.getMessage());
            return false;
        }
    }






}
