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
import org.xml.sax.SAXException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TouristVouchersStAXBuilder extends TouristVouchersBuilder {
    private final static Logger LOGGER = LogManager.getLogger("TouristVouchersStAXBuilder");

    private String XMLfilename;

    public TouristVouchersStAXBuilder(String XMLfilename) {
        this.XMLfilename = XMLfilename;
    }

    @Override
    public void buildTouristVouchers() {
        List<Voucher> voucherList = new ArrayList<>();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        FileInputStream inputStream = null;
        XMLStreamReader reader;
        String name;
        try {
            XMLValidator.validateXML(XMLfilename, "xml/validation.xsd");
            inputStream = new FileInputStream(new File(XMLfilename));
            reader = inputFactory.createXMLStreamReader(inputStream);
            LOGGER.log(Level.INFO, "Parsing has been stated by StAX");
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (VoucherEnum.valueOf(name.toUpperCase()) == VoucherEnum.VOUCHER) {
                        Voucher voucher = buildVoucher(reader);
                        voucherList.add(voucher);
                    }
                }
            }
            LOGGER.log(Level.INFO, "Parsing has been ended by StAX");
            touristVouchers.setVouchers(voucherList);
        } catch (XMLStreamException ex) {
            LOGGER.log(Level.ERROR, "StAX parsing error: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.ERROR,"File not found: " + ex.getMessage());
        } catch (SAXException e) {
            LOGGER.log(Level.ERROR, "Invalid xml file structure: " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "I/O error: " + e.getMessage());
            }
        }
    }

    private Voucher buildVoucher(XMLStreamReader reader) throws XMLStreamException {
        Voucher voucher = new Voucher();
        voucher.setType(reader.getAttributeValue(null, VoucherEnum.TYPE.getValue()));
        voucher.setID(reader.getAttributeValue(null, VoucherEnum.ID.getValue()));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    switch (VoucherEnum.valueOf(reader.getLocalName().toUpperCase())) {
                        case COUNTRY:
                            voucher.setCountry(getXMLText(reader));
                            break;
                        case DURATION:
                            voucher.setDuration(Integer.parseInt(getXMLText(reader)));
                            break;
                        case TRANSPORT:
                            voucher.setTransport(Transport.valueOf((getXMLText(reader).toUpperCase())));
                            break;
                        case HOTEL:
                            voucher.setHotel(buildHotel(reader));
                            break;
                        case COST:
                            voucher.setCost(buildCost(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (VoucherEnum.valueOf(name.toUpperCase()) == VoucherEnum.VOUCHER) {
                        return voucher;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Voucher");
    }

    private Voucher.Hotel buildHotel(XMLStreamReader reader) throws XMLStreamException {
        Voucher.Hotel currentHotel = new Voucher.Hotel();
        currentHotel.setStars(Integer.parseInt(reader.getAttributeValue(null, VoucherEnum.STARS.getValue())));
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    switch (VoucherEnum.valueOf(reader.getLocalName().toUpperCase().replace("-", "_"))) {
                        case MEALS:
                            currentHotel.setMeals(Meals.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                        case TV:
                            currentHotel.setTv(Tv.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                        case AIR_CONDITIONING:
                            currentHotel.setAirConditioning(AirConditioning.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (VoucherEnum.valueOf(reader.getLocalName().toUpperCase().replace("-", "_")) == VoucherEnum.HOTEL) {
                        return currentHotel;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Hotel");
    }

    private Voucher.Cost buildCost(XMLStreamReader reader) throws XMLStreamException {
        Voucher.Cost currentCost = new Voucher.Cost();
        currentCost.setAmount(Integer.parseInt(reader.getAttributeValue(null, VoucherEnum.AMOUNT.getValue())));
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    switch (VoucherEnum.valueOf(reader.getLocalName().toUpperCase().replace("-", "_"))) {
                        case MEALS_COST:
                            currentCost.setMeals(Integer.parseInt(getXMLText(reader)));
                            break;
                        case HOTEL_COST:
                            currentCost.setHotel(Integer.parseInt(getXMLText(reader)));
                            break;
                        case TRANSPORT_COST:
                            currentCost.setTransport(Integer.parseInt(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (VoucherEnum.valueOf(reader.getLocalName().toUpperCase().replace("-", "_")) == VoucherEnum.COST) {
                        return currentCost;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Cost");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
