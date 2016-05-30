/*
 * OldCard
 *
 * Version 1
 */
package oldcards.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes medicine characteristics
 * 
 * @author Tanya Domchenko
 * @version 1, 01 May 2016
 */
public class OldCard {
    private String id;      // unique identifier
    private String theme;   // picture theme
    private CardType type;  // card type
    private String country; // country od origin
    private int year;       // year of publishing
    private List<Author> authors;       // medicine authors   
    private CardValuable valuable;      // valuable type
    
    /**
     * Default constructor 
     */
    public OldCard() {
        authors = new ArrayList<>();
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * @return the card type
     */
    public CardType getType() {
        if ( type == null ) {
            type = new CardType();
        }
        return type;
    }

    /**
     * @param type the card type to set
     */
    public void setType(CardType type) {
        this.type = type;
    }

    /**
     * @return the country of origin
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country of origin to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the year of publishing
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year of publishing to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the authors
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
    /**
     * Adds a new author 
     * 
     * @param firstName the author's name
     * @param surname the author's surname
     */
    public void addAuthor( String firstName, String surname ) {
        Author pAuthor = new Author( firstName, surname );
        authors.add( pAuthor );
    }

    /**
     * @return the valuable
     */
    public CardValuable getValuable() {
        return valuable;
    }

    /**
     * @param valuable the valuable to set
     */
    public void setValuable(CardValuable valuable) {
        this.valuable = valuable;
    }
    
    @Override
    public String toString() {
        String str = "[" + getId() + "] ";
        str += type.getName() + " card with " + theme + " on it";
        str += ", designed in " + country;
        str += " in " + year;
        if ( type.isSent() ) {
          str += ", was sent";
        }
        if ( authors.size() > 0 ) {
            str += "\nAuthor(s): ";
        }
        String strList = "";
        for ( int i = 0; i < authors.size(); i++ ) {
            strList += ( strList.isEmpty() ? "" : ", " ) + authors.get(i).toString();
        }
        return str + strList;
    }
}
