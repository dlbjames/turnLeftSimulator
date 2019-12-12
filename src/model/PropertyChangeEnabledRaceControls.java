
package model;

import java.beans.PropertyChangeListener;

/**
 * Defines behaviors allowing PropertyChangeListeners to be added or removed from a 
 * RaceControls object. Implementing classes should inform PropertyChangeListeners
 * when methods defined in RaceControls mutate the state of the Race. 
 * 
 * Defines a set of Properties that may be listened too. Implementing class may further define
 * more Properties. 
 * 
 * @author Charles Bryan
 * @version Fall 2018
 *
 */
public interface PropertyChangeEnabledRaceControls extends RaceControls {
    /*
     * Add your own constant Property values here. 
     */
    
    /**
     * A property name for an example. Use this as a template for your own Property values. 
     */
    String PROPERTY_EXAMPLE = " THIS IS AN EXAMPLE";
    
    /** A property name for the time.*/
    String PROPERTY_TIME = "THE TIME STEP";
    
    /** A property name for the File load.*/
    String PROPERTY_LOAD = "The File Load";
    
    /** A property name for the out put area.*/
    String PROPERTY_OUTPUT = "Write to the output area";
    
    /** A property name for the participants.*/
    String PROPERTY_PARTICIPANTS = "The Participants";
    
    /** A property name for the multiplier.*/
    String PROPERTY_MULTI = "The multipliier";
    
    /** A property name for the max time.*/
    String PROPERTY_MAX_TIME = "The Max time for the race";
    
    /** A property name for the Slider time.*/
    String PROPERTY_SLIDER = "The slider for the race";
    
    /** A property name for the race messages.*/
    String PROPERTY_MESSAGES = "The race messages for the race.";
    
    /** A property used for the repeat button.*/
    String PROPERTY_REPEAT = "The Repeat button for the race.";
    
    /** A property used for loading a file.*/
    String PROPERTY_WAIT = "The messages to inform the user to wait.";
    
    /** A property for the race model.*/
    String PROPERTY_RACE = "The race model";
    
    /** A property for the leader board messages.*/
    String PROPERTY_LEADER = "The leaderboard message";
    
    /** A property for the track dimensions.*/
    String PROPERTY_TRACK_INFO = "The dimensions of the track";
    
    /** A property for the track dimensions.*/
    String PROPERTY_TELEMETRY = "The telemetry messages";
    
    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for 
     * all properties. The same listener object may be added more than once, and will be 
     * called as many times as it is added. If listener is null, no exception is thrown and 
     * no action is taken.
     * 
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener theListener);
    
    
    /**
     * Add a PropertyChangeListener for a specific property. The listener will be invoked only 
     * when a call on firePropertyChange names that specific property. The same listener object
     * may be added more than once. For each property, the listener will be invoked the number 
     * of times it was added for that property. If propertyName or listener is null, no 
     * exception is thrown and no action is taken.
     * 
     * @param thePropertyName The name of the property to listen on.
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a 
     * PropertyChangeListener that was registered for all properties. If listener was added 
     * more than once to the same event source, it will be notified one less time after being 
     * removed. If listener is null, or was never added, no exception is thrown and no action 
     * is taken.
     * 
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener theListener);
    
    /**
     * Remove a PropertyChangeListener for a specific property. If listener was added more than
     * once to the same event source for the specified property, it will be notified one less 
     * time after being removed. If propertyName is null, no exception is thrown and no action 
     * is taken. If listener is null, or was never added for the specified property, no 
     * exception is thrown and no action is taken.
     * 
     * @param thePropertyName The name of the property that was listened on.
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(String thePropertyName, 
                                      PropertyChangeListener theListener);
}
