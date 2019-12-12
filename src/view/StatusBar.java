package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The status bar class shows the 
 * panel at the bottom of the race 
 * track that displays the current 
 * race time.
 * @author Darryl James
 * @version December 6 2019
 *
 */
public class StatusBar extends JPanel implements PropertyChangeListener {

    /**
     * A default serialization unique identifier.
     */
    private static final long serialVersionUID = -7638438030039023214L;

    /** The message to go in the bottom right of the panel.*/
    private static final String TIME = "Time: ";
    
    /**The message to go in the bottom left of the panel.*/
    private static final String RACER = " Participant: ";
    
    /** The padding for the status bar.*/
    private static final int PADDING = 25;
    
    /** The Label to show the current time.*/
    private final JLabel myTime;
    
//    /** The label to show information on the racer.*/
//    private final JLabel myRacer;

    /**
     * The status bar constructor creates the 
     * status bar panel at the bottom of
     * the race track.
     */
    public StatusBar() {
        super();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(getWidth(), PADDING));
        setBackground(Color.WHITE.darker());
        myTime = new JLabel(TIME + Utilities.formatTime(0));
        final JLabel racer = new JLabel(RACER);
        myTime.setForeground(Color.BLACK);
        racer.setForeground(Color.BLACK);
        
        add(racer, BorderLayout.WEST);
        add(myTime, BorderLayout.EAST);
    }
    
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        // TODO Auto-generated method stub
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            myTime.setText(TIME + Utilities.formatTime(
                                  (Integer) theEvent.getNewValue()));
        }
        
    }

}
