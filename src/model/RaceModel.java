
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.Utilities;

/**
 * The backing model for the Astonishing Race program.
 * 
 * @author Darryl James - 1821226
 * @version 12 November 2019
 */
public class RaceModel implements PropertyChangeEnabledRaceControls {

    /* Have these inputs read already. */
    /** Used for splitting the lines in a valid race file. */
    public static final String SPLITTER = ":";

    /** A string to be displayed when time is less than 0. */
    public static final String TIME_ERROR = "Time may not be less than 0.";

    /** Used for spacing messages. */
    public static final String SPACE = " ";

    /** The name of the Race. */
    private String myRaceName;

    /** The name of the track. */
    private String myTrackName;

    /** The width of the track. */
    private int myTrackWidth;

    /** The height of the track. */
    private int myTrackHeight;

    /** The amount of racers. */
    private int myRacerAmount;

    /** The total distance of the track. */
    private double myTrackDistance;
    /* Have these inputs read already. */

    /** The total time for the race. */
    private int myRaceTime;

    /** The current time for the race. */
    private int myRaceCurrentTime;

    /** The race header from the race file. */
    private final List<String> myRaceHeader;

    /** The participants in the race. */
    private final List<Participant> myParticipants;

    /** The property change support. */
    private final PropertyChangeSupport myPcs;

    /** The list that stores all the messages for the race. */
    private final List<List<Message>> myRaceMessages;

    /** A map of all the participants and their toggled states. */
    private final Map<Integer, Boolean> myToggledRacers;

    /** The state of looping. 1 if we should loop, 0 otherwise. */
    private int myFlag;

    /**
     * Constructs the model for the racers to go kachow!
     */
    public RaceModel() {
        myPcs = new PropertyChangeSupport(this);
        myRaceName = "";
        myRaceHeader = new ArrayList<String>();
        myParticipants = new ArrayList<Participant>();
        myRaceMessages = new ArrayList<List<Message>>();
        myToggledRacers = new HashMap<Integer, Boolean>();
        myRaceCurrentTime = 0;
        myFlag = 0;
    }

    @Override
    public void loadRace(final File theRaceFile) throws IOException {
        // TODO Auto-generated method stub
        myRaceHeader.clear();
        myRaceMessages.clear();
        myParticipants.clear();
        myRaceCurrentTime = 0;

        final BufferedReader br = new BufferedReader(new FileReader(theRaceFile));
        String[] line;
        String anotherLine;
        final String[] header = {"#RACE",
                                 "#TRACK",
                                 "#WIDTH",
                                 "#HEIGHT",
                                 "#DISTANCE",
                                 "#TIME",
                                 "#PARTICIPANTS" };
        for (int i = 0; i < header.length; i++) {
            anotherLine = br.readLine();
            if (anotherLine != null) {
                line = anotherLine.split(SPLITTER);
                if (line[0].equals(header[i])) {
                    myRaceHeader.add(line[1]);
                } else {
                    br.close();
                }
            }
        }
        
        setRaceInfo();

        for (int i = 0; i < myRacerAmount; i++) {
            // && (anotherLine = br.readLine()) != null
            anotherLine = br.readLine();
            if (anotherLine != null) {
                racerInfo(anotherLine);
            }
        }

        myPcs.firePropertyChange(PROPERTY_WAIT, null, "\nRacers Lining up...");

        while ((anotherLine = br.readLine()) != null) {
            messageInfo(anotherLine);
        }

        myPcs.firePropertyChange(PROPERTY_WAIT, null, "\nMessages Loaded...");

        br.close();

        final String msg = myRaceName + "\nTrack Type: " + myTrackName + "\nTotal Time: "
                           + Utilities.formatTime(myRaceTime - 1) + "\nLap Distance: "
                           + (int) myTrackDistance + "\n";
        final String dim = myTrackWidth + SPACE + myTrackHeight + SPACE + myTrackDistance;

        myPcs.firePropertyChange(PROPERTY_RACE, null, this);
        myPcs.firePropertyChange(PROPERTY_LOAD, null, msg);
        myPcs.firePropertyChange(PROPERTY_MAX_TIME, null, myRaceTime - 1);
        myPcs.firePropertyChange(PROPERTY_TRACK_INFO, null, dim);
        myPcs.firePropertyChange(PROPERTY_PARTICIPANTS, null, myParticipants);

    }

    /**
     * The flag is used to check the if the race should be looped or not.
     * 
     * @param theFlag 1 if the race should be looped, 0 otherwise.
     */
    public void setFlag(final int theFlag) {
        myFlag = theFlag;
    }

    /**
     * Return the state of looping.
     * 
     * @return the loop value (0 or 1).
     */
    public int getFlag() {
        return myFlag;
    }

    @Override
    public void advance() {
        // TODO Auto-generated method stub
        advance(1);
    }

