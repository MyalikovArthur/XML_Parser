package gov.myalikov.parser.builder.enumeration;

public enum VoucherEnum {
    TOURIST_VOUCHERS("vch:Tourist_vouchers"),
    VOUCHER("voucher"),
    TYPE("type"),
    ID("ID"),
    AMOUNT("amount"),
    COUNTRY("country"),
    DURATION("duration"),
    TRANSPORT("transport"),
    MEALS("meals"),
    TV("tv"),
    AIR_CONDITIONING("air-conditioning"),
    MEALS_COST("meals-cost"),
    HOTEL_COST("hotel-cost"),
    TRANSPORT_COST("transport-cost"),
    HOTEL("hotel"),
    STARS("stars"),
    COST("cost");

    private String value;

    private VoucherEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}