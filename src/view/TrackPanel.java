
package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_TELEMETRY;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_PARTICIPANTS;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TRACK_INFO;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import model.Participant;
import model.TelemetryMessage;
import track.VisibleRaceTrack;

/**
 * The TrackPanel class is the view for the programming, giving a visual representation of the
 * race.
 * 
 * @author Darryl James
 * @version 2 December 2019
 *
 */
public class TrackPanel extends JPanel implements PropertyChangeListener {

    /**
     * A generated serial version UID for object serialization.
     */
    private static final long serialVersionUID = 2914367566261002112L;

    /** The dimensions of the track window. */
    private static final Dimension TRACK_WINDOW = new Dimension(500, 400);

    /** The off set of the track. */
    private static final int OFF_SET = 40;

    /** The width of the track. */
    private static final int STROKE_WIDTH = 25;

    /** The size of the ovals. */
    private static final int OVAL_SIZE = 20;

    /** The track. */
    private VisibleRaceTrack myTrack;

    /** The width of the track. */
    private int myWidth;

    /** The height of the track. */
    private int myHeight;

    /** The distance of the track. */
    private double myDistance;
    
    /** The circles for the racers. */
    private final Map<Integer, Ellipse2D> myEllipses;
    
    /** The participants for the circles. */
    private final Map<Integer, Participant> myParticipants;
    
    /**
     * The default TrackPanel() constructor.
     */
    public TrackPanel() {
        super();
        setPreferredSize(TRACK_WINDOW);
        setVisible(false);
        myWidth = 2;
        myHeight = 1;
        myEllipses = new HashMap<Integer, Ellipse2D>();
        myParticipants = new HashMap<Integer, Participant>();
        setUpTrack();
    }

    /**
     * Sets up the track.
     */
    private void setUpTrack() {
        myEllipses.clear();
        final int width = myWidth * 100 - (OFF_SET * 2);
        final int height = myHeight * 100 - (OFF_SET * 2);
        final int x = OFF_SET;
        final int y = (int) TRACK_WINDOW.getHeight() / 2 - (height + OVAL_SIZE) / 2;
        myTrack = new VisibleRaceTrack(x, y, width, height, (int) myDistance);
        
        for (final Integer id : myParticipants.keySet()) {
            final Point2D point = myTrack.getPointAtDistance(
                                          myParticipants.get(id).getStartPoint());
            final Ellipse2D circle = new Ellipse2D.Double(point.getX() - OVAL_SIZE / 2,
                                                          point.getY() - OVAL_SIZE / 2,
                                                          OVAL_SIZE,
                                                          OVAL_SIZE);
            myEllipses.put(id, circle);
        }
    }

    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        if (myTrack != null) {
            g2d.setPaint(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(STROKE_WIDTH));
            g2d.draw(myTrack);
        }

        for (final Integer e : myParticipants.keySet()) {
            g2d.setPaint(myParticipants.get(e).getColor().brighter());
            g2d.setStroke(new BasicStroke(1));
            g2d.fill(myEllipses.get(e));
        }
        
        revalidate();

    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        // TODO Auto-generated method stub
        if (PROPERTY_TELEMETRY.equals(theEvent.getPropertyName())) {
            final int id = ((TelemetryMessage) theEvent.getNewValue()).getRacerId();
            final double dist = ((TelemetryMessage) theEvent.getNewValue()).getDistance();
            final Point2D current =
                            myTrack.getPointAtDistance(dist % myTrack.getTrackLength());
            myEllipses.get(id).setFrame(current.getX() - OVAL_SIZE / 2,
                                       current.getY() - OVAL_SIZE / 2,
                                       OVAL_SIZE,
                                       OVAL_SIZE);
            repaint();
        } else if (PROPERTY_TRACK_INFO.equals(theEvent.getPropertyName())) {
            final String dim = (String) theEvent.getNewValue();
            final String[] dims = dim.split(" ");
            myWidth = Integer.parseInt(dims[0]);
            myHeight = Integer.parseInt(dims[1]);
            myDistance = Double.parseDouble(dims[2]);
            myParticipants.clear();
            setUpTrack();
            setVisible(true);
        } else if (PROPERTY_PARTICIPANTS.equals(theEvent.getPropertyName())) {
            //myEllipses.clear();
            for (final Participant p : (List<Participant>) theEvent.getNewValue()) {
                myParticipants.put(p.getID(), p);
            }
            setUpTrack();
            repaint();
        }
    }

}
