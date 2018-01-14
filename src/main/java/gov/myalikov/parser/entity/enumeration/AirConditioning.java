package gov.myalikov.parser.entity.enumeration;

public enum AirConditioning {
    YES("Yes"),
    NO("No");

    private final String value;

    AirConditioning(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AirConditioning fromValue(String v) {
        for (AirConditioning c: AirConditioning.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
