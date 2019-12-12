package controller;

import application.Main;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;


/**
 * This is a simple implementation of an Action. You will most likely not use this
 * implementation in your final solution. Either create your own Actions or alter this to suit
 * the requirements for this assignment.
 * 
 * @author Charles Bryan
 * @version Autumn 2019
 */
public class SimpleAction extends AbstractAction {

    /** The serialization ID. */
    private static final long serialVersionUID = -3160383376683650991L;

    /**
     * Constructs a SimpleAction.
     * 
     * @param theText the text to display on this Action
     * @param theIcon the icon to display on this Action
     */
    SimpleAction(final String theText, final String theIcon) {
        super(theText);

        // small icons are usually assigned to the menu
        ImageIcon icon = (ImageIcon) new ImageIcon(getRes(theIcon));
        final Image smallImage =
                        icon.getImage().getScaledInstance(16, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon smallIcon = new ImageIcon(smallImage);
        putValue(Action.SMALL_ICON, smallIcon);

        // Here is how to assign a larger icon to the tool bar.
        icon = (ImageIcon) new ImageIcon(getRes(theIcon));
        final Image largeImage =
                        icon.getImage().getScaledInstance(24, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon largeIcon = new ImageIcon(largeImage);
        putValue(Action.LARGE_ICON_KEY, largeIcon);
    }

    /**
     * Wrapper method to get a system resource.
     * 
     * @param theResource the name of the resource to retrieve
     * @return the resource
     */
    private URL getRes(final String theResource) {
        return Main.class.getResource(theResource);
    }

    /**
     * Helper to set the Icon to both the Large and Small Icon values.
     * 
     * @param theIcon the icon to set for this Action
     */
    protected void setIcon(final String theIcon) {
        // small icons are usually assigned to the menu
        ImageIcon icon = (ImageIcon) new ImageIcon(getRes(theIcon));
        final Image smallImage =
                        icon.getImage().getScaledInstance(16, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon smallIcon = new ImageIcon(smallImage);
        putValue(Action.SMALL_ICON, smallIcon);

        // Here is how to assign a larger icon to the tool bar.
        icon = (ImageIcon) new ImageIcon(getRes(theIcon));
        final Image largeImage =
                        icon.getImage().getScaledInstance(24, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon largeIcon = new ImageIcon(largeImage);
        putValue(Action.LARGE_ICON_KEY, largeIcon);
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        // TODO If you use this Action class, your behaviors go here.

    }
}
