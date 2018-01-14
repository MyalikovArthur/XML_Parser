package gov.myalikov.parser.entity.enumeration;

public enum Transport {
    PLANE("Plane"),
    TRAIN("Train"),
    CAR("Car"),
    SHIP("Ship");
    private final String value;

    Transport(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Transport fromValue(String v) {
        for (Transport c: Transport.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
