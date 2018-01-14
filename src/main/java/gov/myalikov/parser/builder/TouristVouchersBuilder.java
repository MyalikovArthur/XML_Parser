package gov.myalikov.parser.builder;

import gov.myalikov.parser.entity.TouristVouchers;

public abstract class TouristVouchersBuilder {
    protected TouristVouchers touristVouchers = new TouristVouchers();

    TouristVouchers getTouristsVouchers() {
        return touristVouchers;
    }
    public abstract void buildTouristVouchers();
}
