package model;

/**
 * The abstract class for all message types
 * mainly stores the time, and some message.
 * Mostly used to implement the Message interface.
 * 
 * @author Darryl James
 * @version Autumn 2019
 */
public abstract class AbstractMessage implements Message {


    /** The splitter for messages.*/
    protected static final String SPLIT = ":";
    
    /** The current time of the race.*/
    private final int myCurrentTime;
    
    /** The message.*/
    private final String[] myMessage;
    
    
    /**
     * The abstract message constructor holds
     * the current time and a message.
     * 
     * @param theTime the current time.
     * @param theMessage a message at current time.
     */
    public AbstractMessage(final int theTime, final String[] theMessage) {

        myCurrentTime = theTime;
        myMessage = theMessage.clone();
        
    }
    
    /**
     * Returns the message - this is a test.
     * 
     * @return the message.
     */
    public String[] getTestMessage() {
        return myMessage.clone();
    }
    
    /** 
     * Gets the current time of the race.
     * 
     * @return the current time of the race.
     */
    public int getTime() {
        return myCurrentTime;
    }

}
