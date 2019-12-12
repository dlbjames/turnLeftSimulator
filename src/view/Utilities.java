
package view;

import java.text.DecimalFormat;
import javax.swing.JPanel;

/**
 * Utility methods for this project.
 * 
 * @author Charles Bryan
 * @version Autumn 2019
 */
public final class Utilities extends JPanel {

    /** The number of milliseconds in a second. */
    public static final int MILLIS_PER_SEC = 1000;

    /** The number of seconds in a minute. */
    public static final int SEC_PER_MIN = 60;

    /** The number of minute in a hour. */
    public static final int MIN_PER_HOUR = 60;
    
    /** The delimiter for messages. */
    public static final String DELIMITER = ":";

    /** A formatter to require at least 1 digit, leading 0. */
    public static final DecimalFormat ONE_DIGIT_FORMAT = new DecimalFormat("0");

    /** A formatter to require at least 2 digits, leading 0s. */
    public static final DecimalFormat TWO_DIGIT_FORMAT = new DecimalFormat("00");

    /** A formatter to require at least 3 digits, leading 0s. */
    public static final DecimalFormat THREE_DIGIT_FORMAT = new DecimalFormat("000");
    
    /**
     * A generated serial version UID.
     */
    private static final long serialVersionUID = 8999160514035321136L;

    /**
     * This formats a positive integer into minutes, seconds, and milliseconds. 00:00:000
     * 
     * @param theTime the time to be formatted
     * @return the formated string.
     */
    public static String formatTime(final long theTime) {
        long time = theTime;
        final long milliseconds = time % MILLIS_PER_SEC;
        time /= MILLIS_PER_SEC;
        final long seconds = time % SEC_PER_MIN;
        time /= SEC_PER_MIN;
        final long min = time % MIN_PER_HOUR;
        time /= MIN_PER_HOUR;
        return TWO_DIGIT_FORMAT.format(min) + DELIMITER
               + TWO_DIGIT_FORMAT.format(seconds) + DELIMITER
               + THREE_DIGIT_FORMAT.format(milliseconds);
    }

}
