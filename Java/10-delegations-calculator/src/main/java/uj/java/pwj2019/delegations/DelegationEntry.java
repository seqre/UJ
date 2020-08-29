package uj.java.pwj2019.delegations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class DelegationEntry {

    private final ZonedDateTime start;
    private final ZonedDateTime end;
    private final BigDecimal rate;

    public DelegationEntry(String start, String end, BigDecimal rate) {
        this.start = parseDate(start);
        this.end = parseDate(end);
        this.rate = rate;
    }

    //2019-01-02 13:00 Europe/Warsaw,2019-01-02 15:00 Europe/Berlin
    //2007-12-03T10:15:30+01:00[Europe/Paris]

    private static ZonedDateTime parseDate(String date) {
        String[] args = date.split(" ");
        ZoneId zoneId = ZoneId.of(args[2]);
        String result = args[0] + "T" + args[1] + ":00" + zoneId.getRules().getOffset(LocalDateTime.MAX) + "[" + zoneId + "]";

        return ZonedDateTime.parse(result);
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "[start=\"" + start.toString()
                + ", end=\"" + end.toString()
                + ", rate=" + rate.toString();
    }
}
