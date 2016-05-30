/*
 * Author
 *
 * Version 1
 */
package oldcards.structure;

/**
 * Describes the authors of the cards
 * 
 * @author Tanya Domchenko
 * @version 1, 01 May 2016
 */
public class Author {
    private String name;    // author first name
    private String surname; // author surname

    /**
     * Default constructor
     */
    public Author() {
        
    }
    
    /**
     * Constructor specifying author's name and surname
     * 
     * @param name  the first name
     * @param surname  the surname 
     */
    public Author( String name, String surname ) {
        this.name = name;
        this.surname = surname;
    }
    
    /**
     * @return the first name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the first name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }
}
