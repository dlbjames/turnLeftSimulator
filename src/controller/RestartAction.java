package controller;

import java.awt.event.ActionEvent;
import model.PropertyChangeEnabledRaceControls;

/**
 * This class clears the output area.
 * 
 * @author Darryl James
 * @version 19 November 2019
 */
public class RestartAction extends SimpleAction {

    /** The serialization ID. */
    private static final long serialVersionUID = -2639645770133630561L;
    
    /** The timer for the model.*/
    private final PropertyChangeEnabledRaceControls myRace;
    
    /**
     * Constructor that passes information to the parent class.
     * 
     * @param theText the name of the button.
     * @param theIcon the image for the button.
     * @param theRace the race model to be reset.
     */
    RestartAction(final String theText, final String theIcon
                  , final PropertyChangeEnabledRaceControls theRace) {
        super(theText, theIcon);
        // TODO Auto-generated constructor stub
        myRace = theRace;
    }

    @Override
    public void actionPerformed(final ActionEvent theE) {
        // TODO Auto-generated method stub
        myRace.moveTo(0);
    }
}
