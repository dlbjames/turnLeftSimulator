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
class PlayPauseAction extends SimpleAction implements PropertyChangeListener {

    /** The serialization ID. */
    private static final long serialVersionUID = -2639645770133630561L;

    /** The play button icon location.*/
    private static final String PLAY_BUTTON = "/ic_play.png";

    /** The pause button icon location.*/
    private static final String PAUSE_BUTTON = "/ic_pause.png";
    
    /** The play button text.*/
    private static final String PLAY = "Play";
    
    /** The pause button text.*/
    private static final String PAUSE = "Pause";
    
    /** The Icon location for the play pause buttons. */
    private String myIcon;
    
    /** The timer for the model.*/
    private final Timer myTimer;
    
    /** The slider for the model.*/
    private final JSlider myTimeSlider;
    
    /** The race model.*/
    private final PropertyChangeEnabledRaceControls myRace;
    
    //private final testFlag myCheck;

    /**
     * Constructor that passes information to the parent class.
     * 
     * @param theText the name of the button.
     * @param theIcon the image for the button.
     * @param theTimer the timer for the model.
     * @param theSlider the slider for the model.
     * @param theRace model for the race.
     */
    PlayPauseAction(final String theText, final String theIcon
                    , final Timer theTimer, final JSlider theSlider
                    , final PropertyChangeEnabledRaceControls theRace) {
        super(theText, theIcon);
        // TODO Auto-generated constructor stub
        myIcon = PLAY_BUTTON;
        myTimer = theTimer;
        myTimeSlider = theSlider;
        myRace = theRace;
        myRace.addPropertyChangeListener(PROPERTY_REPEAT, this);
    }

    @Override
    public void actionPerformed(final ActionEvent theE) {
        // TODO Auto-generated method stub
        if (myTimer.isRunning()) {
            myTimer.stop();
            myTimeSlider.setEnabled(true);
            putValue(Action.NAME, PLAY);
            myIcon = PLAY_BUTTON;
            super.setIcon(myIcon);
        } else {
            myTimer.start();
            myTimeSlider.setEnabled(false);
            putValue(Action.NAME, PAUSE);
            myIcon = PAUSE_BUTTON;
            super.setIcon(myIcon); 
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        // TODO Auto-generated method stub
        if (PROPERTY_REPEAT.equals(theEvent.getPropertyName())
            && myTimeSlider.getValue() >= myTimeSlider.getMaximum()) {
            if (((RaceModel) myRace).getFlag() == 0) {
                myTimer.stop();
                myTimeSlider.setEnabled(true);
                myIcon = PLAY_BUTTON;
                putValue(Action.NAME, PLAY);
                super.setIcon(myIcon);
            } else {
                myIcon = PAUSE_BUTTON;
                putValue(Action.NAME, PAUSE);
                super.setIcon(myIcon);
            }
        }
    }
}
