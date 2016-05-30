/*
 * CardType
 *
 * Version 1
 */
package oldcards.structure;

/**
 *
 * @author Tanya Domchenko
 */
public class CardType {
    private CardTypeName name;      // card type name
    private boolean isSent;         // true if card was sent, false - otherwise

    /**
     * @return the type name
     */
    public CardTypeName getName() {
        return name;
    }

    /**
     * @param name the type name to set
     */
    public void setName(CardTypeName name) {
        this.name = name;
    }

    /**
     * @return the sending state
     */
    public boolean isSent() {
        return isSent;
    }

    /**
     * @param isSent the value to set sending state
     */
    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
    }
    
    

}