    @Override
    public void advance(final int theMillisecond) {
        // TODO Auto-generated method stub
        if (myRaceCurrentTime < myRaceTime) {
            moveTo(myRaceCurrentTime + theMillisecond);
        }
    }

    @Override
    public void moveTo(final int theMillisecond) {
        // TODO Auto-generated method stub
        if (theMillisecond < 0) {
            throw new IllegalArgumentException(TIME_ERROR);
        }
        changeTime(theMillisecond);
    }

    @Override
    public void toggleParticipant(final int theParticipantID, final boolean theToggle) {
        // TODO Auto-generated method stub
        if (myToggledRacers.containsKey(theParticipantID)) {
            myToggledRacers.put(theParticipantID, theToggle);
        }
    }

    /**
     * Changes the time of the race.
     * 
     * @param theMillisecond the time to change the race to.
     */
    private void changeTime(final int theMillisecond) {
        int dir = 1;
        if (theMillisecond < myRaceCurrentTime) {
            dir = -1;
        }

        final int old = myRaceCurrentTime;
        myRaceCurrentTime = theMillisecond;

        for (int i = old * dir; i <= myRaceCurrentTime * dir && old <= myRaceTime; i++) {
            final int index = i * dir;
            if (index < myRaceMessages.size()) {
                for (final Message m : myRaceMessages.get(index)) {
                    if (m instanceof TelemetryMessage) {
                        if (myToggledRacers.get(((TelemetryMessage) m).getRacerId())) {
                            myPcs.firePropertyChange(PROPERTY_MESSAGES, null, m);
                            myPcs.firePropertyChange(PROPERTY_TELEMETRY, null, m);
                        }
                    } else if (m instanceof LeaderBoardMessage) {
                        myPcs.firePropertyChange(PROPERTY_LEADER, null, m);
                        myPcs.firePropertyChange(PROPERTY_MESSAGES, null, m);
                    } else {
                        myPcs.firePropertyChange(PROPERTY_MESSAGES, null, m);
                    }
                }
            }
        }

        myPcs.firePropertyChange(PROPERTY_TIME, old, myRaceCurrentTime);
        myPcs.firePropertyChange(PROPERTY_REPEAT, 1, myRaceTime);
    }

    /**
     * This method sets the information about the race into their respective fields.
     */
    private void setRaceInfo() {
        myRaceName = myRaceHeader.get(0);
        myTrackName = myRaceHeader.get(1);
        myTrackWidth = Integer.parseInt(myRaceHeader.get(2));
        myTrackHeight = Integer.parseInt(myRaceHeader.get(2 + 1));
        myTrackDistance = Integer.parseInt(myRaceHeader.get(2 + 2));
        myRaceTime = Integer.parseInt(myRaceHeader.get(2 + 2 + 1));
        myRacerAmount = Integer.parseInt(myRaceHeader.get(2 + 2 + 2));
        for (int i = 0; i < myRaceTime; i++) {
            myRaceMessages.add(i, new ArrayList<Message>());
        }
        myPcs.firePropertyChange(PROPERTY_WAIT, null, "\nRace Information set...");
    }

    /**
     * The messageInfo private helper method Helps parse the data for messages.
     * 
     * @param theLine - the message on a line.
     * @throws IOException - throw if input is wrong.
     */
    private void messageInfo(final String theLine) {
        final String[] line = theLine.split(SPLITTER);
        if (line[0].equals("$L")) {
            final String[] splitting = Arrays.copyOfRange(line, 2, line.length);
            final LeaderBoardMessage leader =
                            new LeaderBoardMessage(Integer.parseInt(line[1]), splitting);
            myRaceMessages.get(Integer.parseInt(line[1])).add(leader);
        } else if (line[0].equals("$T")) {
            final String[] splitting = Arrays.copyOfRange(line, 2, line.length);
            final TelemetryMessage telemetry =
                            new TelemetryMessage(Integer.parseInt(line[1]), splitting);
            myRaceMessages.get(Integer.parseInt(line[1])).add(telemetry);
        } else if (line[0].equals("$C")) {
            final String[] splitting = Arrays.copyOfRange(line, 2, line.length);
            final LineCrossingMessage crossing =
                            new LineCrossingMessage(Integer.parseInt(line[1]), splitting);
            myRaceMessages.get(Integer.parseInt(line[1])).add(crossing);
        } 
    }

    /**
     * The racerInfo is a private helper method that adds the new participant to a List of
     * other participants.
     * 
     * @param theLine theLine with participant data.
     */
    private void racerInfo(final String theLine) {
        final String[] line = theLine.split(SPLITTER);
        // id name distance
        final int id = Integer.parseInt(line[0].substring(1));
        final String name = line[1];
        final double dist = Double.parseDouble(line[2]);

        myParticipants.add(new Participant(id, name, dist));
        myToggledRacers.put(id, true);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);
    }
}

// https://www.codejava.net/java-se/swing/
// show-simple-open-file-dialog-using-jfilechooser
