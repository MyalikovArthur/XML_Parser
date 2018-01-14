package gov.myalikov.parser.entity;

import gov.myalikov.parser.entity.enumeration.AirConditioning;
import gov.myalikov.parser.entity.enumeration.Meals;
import gov.myalikov.parser.entity.enumeration.Transport;
import gov.myalikov.parser.entity.enumeration.Tv;

public class Voucher {

    private String country;
    private Integer duration;
    private Transport transport;
    private Hotel hotel;
    private Cost cost;
    private String type;
    private String id;
    public String getCountry() {
        return country;
    }
    public void setCountry(String value) {
        this.country = value;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer value) {
        this.duration = value;
    }
    public Transport getTransport() {
        return transport;
    }
    public void setTransport(Transport value) {
        this.transport = value;
    }
    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel value) {
        this.hotel = value;
    }
    public Cost getCost() {
        return cost;
    }
    public void setCost(Cost value) {
        this.cost = value;
    }
    public String getType() {
        if (type == null) {
            return "N/A";
        } else {
            return type;
        }
    }
    public void setType(String value) {
        this.type = value;
    }
    public String getID() {
        return id;
    }
    public void setID(String value) {
        this.id = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Voucher voucher = (Voucher) o;

        if (country != null ? !country.equals(voucher.country) : voucher.country != null) return false;
        if (duration != null ? !duration.equals(voucher.duration) : voucher.duration != null) return false;
        if (transport != voucher.transport) return false;
        if (hotel != null ? !hotel.equals(voucher.hotel) : voucher.hotel != null) return false;
        if (cost != null ? !cost.equals(voucher.cost) : voucher.cost != null) return false;
        if (type != null ? !type.equals(voucher.type) : voucher.type != null) return false;
        return id != null ? id.equals(voucher.id) : voucher.id == null;
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (transport != null ? transport.hashCode() : 0);
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                ", transport=" + transport.value() +
                ", hotel=" + hotel +
                ", cost=" + cost +
                '}';
    }


    public static class Hotel {
        private Meals meals;
        private Tv tv;
        private AirConditioning airConditioning;
        private Integer stars;
        public Meals getMeals() {
            return meals;
        }
        public void setMeals(Meals value) {
            this.meals = value;
        }
        public Tv getTv() {
            return tv;
        }
        public void setTv(Tv value) {
            this.tv = value;
        }
        public AirConditioning getAirConditioning() {
            return airConditioning;
        }
        public void setAirConditioning(AirConditioning value) {
            this.airConditioning = value;
        }
        public Integer getStars() {
            return stars;
        }
        public void setStars(Integer value) {
            this.stars = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Hotel hotel = (Hotel) o;

            if (meals != hotel.meals) return false;
            if (tv != hotel.tv) return false;
            if (airConditioning != hotel.airConditioning) return false;
            return stars != null ? stars.equals(hotel.stars) : hotel.stars == null;
        }

        @Override
        public int hashCode() {
            int result = meals != null ? meals.hashCode() : 0;
            result = 31 * result + (tv != null ? tv.hashCode() : 0);
            result = 31 * result + (airConditioning != null ? airConditioning.hashCode() : 0);
            result = 31 * result + (stars != null ? stars.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Hotel{" +
                    "meals=" + meals +
                    ", tv=" + tv.value() +
                    ", airConditioning=" + airConditioning.value() +
                    ", stars=" + stars +
                    '}';
        }
    }


    public static class Cost {
        private Integer meals;
        private Integer hotel;
        private Integer transport;
        private Integer amount;
        public Integer getMeals() {
            return meals;
        }
        public void setMeals(Integer value) {
            this.meals = value;
        }
        public Integer getHotel() {
            return hotel;
        }
        public void setHotel(Integer value) {
            this.hotel = value;
        }
        public Integer getTransport() {
            return transport;
        }
        public void setTransport(Integer value) {
            this.transport = value;
        }
        public Integer getAmount() {
            return amount;
        }
        public void setAmount(Integer value) {
            this.amount = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cost cost = (Cost) o;

            if (meals != null ? !meals.equals(cost.meals) : cost.meals != null) return false;
            if (hotel != null ? !hotel.equals(cost.hotel) : cost.hotel != null) return false;
            if (transport != null ? !transport.equals(cost.transport) : cost.transport != null) return false;
            return amount != null ? amount.equals(cost.amount) : cost.amount == null;
        }

        @Override
        public int hashCode() {
            int result = meals != null ? meals.hashCode() : 0;
            result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
            result = 31 * result + (transport != null ? transport.hashCode() : 0);
            result = 31 * result + (amount != null ? amount.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Cost{" +
                    "meals=" + meals +
                    ", hotel=" + hotel +
                    ", transport=" + transport +
                    ", amount=" + amount +
                    '}';
        }
    }
}
