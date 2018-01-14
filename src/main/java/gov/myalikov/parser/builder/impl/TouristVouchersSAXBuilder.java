package gov.myalikov.parser.builder.impl;

import gov.myalikov.parser.builder.TouristVouchersBuilder;
import gov.myalikov.parser.builder.enumeration.VoucherEnum;
import gov.myalikov.parser.entity.Voucher;
import gov.myalikov.parser.entity.enumeration.AirConditioning;
import gov.myalikov.parser.entity.enumeration.Meals;
import gov.myalikov.parser.entity.enumeration.Transport;
import gov.myalikov.parser.entity.enumeration.Tv;
import gov.myalikov.parser.validation.XMLValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class TouristVouchersSAXBuilder extends TouristVouchersBuilder {
    private final static Logger LOGGER = LogManager.getLogger("TouristVouchersSAXBuilder");

    private String XMLfilename;

    public TouristVouchersSAXBuilder(String XMLfilename) {
        this.XMLfilename = XMLfilename;
    }

    @Override
    public void buildTouristVouchers() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            TouristVouchersSAXHandler voucherHandler = new TouristVouchersSAXHandler();
            XMLValidator.validateXML(XMLfilename, "xml/validation.xsd");
            parser.parse(XMLfilename, voucherHandler);
            touristVouchers.setVouchers(voucherHandler.getVouchers());
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Parser configuration error: " + e.getMessage());
        } catch (SAXException e) {
            LOGGER.log(Level.ERROR, "Invalid xml file structure: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "File error or I/O error: " + e.getMessage());
        }
    }

    public static class TouristVouchersSAXHandler extends DefaultHandler {
        private final static Logger LOGGER = LogManager.getLogger("TouristVouchersSAXHandler");

        private List<Voucher> vouchers;
        private Voucher currentVoucher = null;
        private Voucher.Hotel currentHotel = null;
        private Voucher.Cost currentCost = null;
        private VoucherEnum currentEnum = null;
        private EnumSet<VoucherEnum> withText;

        public TouristVouchersSAXHandler() {
            vouchers = new ArrayList<>();
            withText = EnumSet.range(VoucherEnum.COUNTRY, VoucherEnum.TRANSPORT_COST);
        }

        List<Voucher> getVouchers(){
            return vouchers;
        }

        @Override
        public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException {
            if (qName.equals(VoucherEnum.TOURIST_VOUCHERS.getValue())) {
                LOGGER.log(Level.INFO, "Parsing has been started by SAX");
            } else if (qName.equals(VoucherEnum.VOUCHER.getValue())) {
                currentVoucher = new Voucher();
                currentVoucher.setType(attrs.getValue(VoucherEnum.TYPE.getValue()));
                currentVoucher.setID(attrs.getValue(VoucherEnum.ID.getValue()));
            } else if (qName.equals(VoucherEnum.HOTEL.getValue())) {
                currentHotel = new Voucher.Hotel();
                currentHotel.setStars(Integer.parseInt(attrs.getValue(VoucherEnum.STARS.getValue())));
            } else if (qName.equals(VoucherEnum.COST.getValue())){
                currentCost = new Voucher.Cost();
                currentCost.setAmount(Integer.parseInt(attrs.getValue(VoucherEnum.AMOUNT.getValue())));
            } else {
                VoucherEnum temp = VoucherEnum.valueOf(qName.toUpperCase().replace("-", "_"));
                if (withText.contains(temp)) {
                    currentEnum = temp;
                }
            }
        }

        @Override
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            if (VoucherEnum.TOURIST_VOUCHERS.getValue().equals(qName)){
                LOGGER.log(Level.INFO, "Parsing has been ended by SAX");
            } else if (VoucherEnum.VOUCHER.getValue().equals(qName)) {
                vouchers.add(currentVoucher);
            } else if (VoucherEnum.HOTEL.getValue().equals(qName)) {
                currentVoucher.setHotel(currentHotel);
            } else if (VoucherEnum.COST.getValue().equals(qName)) {
                currentVoucher.setCost(currentCost);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String string = new String(ch, start, length).trim();
            if (currentEnum != null) {
                switch (currentEnum) {
                    case COUNTRY:
                        currentVoucher.setCountry(string);
                        break;
                    case DURATION:
                        currentVoucher.setDuration(Integer.parseInt(string));
                        break;
                    case TRANSPORT:
                        currentVoucher.setTransport(Transport.valueOf(string.toUpperCase()));
                        break;
                    case MEALS:
                        currentHotel.setMeals(Meals.valueOf(string.toUpperCase()));
                        break;
                    case TV:
                        currentHotel.setTv(Tv.valueOf(string.toUpperCase()));
                        break;
                    case AIR_CONDITIONING:
                        currentHotel.setAirConditioning(AirConditioning.valueOf(string.toUpperCase()));
                        break;
                    case MEALS_COST:
                        currentCost.setMeals(Integer.parseInt(string));
                        break;
                    case HOTEL_COST:
                        currentCost.setHotel(Integer.parseInt(string));
                        break;
                    case TRANSPORT_COST:
                        currentCost.setTransport(Integer.parseInt(string));
                        break;
                    default:
                        throw new EnumConstantNotPresentException(
                                currentEnum.getDeclaringClass(), currentEnum.name());
                }
            }
            currentEnum = null;
        }
    }
}
