package gov.myalikov.parser.validation;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator {
    private final static Logger LOGGER = LogManager.getLogger("XMLValidator");

    public static void validateXML(String xmlFilePath, String schemeFilePath) throws SAXException {
        File schemaFile = new File(schemeFilePath);
        Source xmlFile = new StreamSource(new File(xmlFilePath));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            LOGGER.log(Level.INFO, "XML file \"" + xmlFilePath + "\" is valid according to \"" + schemeFilePath + "\" scheme");
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "File not found: " + e.getMessage());
        }
    }
}
