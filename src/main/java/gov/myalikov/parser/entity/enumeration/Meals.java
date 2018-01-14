package gov.myalikov.parser.entity.enumeration;

public enum Meals {
    RO, BB, HB, FB, AI;

    public String value() {
        return name();
    }

    public static Meals fromValue(String v) {
        return valueOf(v);
    }

}
