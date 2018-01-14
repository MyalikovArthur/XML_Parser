package gov.myalikov.parser.builder;

import gov.myalikov.parser.builder.impl.TouristVouchersDOMBuilder;
import gov.myalikov.parser.builder.impl.TouristVouchersSAXBuilder;
import gov.myalikov.parser.builder.impl.TouristVouchersStAXBuilder;
import gov.myalikov.parser.entity.TouristVouchers;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParsersEqualityTest {
    private static final TouristVouchers touristVouchersExpected = new TouristVouchers();

    @Test
    public void DOMAndSAXEquality(){
        TouristVouchers touristVouchersDOM =
                TouristVouchersDirector.createTouristVouchers(
                        new TouristVouchersDOMBuilder("xml/vouchers.xml"));
        TouristVouchers touristVouchersSAX =
                TouristVouchersDirector.createTouristVouchers(
                        new TouristVouchersSAXBuilder("xml/vouchers.xml"));
        Assert.assertEquals(touristVouchersDOM, touristVouchersSAX);
    }

    @Test
    public void SAXAndStAXEquality(){
        TouristVouchers touristVouchersSAX =
                TouristVouchersDirector.createTouristVouchers(
                        new TouristVouchersSAXBuilder("xml/vouchers.xml"));
        TouristVouchers touristVouchersStAX =
                TouristVouchersDirector.createTouristVouchers(
                        new TouristVouchersStAXBuilder("xml/vouchers.xml"));
        Assert.assertEquals(touristVouchersSAX, touristVouchersStAX);
    }

    @Test
    public void StAXAndDOMEquality(){
        TouristVouchers touristVouchersDOM =
                TouristVouchersDirector.createTouristVouchers(
                        new TouristVouchersDOMBuilder("xml/vouchers.xml"));
        TouristVouchers touristVouchersStAX =
                TouristVouchersDirector.createTouristVouchers(
                        new TouristVouchersStAXBuilder("xml/vouchers.xml"));
        Assert.assertEquals(touristVouchersDOM, touristVouchersStAX);
    }
}
