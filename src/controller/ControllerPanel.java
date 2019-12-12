
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_PARTICIPANTS;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_MAX_TIME;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_MESSAGES;
//import static model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD;
//import static model.PropertyChangeEnabledRaceControls.PROPERTY_WAIT;
//import static model.PropertyChangeEnabledRaceControls.PROPERTY_RACE;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;
import model.Participant;
import model.PropertyChangeEnabledRaceControls;
import model.RaceModel;
import view.RaceView;
import view.Utilities;

/**
 * The ControllerPanel class does pretty much
 * everything from making the GUI, to updating 
 * the race model. Should decouple soon.
 * 
 * @author Charles Bryan
 * @author Darryl James
 * @version Autumn 2019
 */
public class ControllerPanel extends JPanel {

    /** The serialization ID. */
    private static final long serialVersionUID = -6759410572845422202L;

    /** The frequency for the timer. */
    private static final int TIMER_FREQUENCY = 30;

    /** The multiplier for the timer. */
    private static final int FOUR_TIMES = 4;

    /** The multiplier for the timer. */
    private static final int BIG_TICK = 60000;

    /** The multiplier for the timer. */
    private static final int SMALL_TICK = 10000;

    /** The icon for the JFrame and the about section. */
    private static final Image IMAGE_GIF = new ImageIcon("./images/catCar.gif").
                                           getImage().
                                           getScaledInstance((TIMER_FREQUENCY + 2) * 2,
                                                             (TIMER_FREQUENCY + 2) * 2,
                                                             Image.SCALE_DEFAULT);
    /** The play button location. */
    private static final String PLAY_BUTTON = "/ic_play.png";

    /** The 1x button location. */
    private static final String ONE_TIME_BUTTON = "/ic_one_times.png";

    /** The clear button location. */
    private static final String CLEAR_BUTTON = "/ic_clear.png";

    /** The repeat button location. */
    private static final String REPEAT_BUTTON = "/ic_repeat.png";

    /** THe Restart button location. */
    private static final String RESTART_BUTTON = "/ic_restart.png";

    /** The message for a one time multiplier. */
    private static final String ONE_TIME_MESSAGE = "Times One";

    /** Used to set various component dimensions. */
    private static final int TEN = 10;

    /** Used to set various component dimensions. */
    private static final int FIVE = 5;

    /** Used to set various component dimensions. */
    private static final int DEUXCINQ = 25;

    /** The file used to load the race. */
    private static File myRaceFile;

    /** A reference to the backing Race Model. */
    private static PropertyChangeEnabledRaceControls myRace;

    /** Display of messages coming from the Race Model. */
    private final JTextArea myOutputArea;

    /** Panel to display CheckBoxs for each race Participant. */
    private final JPanel myParticipantPanel;

    /** A view on the race model that displays the current race time. */
    private final JLabel myTimeLabel;

    /** A controller and view of the Race Model. */
    private final JSlider myTimeSlider;

    /** The list of javax.swing.Actions that make up the ToolBar (Controls) buttons. */
    private final List<Action> myControlActions;

    /** The timer that advances the Race Model. */
    private final Timer myTimer;

    /** Container to hold the different output areas. */
    private final JTabbedPane myTabbedPane;

    /**
     * The myMsg field is used to display race information to the Race info button.
     */
    private String myMsg;

    /** The multiplier for the timer. */
    private int myMultiplier;

    /**
     * Construct a ControllerPanel.
     * 
     * @param theRace the backing race model
     */
    public ControllerPanel(final PropertyChangeEnabledRaceControls theRace) {
        super();
        myOutputArea = new JTextArea(TEN, TEN * FIVE);
        myTimeLabel = new JLabel(Utilities.formatTime(0));
        
        myTimeSlider = new JSlider(0, 0, 0);
        myControlActions = new ArrayList<>();

        myTabbedPane = new JTabbedPane();
        myParticipantPanel = new JPanel();

        // TODO This component require Event Handlers
        myTimer = new Timer(TIMER_FREQUENCY, this::handleTimer);
        setUpFields(theRace);
    }

    /**
     * This private method sets up the necessary components to get the GUI running. Also to
     * avoid 10 statements in the constructor.
     * @param theRace model to be used
     */
    private void setUpFields(final PropertyChangeEnabledRaceControls theRace) {
        myRace = theRace;
        buildActions();
        setUpComponents();
        addListeners();
        myMsg = "";
        myMultiplier = 1;
    }

