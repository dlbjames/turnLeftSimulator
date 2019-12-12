package controller;

import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

/**
 * This class clears the output area.
 * 
 * @author Darryl James
 * @version 19 November 2019
 */
public class ClearThatTrash extends SimpleAction {

    /** The serialization ID. */
    private static final long serialVersionUID = -2639645770133630561L;

    /** The JTextArea that is to be cleared.*/
    private final JTextArea myOutput;
    
    /**
     * Constructor that passes information to the parent class.
     * 
     * @param theText the name of the button.
     * @param theIcon the image for the button.
     * @param theOutput the JTextArea to clear.
     */
    ClearThatTrash(final String theText, final String theIcon, final JTextArea theOutput) {
        super(theText, theIcon);
        // TODO Auto-generated constructor stub
        myOutput = theOutput;
    }

    @Override
    public void actionPerformed(final ActionEvent theE) {
        // TODO Auto-generated method stub
        myOutput.setText("");
    }
}