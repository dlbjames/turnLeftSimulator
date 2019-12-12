
package model;

import java.awt.Color;
import java.util.Random;

// import java.util.ArrayList;

/**
 * The participant class which holds a participants racer id, name, and starting point.
 * 
 * @author Darryl James
 * @version Autumn 2019
 */
public class Participant {

    /** Random color multiplier.*/
    //Old Algorithm: 4993
    //Test on Simple Algorithm: 254 248 205
    private static final int GEN = 255;
    
    /** The name of the racer. */
    private final String myRacerName;

    /** The ID of the racer. */
    private final int myID;

    /** The starting point of the racer. */
    private final double myStartPoint;

    /** The color for the racer.*/
    private final Color myColor;

    /**
     * The constructor for the participant class.
     * 
     * @param theID of the participant.
     * @param theName of the participant
     * @param theStartPoint of the participant.
     */
    public Participant(final int theID, final String theName, final double theStartPoint) {
        myID = theID;
        myRacerName = theName;
        myStartPoint = theStartPoint;
        myColor = randomColor();
    }

    /**
     * Returns the color for the racer.
     * @return the color for the racer.
     */
    private Color randomColor() {
        final Random random = new Random();
//        random.setSeed(GEN % myID);
//        final float hue = random.nextFloat();
//        // Saturation between 0.1 and 0.3
//        final float saturation = 0.8f;
//        final float luminance = 1.0f;
//        return Color.getHSBColor(hue, saturation, luminance); 
        final int r = random.nextInt(GEN);
        final int g = random.nextInt(GEN);
        final int b = random.nextInt(GEN);
        return new Color(r, g, b);
    }

    /**
     * Returns the color of the participant.
     * 
     * @return the color of the participant.
     */
    public Color getColor() {
        return myColor;
    }

    /**
     * Returns the name of the participant.
     * 
     * @return the name of the participant.
     */
    public String getName() {
        return myRacerName;
    }

    /**
     * Returns the ID of the participant.
     * 
     * @return the ID of the participant.
     */
    public int getID() {
        return myID;
    }

    /**
     * Returns the start point of the participant.
     * 
     * @return the start point of the participant.
     */
    public double getStartPoint() {
        return myStartPoint;
    }

    @Override
    public String toString() {
        return myID + ": " + myRacerName;
    }

}
