package gov.myalikov.parser.entity.enumeration;

public enum Tv {
    YES("Yes"),
    NO("No");

    private final String value;

    Tv(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Tv fromValue(String v) {
        for (Tv c: Tv.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
