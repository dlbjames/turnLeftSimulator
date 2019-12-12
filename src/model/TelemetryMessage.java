package model;

/**
 * The Telemetry class holds messages
 * for the distance a racer has moved.
 * 
 * @author Darryl James
 * @version Autumn 2019
 */
public class TelemetryMessage extends AbstractMessage {
    
    /** The id of the racer.*/
    private final int myID;
    
    /** The distance of the racer.*/
    private final double myDist;
    
    /** The lap of the racer.*/
    private final int myLap;
    
    /** The time for the race.*/
    private final int myTime;
    
    /**
     * The Telemetry message constructor holds
     * the time of the message and the 
     * information about the racer and their 
     * distance moved.
     * 
     * @param theTime the time of the message.
     * @param theMessage which racer moved where.
     */
    public TelemetryMessage(final int theTime, final String[] theMessage) {
        super(theTime, theMessage);
        // TODO Auto-generated constructor stub
        myID = Integer.parseInt(theMessage[0]);
        myDist = Double.parseDouble(theMessage[1]);
        myLap = Integer.parseInt(theMessage[2]);
        myTime = theTime;
    }
    
    /**
     * The method gets the racer's ID number.
     * 
     * @return the racer's ID number.
     */
    public int getRacerId() {
        return myID;
    }
    
    /**
     * Returns the current lap the racer is on.
     * 
     * @return the current lap the racer is on.
     */
    public int getLap() {
        return myLap;
    }
    
    /**
     * Return the distance of the racer.
     * 
     * @return the distance of the racer.
     */
    public double getDistance() {
        return myDist;
    }
    
    @Override
    public String toString() {
        return "$T:" + myTime 
               + SPLIT + myID 
               + SPLIT + myDist 
               + SPLIT + myLap;
    }

}
