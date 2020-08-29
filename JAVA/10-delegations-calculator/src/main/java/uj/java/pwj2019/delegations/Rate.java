package uj.java.pwj2019.delegations;

import java.math.BigDecimal;

public enum Rate {
    FULL            (24, 24, BigDecimal.ONE),
    MORE_THAN_12    (12, 12, BigDecimal.ONE),
    FROM_8_TO_12    (8,  8,  BigDecimal.valueOf(2)),
    UP_TO_8_HOURS   (0,  8,  BigDecimal.valueOf(3));

    private int lowerLimit;
    private int substracter;
    private BigDecimal divider;

    Rate(int lowerLimit, int substracter, BigDecimal divider) {
        this.lowerLimit = lowerLimit;
        this.substracter = substracter;
        this.divider = divider;
    }

    public static Rate getRate(long hours) {
        if (hours >= FULL.lowerLimit) return FULL;
        if (hours > MORE_THAN_12.lowerLimit) return MORE_THAN_12;
        if (hours > FROM_8_TO_12.lowerLimit) return FROM_8_TO_12;
        else return UP_TO_8_HOURS;
    }

    public long getHoursMultiplier(long hours) {
        return this == FULL ? hours / 24 : 1;
    }

    public BigDecimal getDivider() {
        return divider;
    }

    public int getSubstracter() {
        return substracter;
    }
}
