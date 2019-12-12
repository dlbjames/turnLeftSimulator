package model;

import java.util.Arrays;

/**
 * The leader board message class is a specific
 * type of message that determines which racers
 * are in which position in the race (1st, 2nd, ...).
 * 
 * @author Darryl James
 * @version Autumn 2019
 */
public class LeaderBoardMessage extends AbstractMessage {

    /** The time for the standings.*/
    private final int myTime;
    
    /** The current standings of the racers.*/
    private final String[] myRacers;
    
    /**
     * The leader board message constructor
     * keeps track of the current time and all
     * the racers positions in the race in an array.
     * Where the 0 index is the racer in 1st place.
     * 
     * @param theTime time of the leader board update.
     * @param theMessage the rankings at the time.
     */
    public LeaderBoardMessage(final int theTime, final String[] theMessage) {
        super(theTime, theMessage);
        myRacers = Arrays.copyOf(theMessage, theMessage.length);
        myTime = theTime;
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Return a list of the current standings
     * of the racers.
     * 
     * @return the standings of the racers.
     */
    public String[] getRacers() {
        return myRacers.clone();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("$L:");
        sb.append(myTime);
        for (final String s : getRacers()) {
            sb.append(SPLIT);
            sb.append(s);
        }
        return sb.toString();
    }
}
