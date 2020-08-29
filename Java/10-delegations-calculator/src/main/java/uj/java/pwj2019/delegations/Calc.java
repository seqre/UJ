package uj.java.pwj2019.delegations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

import static uj.java.pwj2019.delegations.Rate.getRate;

public class Calc {

    BigDecimal calculate(String start, String end, BigDecimal dailyRate) throws IllegalArgumentException {
        DelegationEntry entry = new DelegationEntry(start, end, dailyRate);
        System.out.println(entry.toString());
        return calculateMoney(entry);
    }

    private BigDecimal calculateMoney(DelegationEntry delegationEntry) {
        if (delegationEntry.getStart().isBefore(delegationEntry.getEnd())) {
            BigDecimal result = BigDecimal.ZERO;

            long hours = delegationEntry.getStart().until(delegationEntry.getEnd(), ChronoUnit.HOURS);
            if (hours == 0 && !delegationEntry.getStart().equals(delegationEntry.getEnd())) hours = 1;
            System.out.println(hours);

            long days;

            days = hours / 24;
            hours %= 24;

            result = result.add(delegationEntry.getRate().multiply(BigDecimal.valueOf(days)));
            if (hours != 0) {
                result = result.add(delegationEntry.getRate().divide(getRate(hours).getDivider(), 2, RoundingMode.HALF_UP));
            }

            return result;
        } else {
            return BigDecimal.ZERO.divide(BigDecimal.ONE,2,RoundingMode.HALF_UP);
        }
    }

}
