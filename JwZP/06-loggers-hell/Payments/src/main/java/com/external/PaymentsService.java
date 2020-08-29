package com.external;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class PaymentsService {

    private static Logger logger = LogManager.getLogger(PaymentsService.class);

    public boolean makePayment(long customerId, long companyId, BigDecimal amount) {
        logger.info("Begging payment transaction from " + customerId + " to " + companyId);
        logger.info("Will transfer " + amount + " dollars");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
            logger.error("Something went wrong during payment, rolled back");
            return false;
        }
        logger.debug("Transaction for customer " + customerId + " in progress");
        logger.info("Transaction for customer" + customerId + " finished successfully");
        return true;
    }

}
