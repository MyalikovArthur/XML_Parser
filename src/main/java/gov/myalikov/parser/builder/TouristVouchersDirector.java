package gov.myalikov.parser.builder;

import gov.myalikov.parser.builder.impl.TouristVouchersDOMBuilder;
import gov.myalikov.parser.builder.impl.TouristVouchersSAXBuilder;
import gov.myalikov.parser.builder.impl.TouristVouchersStAXBuilder;
import gov.myalikov.parser.entity.TouristVouchers;
import gov.myalikov.parser.entity.Voucher;

public class TouristVouchersDirector {

    public static TouristVouchers createTouristVouchers(TouristVouchersBuilder builder) {
        builder.buildTouristVouchers();
        return builder.getTouristsVouchers();
    }
}
