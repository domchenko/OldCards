/*
 * XMLValidation
 *
 * Version 1
 */
package oldcards;

import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Provides xml validation by xsd scheme
 * 
 * @author Tanya Domchenko
 * @version 1, 01 May 2016
 */
public class XMLValidation {
    
    /**
     * Parses an XML document 
     * 
     * @param dataFile      the XML document file path
     * @param schemeFile    the XSD scheme file path
     * @return              error message
     */
    public static String run( String dataFile, String schemeFile ) {
        String res = "";
        try {            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setValidating( false );
            docFactory.setNamespaceAware( true );
            
            SchemaFactory factory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );            
            docFactory.setSchema( factory.newSchema(
                new Source[] { new StreamSource( schemeFile ) } ) );
            
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            builder.setErrorHandler( new ErrorHandler() {

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }
            });
            Document doc = builder.parse( new InputSource( dataFile ) );
            return "";
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            return ex.getMessage();
        }
    }
}
