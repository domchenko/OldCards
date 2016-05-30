/*
 * ParserStAX
 *
 * Version 1
 */
package oldcards.parsers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import oldcards.structure.Author;
import oldcards.structure.CardTypeName;
import oldcards.structure.CardValuable;
import oldcards.structure.OldCard;

/**
 * Provides loading data from xml file
 * (StAX parser)
 *
 * @author Tanya Domchenko
 * @version 1, 01 May 2016
 */
public class ParserStAX {

    /**
     * Load cards items from the file
     * 
     * @param xmlPath   the file path
     * @return          a list of the drugs  
     */
    public List<OldCard> parse( String xmlPath ) {
        List<OldCard> list = new ArrayList<>();
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader;            
        try {            
            eventReader = factory.createXMLEventReader(
                    new FileReader( xmlPath ) );
        } catch (XMLStreamException | FileNotFoundException ex) {
            return null;
        }
        factory.setProperty( XMLInputFactory.IS_NAMESPACE_AWARE, true );

        OldCard card = null;
        Author author = null;
        int current = 0;

        while ( eventReader.hasNext() ) {
            XMLEvent event;
            try {
                event = eventReader.nextEvent();
            } catch (XMLStreamException ex) {
                return null;
            }

            Attribute attr;        
            String tagName;

            switch ( event.getEventType() ){
                // Occurs when the parser meets the beginning of the tag
                case XMLStreamConstants.START_ELEMENT: {
                    StartElement startElement = event.asStartElement();
                    tagName = startElement.getName().getLocalPart();
                    String prefix = startElement.getName().getPrefix();
                    if ( !prefix.isEmpty() ) {
                        tagName = prefix + ":" + tagName;
                    }
                    switch ( tagName ) {
                        case "oldcard": {
                            card = new OldCard();
                            attr = startElement.getAttributeByName( QName.valueOf( "id" ) );
                            card.setId( attr.getValue() );
                            break;
                        }
                        case "theme" : {
                            current = 1;
                            break;
                        }
                        case "type" : {
                            attr = startElement.getAttributeByName( QName.valueOf( "isSent" ) );
                            if ( attr.getValue().compareToIgnoreCase( "yes" ) == 0 ) { 
                                card.getType().setIsSent( true );
                            }
                            current = 2;
                            break;
                        }
                        case "country" : {
                            current = 3;
                            break;
                        }
                        case "year" : {
                            current = 4;
                            break;
                        }
                        case "author": {
                            author = new Author();
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
                        case "valuable" : { 
                            current = 7;
                            break;
                        }
                    }
                    break;
                }
                // Occurs when the parser meets the text content
                case XMLStreamConstants.CHARACTERS: {
                    Characters characters = event.asCharacters();
                    String nodeValue = characters.getData();
                    if ( nodeValue.isEmpty() ) {
                        break;
                    }
                    switch ( current ) {
                        case 1: {
                            card.setTheme( nodeValue );
                            break;
                        }
                        case 2: {
                            card.getType().setName( 
                                    CardTypeName.valueOf( nodeValue.toUpperCase() ) );
                            break;
                        }
                        case 3: {
                            card.setCountry( nodeValue );
                            break;
                        }
                        case 4: {
                            card.setYear( Integer.parseInt( nodeValue ) );
                            break;
                        }
                        case 5: {
                            author.setName( nodeValue );
                            break;
                        }
                        case 6: {
                            author.setSurname( nodeValue );
                            break;
                        }
                        case 7: {
                            card.setValuable( 
                                    CardValuable.valueOf( nodeValue.toUpperCase() ) );
                            break;
                        }
                    }
                    current = 0;
                    break;
                }
                // Occurs when the parser meets tag end
                case XMLStreamConstants.END_ELEMENT: {
                    EndElement endElement = event.asEndElement();
                    tagName = endElement.getName().getLocalPart();
                    if ( tagName.equalsIgnoreCase( "oldcard" ) ) {                            
                        list.add( card );
                        card = null;
                    }
                    if ( tagName.equalsIgnoreCase( "author" ) ) {                            
                        card.getAuthors().add( author );
                        author = null;
                    }
                    break;
                }
            }
        }
        
        return list;
    }
   
}

