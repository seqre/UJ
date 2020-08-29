package uj.jwzp2019.loghell;

import com.external.PaymentsService;
import com.internal.DiscountCalculator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uj.jwzp2019.loghell.cmd.Parser;

import java.math.BigDecimal;
import java.util.Objects;

public class TicketPurchase {

    private static final Logger logger = LoggerFactory.getLogger(TicketPurchase.class);

    public static void main(String[] args) {
        Parser parser = new Parser();
        CommandLine cmd;

        BigDecimal ticketPrice;
        int customerAge;
        long customerId;
        long companyId;
        try {
            cmd = parser.parse(args);

            ticketPrice = new BigDecimal(Objects.requireNonNull(cmd.getOptionValue("ticketPrice")));
            customerAge = Integer.parseInt(Objects.requireNonNull(cmd.getOptionValue("customerAge")));
            customerId = Long.parseLong(Objects.requireNonNull(cmd.getOptionValue("customerId")));
            companyId = Long.parseLong(Objects.requireNonNull(cmd.getOptionValue("companyId")));

            logger.info("Received: " + ticketPrice.toString() + " " + customerAge + " " + customerId + " " + companyId);

            DiscountCalculator discountCalculator = new DiscountCalculator();
            PaymentsService paymentsService = new PaymentsService();

            BigDecimal price = discountCalculator.calculateDiscount(ticketPrice, customerAge);
            logger.info("Discounted price: " + price.toString());

            boolean paymentResult = paymentsService.makePayment(customerId, companyId, price);
        } catch (ParseException | NumberFormatException | NullPointerException e) {
            logger.info("Arguments parsing failed");
            e.printStackTrace();
        }

    }
}
