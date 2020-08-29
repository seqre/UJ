package uj.java.pwj2019.w7;

import java.math.BigDecimal;

public class InsuranceEntry {

    /** ID of policy. colum: policyID */
    private final int policyId;
    /** Column: country */
    private final String country;
    /** Total insurable value in 2011. Column: tiv_2011 */
    private final BigDecimal tiv2011;
    /** Total insurable value in 2012. Column: tiv_2012 */
    private final BigDecimal tiv2012;
    /** Property line (type). Column: line **/
    private final Line line;
    /** Method of construction. Column: construction */
    private final String construction;
    /** Geographical coord, latitude. Column: latitude */
    private final double latitude;
    /** Geographical coord, longitude. Column: longitude */
    private final double longitude;

    public InsuranceEntry(
            int policyId,
            String country,
            BigDecimal tiv2011,
            BigDecimal tiv2012,
            Line line,
            String construction,
            double latitude,
            double longitude) {
        this.policyId = policyId;
        this.country = country;
        this.tiv2011 = tiv2011;
        this.tiv2012 = tiv2012;
        this.line = line;
        this.construction = construction;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int policyId() {
        return policyId;
    }

    public String country() {
        return country;
    }

    public BigDecimal tiv2011() {
        return tiv2011;
    }

    public BigDecimal tiv2012() {
        return tiv2012;
    }

    public Line line() {
        return line;
    }

    public String construction() {
        return construction;
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "InsuranceEntry{" +
                "policyId=" + policyId +
                ", country='" + country + '\'' +
                ", tiv2011=" + tiv2011 +
                ", tiv2012=" + tiv2012 +
                ", line=" + line +
                ", construction='" + construction + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
