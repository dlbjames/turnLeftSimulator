package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_REPEAT;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JSlider;
import javax.swing.Timer;
import model.PropertyChangeEnabledRaceControls;
import model.RaceModel;

/**
 * This class clears the output area.
 * 
 * @author Darryl James
 * @version 19 November 2019
 */
class RepeatAction extends SimpleAction implements PropertyChangeListener {

    /** The serialization ID. */
    private static final long serialVersionUID = -2639645770133630561L;

    /** The repeat color button icon location. */
    private static final String REPEAT_COLOR_BUTTON = "/ic_repeat_color.png";

    /**The repeat button icon location. */
    private static final String REPEAT_BUTTON = "/ic_repeat.png";
    
    /** The Icon location for the play pause buttons. */
    private String myIcon;
    
    /** The slider for the race.*/
    private final JSlider mySlider;
    
    /** The model for the race.*/
    private final PropertyChangeEnabledRaceControls myRace;
    
    /** The timer for the race.*/
    private final Timer myTimer;
    
    //private testFlag myCheck;
    
    /**
     * Constructor that passes information to the parent class.
     * 
     * @param theText the name of the button.
     * @param theIcon the image for the button.
     * @param theSlider the slider for the race.
     * @param theRace the race model for the race.
     * @param theTimer the timer for the race.
     */
    RepeatAction(final String theText, final String theIcon,
                 final JSlider theSlider,
                 final PropertyChangeEnabledRaceControls theRace,
                 final Timer theTimer) { 
        super(theText, theIcon);
        // TODO Auto-generated constructor stub
        myIcon = REPEAT_BUTTON;
        mySlider = theSlider;
        myRace = theRace;
        myTimer = theTimer;
        //myCheck = theCheck;
        myRace.addPropertyChangeListener(PROPERTY_REPEAT, this);

    }

    @Override
    public void actionPerformed(final ActionEvent theE) {
        // TODO Auto-generated method stub
        if (myIcon.equals(REPEAT_BUTTON)) {
            putValue(Action.NAME, "Loop Race");
            myIcon = REPEAT_COLOR_BUTTON; 
            if (myIcon.equals(REPEAT_COLOR_BUTTON)
                && mySlider.getValue() >= mySlider.getMaximum()) {
                myRace.moveTo(0);
            }
            ((RaceModel) myRace).setFlag(1);
            super.setIcon(myIcon);
            
        } else {
            putValue(Action.NAME, "Single Race");
            myIcon = REPEAT_BUTTON;
            ((RaceModel) myRace).setFlag(0);
            super.setIcon(myIcon);
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        // TODO Auto-generated method stub
        if (PROPERTY_REPEAT.equals(theEvent.getPropertyName())
            && myIcon.equals(REPEAT_COLOR_BUTTON)
            && mySlider.getValue() >= mySlider.getMaximum()) {
            myTimer.start();
            myRace.moveTo(0);
            mySlider.setEnabled(false);
        }
    }
    
}
