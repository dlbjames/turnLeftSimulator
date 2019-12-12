package view;

//import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE;
//import static model.PropertyChangeEnabledRaceControls.PROPERTY_LEADER;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TRACK_INFO;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_PARTICIPANTS;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TELEMETRY;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import model.PropertyChangeEnabledRaceControls;

/**
 * The RaceView class provides the window
 * to display all the components for the 
 * race track.
 * 
 * @author Darryl James
 * @version December 6 2019
 *
 */
public class RaceView extends JFrame implements PropertyChangeListener {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7324689057518213813L;

    /** Size of the main panel. */
    private static final Dimension BUTTON_SIZE = new Dimension(500, 400);

    /** The name for the window of the class. */
    private static final String TRACK = "Race Track";
    
    /** The icon for the JFrame and the about section. */
    private static final Image IMAGE_GIF = new ImageIcon("./images/catCar.gif").
                                           getImage().
                                           getScaledInstance(64,
                                                             64,
                                                             Image.SCALE_DEFAULT);
    
    /** The JFrame to display the race track, status bar, and leader board.*/
    private final JFrame myFrame;
    
    /** The panel to hold the race track.*/
    private final JPanel myMainPanel;
    
    /** The track panel that shows the race track.*/
    private final TrackPanel myTrackPanel;
    
    /** The leader board panel that shows the current standings.*/
    private final LeaderBoardPanel myLeaderBoardPanel;
    
    /** The status bar panel that shows the time of the race.*/
    private final StatusBar myStatusBarPanel;
    
    /** Used to create a titled border.*/
    private final TitledBorder myTitle;
    
    /** The race model ... Uh oh.*/
    private final PropertyChangeEnabledRaceControls myRace;
    
    /**
     * The race view constructor sets up all
     * the main components to add to the JFrame.
     * 
     * @param theRace the race model, big whoops.
     */
    public RaceView(final PropertyChangeEnabledRaceControls theRace) {
        super();
        myRace = theRace;
        myTitle = BorderFactory.createTitledBorder(TRACK);
        myFrame = new JFrame(TRACK);
        myFrame.setLayout(new BorderLayout());
        myMainPanel = new JPanel(new BorderLayout());
        myTrackPanel = new TrackPanel();
        myLeaderBoardPanel = new LeaderBoardPanel();
        myStatusBarPanel = new StatusBar();
    }
    
    /**
     * Creates the panels to be embedded into
     * the JFrame. This Includes the layout as well.
     */
    public void showPanels() {
        myFrame.setBackground(Color.BLUE);
        myFrame.setIconImage(IMAGE_GIF);
        myMainPanel.setBackground(Color.WHITE);
        myMainPanel.setBorder(myTitle);
        myMainPanel.setPreferredSize(BUTTON_SIZE);
        
        myMainPanel.add(myTrackPanel, BorderLayout.WEST);
        myFrame.add(myLeaderBoardPanel, BorderLayout.EAST);
        myFrame.add(myMainPanel, BorderLayout.WEST);
        myFrame.add(myStatusBarPanel, BorderLayout.SOUTH);
        
        myFrame.setVisible(true);
        myFrame.setResizable(false);
        myFrame.pack();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        // TODO Auto-generated method stub
        if (model.
            PropertyChangeEnabledRaceControls.
            PROPERTY_RACE.equals(theEvent.getPropertyName())) {
            
            myRace.addPropertyChangeListener(PROPERTY_TELEMETRY, myTrackPanel);
            myRace.addPropertyChangeListener(PROPERTY_PARTICIPANTS, myTrackPanel);
            myRace.addPropertyChangeListener(PROPERTY_TRACK_INFO, myTrackPanel);
            myRace.addPropertyChangeListener(PROPERTY_TIME, myStatusBarPanel);
            myRace.addPropertyChangeListener(PROPERTY_PARTICIPANTS, myLeaderBoardPanel);
            myRace.addPropertyChangeListener(model.
                                             PropertyChangeEnabledRaceControls.
                                             PROPERTY_LEADER, myLeaderBoardPanel);
        }
    }
    
}
