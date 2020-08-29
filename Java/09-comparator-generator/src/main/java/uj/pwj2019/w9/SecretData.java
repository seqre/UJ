package uj.pwj2019.w9;

@MyComparable
public class SecretData {

    @ComparePriority(1)
    public final int intField;

    @ComparePriority(3)
    public final boolean boolField;

    @ComparePriority(2)
    public final char charField;

    public final float floatField;

    public final String stringField;

    public SecretData(int intField, boolean boolField, char charField, float floatField, String stringField) {
        this.intField = intField;
        this.boolField = boolField;
        this.charField = charField;
        this.floatField = floatField;
        this.stringField = stringField;
    }
}
