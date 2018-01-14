package gov.myalikov.parser.entity;

import java.util.ArrayList;
import java.util.List;

public class TouristVouchers {

    private List<Voucher> vouchers;

    public List<Voucher> getTouristVouchersList() {
        if (vouchers == null) {
            vouchers = new ArrayList<Voucher>();
        }
        return this.vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TouristVouchers that = (TouristVouchers) o;

        return vouchers != null ? vouchers.equals(that.vouchers) : that.vouchers == null;
    }

    @Override
    public int hashCode() {
        return vouchers != null ? vouchers.hashCode() : 0;
    }
}