    /**
     * Displays a simple JFrame.
     */
    private void setUpComponents() {
        setLayout(new BorderLayout());
        // JPanel is a useful container for organizing components inside a JFrame
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(TEN, TEN, TEN, TEN));
        mainPanel.add(buildSliderPanel(), BorderLayout.NORTH);

        myOutputArea.setEditable(false);
        final JScrollPane scrollPane = new JScrollPane(myOutputArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.
                                                HORIZONTAL_SCROLLBAR_NEVER);
        final DefaultCaret caret = (DefaultCaret) myOutputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        final JScrollPane participantScrollPane = new JScrollPane(myParticipantPanel);
        participantScrollPane.setPreferredSize(scrollPane.getSize());

        myTabbedPane.addTab("Data Output Stream", scrollPane);
        myTabbedPane.addTab("Race Participants", participantScrollPane);
        myTabbedPane.setEnabledAt(1, false);

        mainPanel.add(myTabbedPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
        add(buildToolBar(), BorderLayout.SOUTH);
    }

    /**
     * Builds the panel with the time slider and time label.
     * 
     * @return the panel
     */
    private JPanel buildSliderPanel() {
        // TODO These components require Event Handlers
        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(FIVE, FIVE, DEUXCINQ, FIVE));

        myTimeSlider.setBorder(BorderFactory.createEmptyBorder(FIVE, FIVE, FIVE, FIVE));
        myTimeSlider.setEnabled(false);
        
        myTimeSlider.addChangeListener(theEvent -> {
            if (myTimeSlider.getValueIsAdjusting()) {
                myRace.moveTo(myTimeSlider.getValue());
            }
        });

        panel.add(myTimeSlider, BorderLayout.CENTER);

