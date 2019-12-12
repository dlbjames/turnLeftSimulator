package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_PARTICIPANTS;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_LEADER;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.LeaderBoardMessage;
import model.Participant;


/**
 * The leader board panel class creates
 * the panel to display the current
 * standings in the race.
 * @author Darryl James
 * @version December 6 2019
 *
 */
public class LeaderBoardPanel extends JPanel implements PropertyChangeListener {

    /**
     * A generated serialization unique identifier.
     */
    private static final long serialVersionUID = -6522386561076803185L;

    /** The size of the panel. */
    private static final Dimension BUTTON_SIZE = new Dimension(250, 400);
    
    /** The maximum amount of racers. */
    private static final int MAX_RACERS = 10;
    
    /** The buttons for the leader board. */
    private final Map<Integer, JButton> myButtons;
    
    /** The messages for the leader board standings. */
    private final List<Integer> myMessages;
    
    /**
     * Creates the background
     * and sets up the data structures
     * to hold messages and buttons.
     */
    public LeaderBoardPanel() {
        super();
        setBackground(Color.WHITE);
        setPreferredSize(BUTTON_SIZE);
        myMessages = new ArrayList<Integer>();
        myButtons = new HashMap<Integer, JButton>();
    }
    
    /**
     * Adds the buttons to the 
     * leader board panel class.
     */
    private void setUpComponents() {
        removeAll();
        revalidate();
        repaint();
        setLayout(new GridLayout(MAX_RACERS, 1));
        for (int i = 0; i < myButtons.size(); i++) {
            add(myButtons.get(myMessages.get(i)));
        }
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        // TODO Auto-generated method stub
        if (PROPERTY_PARTICIPANTS.equals(theEvent.getPropertyName())) {
            myButtons.clear();
            myMessages.clear();
            @SuppressWarnings("unchecked")
            final List<Participant> people = (List<Participant>) theEvent.getNewValue();
            for (final Participant p : people) {
                final JButton info = new JButton(p.toString());
                info.setBackground(p.getColor().brighter());
                myButtons.put(p.getID(), info);
                myMessages.add(p.getID());
            }
            setUpComponents();
        } else if (PROPERTY_LEADER.equals(theEvent.getPropertyName())) {
            myMessages.clear();
            final LeaderBoardMessage leader = (LeaderBoardMessage) theEvent.getNewValue();
            final String[] stand = leader.toString().split(":");
            removeAll();
            for (int i = 0; i < stand.length - 2; i++) {
                myMessages.add(Integer.parseInt(stand[i + 2]));
                add(myButtons.get(myMessages.get(i)), BorderLayout.CENTER);
            }
            revalidate();
            repaint();
        } 
    }
}
