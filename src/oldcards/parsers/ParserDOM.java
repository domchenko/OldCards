/*
 * ParserDOM
 *
 * Version 1
 */
package oldcards.parsers;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oldcards.structure.CardTypeName;
import oldcards.structure.CardValuable;
import oldcards.structure.OldCard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * Provides loading data from xml file
 * (DOM parser)
 * 
 * @author Tanya Domchenko
 * @version 1, 01 May 2016
 */
public class ParserDOM {
    
    /**
     * Load old cards items from the file
     * 
     * @param xmlPath   the file path
     * @return          a list of the cards
     */
    public List<OldCard> parse( String xmlPath ) {
        List<OldCard> list = new ArrayList<>();
        DOMParser parser = new DOMParser();
        try {
            parser.parse( xmlPath );
        } catch (SAXException | IOException ex) {
            return null;
        }
        
        Document doc = parser.getDocument();
        Element root = doc.getDocumentElement(); 
        
        NodeList tagList = root.getElementsByTagName( "oldcard" );
        for ( int i = 0; i < tagList.getLength(); i++ ) {
            OldCard card = new OldCard();
            
            Element cardTag = (Element) tagList.item( i );
            String attrValue = cardTag.getAttribute( "id" ); 
            card.setId( attrValue );
            
            card.setTheme( getChildNodeValue( cardTag, "theme" ) );            
            Element typeTag = (Element) cardTag.getElementsByTagName( "type" ).item(0);
            card.getType().setName( CardTypeName.valueOf(
                    getNodeValue( typeTag ).toUpperCase() ) );
            if ( typeTag.getAttribute( "isSent" ).compareToIgnoreCase( "yes" ) == 0 ) {
                card.getType().setIsSent( true );
            }
            card.setCountry( getChildNodeValue( cardTag, "country" ) );
            card.setYear( Integer.parseInt(
                    getChildNodeValue( cardTag, "year" ) ) );
            card.setValuable( CardValuable.valueOf( 
                    getChildNodeValue( cardTag, "valuable" ).toUpperCase() ) );            
            
            Element authorsTag = (Element) cardTag.getElementsByTagName( "authors" ).item(0);
            parseAuthors( card, authorsTag );
            
            list.add( card );
        }
        
        return list;
    } 
    
    /**
     * Returns sub node value by its name
     * 
     * @param parent    the parent element
     * @param name      the child element name
     * @return          the node value
     */
    private static String getChildNodeValue( Element parent, String name ) {
        String res = "";
        Element elem = (Element) parent.getElementsByTagName( name ).item(0);
        if ( elem != null ) {
            Text nodeData = (Text) elem.getFirstChild();
            res = nodeData.getTextContent();
        }
        return res;
    }
    
    /**
     * Returns node value
     * 
     * @param elem  the element
     * @return      the node value
     */
    private static String getNodeValue( Element elem ) {
        Text nodeData = (Text) elem.getFirstChild();
        return nodeData.getTextContent();
    }
    
    /**
     * Parsing the card authors
     * 
     * @param card      the card item
     * @param authors   the tag containing a list of authors of the card
     */
    private static void parseAuthors( OldCard card, Element authors ) {
        if ( authors == null ) {
            return;
        }
        NodeList tagList = authors.getElementsByTagName( "author" );
        for ( int i = 0; i < tagList.getLength(); i++ ) {
            card.addAuthor( 
                    getChildNodeValue( (Element) tagList.item(i), "authorspace:name" ),
                    getChildNodeValue( (Element) tagList.item(i), "authorspace:surname" ) );
        }
    }
   
}