        myTimeLabel.setBorder(BorderFactory.
                              createCompoundBorder(BorderFactory.createEtchedBorder()
                                                   , BorderFactory.createEmptyBorder
                                                   (FIVE, FIVE, FIVE, FIVE)));
        final JPanel padding = new JPanel();
        padding.add(myTimeLabel);
        panel.add(padding, BorderLayout.EAST);
        return panel;
    }

    /**
     * Constructs a JMenuBar for the Frame.
     * 
     * @return the Menu Bar
     */
    private JMenuBar buildMenuBar() {
        final JMenuBar bar = new JMenuBar();
        bar.add(buildFileMenu());
        bar.add(buildControlsMenu(myControlActions));
        bar.add(buildHelpMenu());
        return bar;
    }

    /**
     * Builds the file menu for the menu bar.
     * 
     * @return the File menu
     */
    private JMenu buildFileMenu() {
        // TODO These components require Event Handlers
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem load = new JMenuItem("Load Race...");

        load.addActionListener(theEvent -> {
            final FileLoader fileLoader = new FileLoader();
            fileLoader.execute();
        });

        fileMenu.add(load);
        fileMenu.addSeparator();

        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(theEvent -> System.exit(0));

        fileMenu.add(exitItem);
        return fileMenu;
    }

    /**
     * Build the Controls JMenu.
     * 
     * @param theActions the Actions needed to add/create the items in this menu
     * @return the Controls JMenu
     */
    private JMenu buildControlsMenu(final List<Action> theActions) {
        final JMenu controlsMenu = new JMenu("Controls");

        for (final Action a : theActions) {
            controlsMenu.add(a);
            a.setEnabled(false); // Added this line
        }
        return controlsMenu;
    }

    /**
     * Build the Help JMenu.
     * 
     * @return the Help JMenu
     */
    private JMenu buildHelpMenu() {
        // TODO These components require Event Handlers
        final JMenu helpMenu = new JMenu("Help");

        final JMenuItem infoItem = new JMenuItem("Race Info...");
        helpMenu.add(infoItem);
        infoItem.setEnabled(false); // Added this line

        myRace.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.
                                         PROPERTY_LOAD, theEvent -> {
                if (model.PropertyChangeEnabledRaceControls.
                            PROPERTY_LOAD.equals(theEvent.getPropertyName())) {
                    infoItem.setEnabled(true);
                    myMsg = (String) theEvent.getNewValue();
                }
            });

        infoItem.addActionListener(theEvent -> {
            JOptionPane.showMessageDialog(ControllerPanel.this, myMsg);
        });

        final JMenuItem aboutItem = new JMenuItem("About...");

        aboutItem.addActionListener(theEvent -> {
            JOptionPane.showMessageDialog(ControllerPanel.this,
                                          "Darryl James \n" + "Winter 2019 \n" + "TCSS 305",
                                          "About Info", JOptionPane.INFORMATION_MESSAGE,
                                          new ImageIcon(IMAGE_GIF));
        });

        helpMenu.add(aboutItem);
        return helpMenu;
    }

    /**
     * Build the toolbar from the Actions list.
     * 
     * @return the toolbar with buttons for all of the Actions in the list
     */
    private JToolBar buildToolBar() {
        final JToolBar toolBar = new JToolBar();
        for (final Action a : myControlActions) {
            final JButton b = new JButton(a);
            b.setHideActionText(true);
            toolBar.add(b);
            b.setEnabled(false); // Added this line
        }
        return toolBar;
    }

    /**
     * Event handler for the timer.
     * 
     * @param theEvent the fired event
     */
    private void handleTimer(final ActionEvent theEvent) { // NOPMD
        myRace.advance(TIMER_FREQUENCY * myMultiplier);
    }

    /**
     * Method to add the racers to the 
     * participant panel.
     * 
     * @param theRacers the list of racers.
     */
    private void addRacers(final List<Participant> theRacers) {
        for (final Participant p : theRacers) {
            final JCheckBox box = new JCheckBox(p.getName());
            box.setSelected(true);
            box.addItemListener(theEvent2 -> {
                if (theEvent2.getStateChange() == 1) {
                    myRace.toggleParticipant(p.getID(), true);
                } else {
                    myRace.toggleParticipant(p.getID(), false);
                }
            });
            myParticipantPanel.add(box);
        }
        myParticipantPanel.setLayout(new BoxLayout(myParticipantPanel, BoxLayout.Y_AXIS));
        final JScrollPane scrollPane = new JScrollPane(myParticipantPanel);
        myTabbedPane.setComponentAt(1, scrollPane);
    }

    /**
     * Add actionListeners to the buttons.
     */
    private void addListeners() {   
        myRace.addPropertyChangeListener(PROPERTY_PARTICIPANTS, theEvent -> {
            for (final Action b : myControlActions) {
                b.setEnabled(true);
            }
            myTimeSlider.setEnabled(true);
            myTabbedPane.setEnabledAt(1, true);
            myOutputArea.append("\nThe file is loaded. It's Racing time!\n");
            myParticipantPanel.removeAll();

            // Fix the suppress message later
            @SuppressWarnings("unchecked")
            final List<Participant> people = (List<Participant>) theEvent.getNewValue();
            //myRacers.addAll(people);
            addRacers(people);
            // Fix the suppress message later
        });

        myRace.addPropertyChangeListener(PROPERTY_TIME, theEvent -> {
            myTimeLabel.setText(Utilities.formatTime((Integer) theEvent.getNewValue()));
            myTimeSlider.setValue((Integer) theEvent.getNewValue());
        });

        myRace.addPropertyChangeListener(PROPERTY_MAX_TIME, theEvent -> {
            myTimeSlider.setMaximum((Integer) theEvent.getNewValue());
            myTimeSlider.setMajorTickSpacing(BIG_TICK);
            myTimeSlider.setMinorTickSpacing(SMALL_TICK);
            myTimeSlider.setPaintTicks(true);
        });

        myRace.addPropertyChangeListener(PROPERTY_MESSAGES, theEvent -> {
            myOutputArea.append(theEvent.getNewValue().toString());
            myOutputArea.append("\n");
        });

        myRace.addPropertyChangeListener(model.
                                         PropertyChangeEnabledRaceControls.
                                         PROPERTY_WAIT, theEvent -> {
                myOutputArea.append((String) theEvent.getNewValue());
            });

    }

    /**
     * Instantiate and add the Actions.
     */
    private void buildActions() {
        // TODO These components require Event Handlers
        myControlActions.add(new RestartAction("Restart", RESTART_BUTTON, myRace));
        myControlActions.add(new PlayPauseAction("Play", PLAY_BUTTON, myTimer, myTimeSlider,
                                                 myRace));
        myControlActions.add(new SpeedAction(ONE_TIME_MESSAGE, ONE_TIME_BUTTON));
        myControlActions.add(new RepeatAction("Single Race", REPEAT_BUTTON, myTimeSlider,
                                              myRace, myTimer));
        myControlActions.add(new ClearThatTrash("Clear", CLEAR_BUTTON, myOutputArea));
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        // Create and set up the window.
        final JFrame frame = new JFrame("Race Day");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setIconImage(IMAGE_GIF); // This should set the title bar icon of the
                                       // JFrame, but on Mac it doensn't show up.

        // TODO instantiate your model here.
        final PropertyChangeEnabledRaceControls race = new RaceModel();

        // Create and set up the content pane.
        final ControllerPanel pane = new ControllerPanel(race);

        // Add the JMenuBar to the frame:
        frame.setJMenuBar(pane.buildMenuBar());

        pane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(pane);

        final RaceView panel = new RaceView(myRace);
        race.addPropertyChangeListener(model.
                                       PropertyChangeEnabledRaceControls.
                                       PROPERTY_RACE, panel);
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                panel.showPanels();

            }
        });

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * A worker thread to load the files.
     * 
     * @author Charles Bryan
     * @version Autumn 2019
     */
    private class FileLoader extends SwingWorker<Boolean, Void> {

        @Override
        public Boolean doInBackground() {
            boolean result = true;

            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            ControllerPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            final int choice = fileChooser.showOpenDialog(ControllerPanel.this);

            myOutputArea.append("Loading File...");

            if (choice == JFileChooser.APPROVE_OPTION) {
                myRaceFile = fileChooser.getSelectedFile();
                try {
                    // TODO Load the race file here
                    myRace.loadRace(myRaceFile);
                } catch (final IOException exception) {
                    errorMessage();
                    myOutputArea.append("\n It seems to be an IOException.");
                    result = false;
                } catch (final NumberFormatException e3) {
                    errorMessage();
                    myOutputArea.append("\n It seems to be a NumberFormatException.");
                    result = false;
                }
            }
            return result;
        }

        @Override
        public void done() {
            ControllerPanel.this.setCursor(Cursor.getDefaultCursor());
//            try {
//                final boolean resultOfFileLoad = get();
//                //System.out.println("Finished Loading: " + resultOfFileLoad);
//                /*
//                 * Do something with the result of reading the file.
//                 */
//            } catch (final InterruptedException ex1) {
//                ex1.printStackTrace();
//            } catch (final ExecutionException ex2) {
//                ex2.printStackTrace();
//            }
        }

        /**
         * Displays an error message if the race was not formatted or loaded correctly.
         */
        private void errorMessage() {
            myOutputArea.append("\nJust kidding! " + "There was an error loading the file.");
            JOptionPane.showMessageDialog(ControllerPanel.this, "Error loading file.",
                                          "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This class clears the output area.
     * 
     * @author Darryl James
     * @version 19 November 2019
     */
    class SpeedAction extends SimpleAction {

        /** The serialization ID. */
        private static final long serialVersionUID = -2639645770133630561L;

        /** The 4x icon button location. */
        private static final String FOUR_TIME_BUTTON = "/ic_four_times.png";

        /** The Icon location for the play pause buttons. */
        private String myIcon;

        /**
         * Constructor that passes information to the parent class.
         * 
         * @param theText the name of the button.
         * @param theIcon the image for the button.
         */
        SpeedAction(final String theText, final String theIcon) {
            super(theText, theIcon);
            // TODO Auto-generated constructor stub
            myIcon = ONE_TIME_BUTTON;
        }

        @Override
        public void actionPerformed(final ActionEvent theE) {
            // TODO Auto-generated method stub
            if (myMultiplier == 1) {
                putValue(Action.NAME, "Times Four");
                myIcon = FOUR_TIME_BUTTON;
                myMultiplier = FOUR_TIMES;
                super.setIcon(myIcon);
            } else {
                putValue(Action.NAME, ONE_TIME_MESSAGE);
                myIcon = ONE_TIME_BUTTON;
                myMultiplier = 1;
                super.setIcon(myIcon);
            }
        }
    }
}
