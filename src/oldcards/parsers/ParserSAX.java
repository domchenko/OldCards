/*
 * ParserSAX
 *
 * Version 1
 */
package oldcards.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import oldcards.structure.Author;
import oldcards.structure.CardTypeName;
import oldcards.structure.CardValuable;
import oldcards.structure.OldCard;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Provides loading data from the xml file
 * (SAX parser)
 * 
 * @author Tanya Domchenko
 * @version 1, 01 May 2016
 */
public class ParserSAX {
    
    public List<OldCard> parse( String xmlPath ) {
        try {
            SAXParser parserSAX = SAXParserFactory.newInstance().newSAXParser();            
            OldCardsParserSAX myParserSAX = new OldCardsParserSAX();            
            parserSAX.parse( xmlPath, myParserSAX );
            List<OldCard> results = myParserSAX.getList();
            return results;            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            return null;
        }
    }
    
    /**
    * Provides parsing xml files with specific structure
    * 
    * @author Tanya Domchenko
    * @version 1, 01 May 2016
    */
   private class OldCardsParserSAX extends DefaultHandler {
       List<OldCard> l = new ArrayList<>();   // cards list
       OldCard card;                          // current card card
       Author author;                     // current author 
       int current;                           // tag number, allows to identify active tag

       /**
        * Returns all cards
        * 
        * @return 
        */
       public List<OldCard> getList() {
           return l;
       }

       /**
        * Occurs when the parser meets any tag 
        * 
        * @param uri       the namespace url
        * @param localName the tag name
        * @param qName     the tag name with namespace name 
        * @param attrs     the tag attributes
        */
       @Override
       public void startElement( String uri, String localName, String qName, Attributes attrs ) {
           // qName: <namespace_name><local_name>
           switch ( qName ) {
               case "oldcards": {
                   current = 0;
                   break;
               }
               case "oldcard": {
                   card = new OldCard(); 
                   card.setId( attrs.getValue( "id" ) );
                   current = 0;
                   break;
               }
               case "theme": {
                   current = 1;
                   break;
               }
               case "type": {
                   if ( attrs.getValue( "isSent" ).compareToIgnoreCase( "yes" ) == 0 ) { 
                       card.getType().setIsSent( true );
                   }
                   current = 2;
                   break;
               }
               case "country": {
                   current = 3;
                   break;
               }
               case "year": {
                   current = 4;
                   break;
               }
               case "authors": {
                   current = 0;
                   break;
               }
               case "author": {
                   author = new Author();
                   current = 0;
                   break;
               }
               case "authorspace:name" : {
                   current = 5;
                   break;
               }
               case "authorspace:surname" : {
                   current = 6;
                   break;
               }
               case "valuable": {
                   current = 7;
                   break;
               }
           }
       }

       /**
        * Occurs when the parser meets the text data
        * 
        * @param chars     the text content
        * @param start     the starting position
        * @param length    the ending position
        */
       @Override
       public void characters( char[] chars, int start, int length ) {
           String tagValue = new String( chars, start, length );
           switch ( current ) {
               case 1: { // oldcard/theme
                   card.setTheme( tagValue );
                   break;
               }
               case 2: { // ./type
                   card.getType().setName( CardTypeName.valueOf( tagValue.toUpperCase() ) );
                   break;
               }
               case 3: { // ./country
                   card.setCountry( tagValue );
                   break;
               }
               case 4: { // ./year
                   card.setYear( Integer.parseInt( tagValue ) );
                   break;
               }
               case 5: { // ./authors/author name
                   author.setName( tagValue );
                   break;
               }
               case 6: { // ./authors/author surname
                   author.setSurname( tagValue );
                   break;
               }
               case 7: { // ./valuable
                   card.setValuable( CardValuable.valueOf( tagValue.toUpperCase() ) );
                   break;
               }
           }
           current = 0;
       }

       /**
        * Occurs when the parser meets tag end
        * 
        * @param uri       the namespace url
        * @param localName the tag name
        * @param qName     the tag name including namespace
        */
       @Override
       public void endElement( String uri, String localName, String qName ) {
           switch ( qName ) {
               case "oldcard": {
                   l.add(card );
                   break;
               }
               case "author": {
                   card.getAuthors().add(author );
                   break;
               }
           }
       }

   }
}



