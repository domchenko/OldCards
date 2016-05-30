/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oldcards;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import oldcards.parsers.ParserDOM;
import oldcards.parsers.ParserSAX;
import oldcards.parsers.ParserStAX;
import oldcards.structure.OldCard;

/**
 *
 * @author Tanya Domchenko
 */
public class OldCards {    
    
    /**
     * Sorting cards by the picture name 
     */
    static class SortByThemeComparator implements Comparator<OldCard> {

        @Override
        public int compare(OldCard o1, OldCard o2) {
            int res = o1.getTheme().toString().compareToIgnoreCase( 
                    o2.getTheme().toString() );
            if ( res == 0 ) {
                return o1.getYear() - o2.getYear();
            }
            return res;
        }        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String xmlData = "resources\\oldcards_data.xml";
        
        System.out.println( "Validating xml by its scheme..." );
        String err = XMLValidation.run( 
                xmlData, "resources\\oldcards.xsd" );
        if ( err == null || err.isEmpty() ) {                
            System.out.println( "Ok" );
        }
        else {
            System.out.println( "\nSorry, validation failed: " + err );
            return;
        }
        
        System.out.println( "\nSAX parser:" ); 
        ParserSAX pSAX = new ParserSAX();
        List<OldCard> res = pSAX.parse( xmlData );
        print( res );
        
        System.out.println( "\n\nDOM parser:" );
        ParserDOM pDOM = new ParserDOM();
        res = pDOM.parse( xmlData );
        print( res );
        
        System.out.println( "\n\nStAX parser:" );
        ParserStAX pStAX = new ParserStAX();
        res = pStAX.parse( xmlData );
        print( res ); 
        
        System.out.println( "\n\nTransforming xml to htm..." );
        System.out.println( saveAsHTML( xmlData, "resources\\oldcards.xsl" ) );
    }
    
    /**
     * Prints data loaded from xml file
     * @param list  medicine list
     */
    public static void print( List<OldCard> list ) {
        if ( list != null ) {
            SortByThemeComparator comparator = new SortByThemeComparator();
            list.sort( comparator );
            for ( OldCard card: list ) {
                System.out.println( card.toString() );
            }
        }
    }
    
    /**
     * Prints data as html
     * 
     * @param xmlPath
     * @param xslPath
     * @return 
     */
    public static String saveAsHTML( String xmlPath, String xslPath ) {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = factory.newTransformer(
                    new StreamSource( xslPath ) );
            String htmlPath = xmlPath.replace( ".xml", ".htm" );
            transformer.transform(
                    new StreamSource( xmlPath ),
                    new StreamResult( htmlPath ) );
            File f = new File( htmlPath );
            if ( f.length() > 0 ) {
                return htmlPath;
            }
        } catch (TransformerConfigurationException ex) {
            System.out.println( ex.getMessage() );
        } catch (TransformerException ex) {
            System.out.println( ex.getMessage() );
        }
        return "";
    }
    
}
