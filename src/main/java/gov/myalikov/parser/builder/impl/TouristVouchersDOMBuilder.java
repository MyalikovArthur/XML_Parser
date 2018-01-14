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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TouristVouchersDOMBuilder extends TouristVouchersBuilder {
    private final static Logger LOGGER = LogManager.getLogger("TouristVouchersDOMBuilder");

    private String XMLfilename;

    public TouristVouchersDOMBuilder(String XMLfilename) {
        this.XMLfilename = XMLfilename;
    }

    @Override
    public void buildTouristVouchers() {
        try {
            LOGGER.log(Level.INFO, "Parsing has been started by DOM");
            List<Voucher> voucherList = new ArrayList<>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            XMLValidator.validateXML(XMLfilename, "xml/validation.xsd");
            Document document = builder.parse(XMLfilename);
            Element root = document.getDocumentElement();
            NodeList vouchersList = root.getElementsByTagName(VoucherEnum.VOUCHER.getValue());
            for (int i = 0; i < vouchersList.getLength(); i++) {
                Element voucherElement = (Element) vouchersList.item(i);
                Voucher voucher = buildVoucher(voucherElement);
                voucherList.add(voucher);
            }
            touristVouchers.setVouchers(voucherList);
            LOGGER.log(Level.INFO, "Parsing has been ended by DOM");
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.ERROR, "Parser configuration error: " + e.getMessage());
        } catch (SAXException e) {
            LOGGER.log(Level.ERROR, "Invalid xml file structure: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "File error or I/O error: " + e.getMessage());
        }
    }
    private Voucher buildVoucher(Element voucherElement) {
        Voucher voucher = new Voucher();
        voucher.setType(voucherElement.getAttribute(VoucherEnum.TYPE.getValue()));
        voucher.setID(voucherElement.getAttribute(VoucherEnum.ID.getValue()));
        voucher.setCountry(getElementTextContent(voucherElement, VoucherEnum.COUNTRY.getValue()));
        if (getElementTextContent(voucherElement, VoucherEnum.DURATION.getValue()) != null) {
            voucher.setDuration(Integer.parseInt(getElementTextContent(voucherElement, VoucherEnum.DURATION.getValue())));
        }
        voucher.setTransport(Transport.valueOf(getElementTextContent(voucherElement, VoucherEnum.TRANSPORT.getValue()).toUpperCase()));
        Voucher.Hotel hotel = new Voucher.Hotel();
        if (voucherElement.getElementsByTagName(VoucherEnum.HOTEL.getValue()).getLength() != 0) {
            Element hotelElement = (Element) voucherElement.getElementsByTagName(VoucherEnum.HOTEL.getValue()).item(0);
            hotel.setStars(Integer.parseInt(hotelElement.getAttribute(VoucherEnum.STARS.getValue())));
            hotel.setMeals(Meals.valueOf(getElementTextContent(hotelElement, VoucherEnum.MEALS.getValue()).toUpperCase()));
            hotel.setTv(Tv.valueOf(getElementTextContent(hotelElement, VoucherEnum.TV.getValue()).toUpperCase()));
            hotel.setAirConditioning(AirConditioning.valueOf(getElementTextContent(hotelElement, VoucherEnum.AIR_CONDITIONING.getValue()).toUpperCase()));
            voucher.setHotel(hotel);
        }
        Voucher.Cost cost = new Voucher.Cost();
        Element costElement = (Element) voucherElement.getElementsByTagName(VoucherEnum.COST.getValue()).item(0);
        cost.setAmount(Integer.parseInt(costElement.getAttribute(VoucherEnum.AMOUNT.getValue())));
        if ((getElementTextContent(costElement, VoucherEnum.MEALS_COST.getValue())) != null) {
            cost.setMeals(Integer.parseInt((getElementTextContent(costElement, VoucherEnum.MEALS_COST.getValue()))));
        }
        if(getElementTextContent(costElement, VoucherEnum.HOTEL_COST.getValue()) != null){
            cost.setHotel(Integer.parseInt(getElementTextContent(costElement, VoucherEnum.HOTEL_COST.getValue())));
        }
        if(getElementTextContent(costElement, VoucherEnum.TRANSPORT_COST.getValue()) != null){
            cost.setTransport(Integer.parseInt(getElementTextContent(costElement, VoucherEnum.TRANSPORT_COST.getValue())));
        }
        voucher.setCost(cost);
        return voucher;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        if (node == null){
            return null;
        }
        return node.getTextContent();
    }
}
