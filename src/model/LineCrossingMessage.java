package model;

/**
 * The line crossing message class holds
 * messages that show when a racer has 
 * finished a lap.
 * 
 * @author Darryl James
 * @version Autumn 2019
 */
public class LineCrossingMessage extends AbstractMessage {
    
    /** Determines if the racer is finished or not.*/
    private final boolean myFinishState;
    
    /** The racer's ID number.*/
    private final int myID;
    
    /** The new lap for the racer.*/
    private final int myNewLap;
    
    /** The time for the race message.*/
    private final int myTime;
    
    /**
     * The line crossing message constructor 
     * holds the time a racer finished a lap
     * and the message at that time.
     * 
     * @param theTime a racer finished a lap.
     * @param theMessage which racer finished the lap.
     */
    public LineCrossingMessage(final int theTime, final String[] theMessage) {
        super(theTime, theMessage);
        // TODO Auto-generated constructor stub
        myID = Integer.parseInt(theMessage[0]);
        myNewLap = Integer.parseInt(theMessage[1]);
        myFinishState = Boolean.parseBoolean(theMessage[2]);
        myTime = theTime;
    }
    
    /**
     * The method gets the racer's ID number.
     * 
     * @return the racer's ID number.
     */
    public int getRacerID() {
        return myID;
    }
    
    /**
     * Returns the racer's new lap number.
     * 
     * @return the racer's new lap number.
     */
    public int getNewLap() {
        return myNewLap;
    }
    
    /**
     * Returns the finished state of the racer.
     * 
     * @return if the racer is finished or not.
     */
    public boolean isFinishedState() {
        return myFinishState;
    }
    
    @Override
    public String toString() {
        return "$C:" + myTime 
               + SPLIT + myID 
               + SPLIT + myNewLap 
               + SPLIT + myFinishState;
    }

}
