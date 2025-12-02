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
}
