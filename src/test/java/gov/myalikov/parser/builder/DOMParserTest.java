package gov.myalikov.parser.builder;

import gov.myalikov.parser.builder.impl.TouristVouchersDOMBuilder;
import gov.myalikov.parser.entity.TouristVouchers;
import gov.myalikov.parser.entity.Voucher;
import gov.myalikov.parser.entity.enumeration.Transport;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class DOMParserTest {
    private static final TouristVouchers touristVouchersExpected = new TouristVouchers();
    @BeforeMethod
    public void initBallAction() {
        Voucher voucher = new Voucher();
        voucher.setID("TS00000000");
        voucher.setType("holiday");
        voucher.setCountry("DOM");
        voucher.setDuration(1);
        voucher.setHotel(null);
        voucher.setTransport(Transport.valueOf("CAR"));
        Voucher.Cost cost = new Voucher.Cost();
        cost.setTransport(200);
        cost.setAmount(200);
        voucher.setCost(cost);
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher);
        touristVouchersExpected.setVouchers(voucherList);
    }

    @Test
    public void parseXMLbyDOM(){
        TouristVouchers touristVouchersBuilt =
                TouristVouchersDirector.createTouristVouchers(
                        new TouristVouchersDOMBuilder("xml/DOMtest.xml"));
        Assert.assertEquals(touristVouchersBuilt, touristVouchersExpected);
    }
}
