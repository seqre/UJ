package com.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class DiscountCalculator {

    private static final Logger logger = LoggerFactory.getLogger(DiscountCalculator.class);

    public BigDecimal calculateDiscount(BigDecimal ticketPrice, int customerAge) {
        if (customerAge < 5) {
            logger.info("Customer aged under 5, discount 100%");
            return ZERO;
        } else if (customerAge < 13) {
            logger.info("Customer aged between 5 and 13, discount 50%");
            return ticketPrice.multiply(valueOf(0.5));
        } else if (customerAge > 65) {
            logger.info("Customer over 65, discount 40%");
            return ticketPrice.multiply(valueOf(0.4));
        } else {
            logger.info("No discount for this customer");
            return ticketPrice;
        }
    }
}
