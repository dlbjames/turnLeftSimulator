package application;

import controller.ControllerPanel;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * Driver for the Astonishing Race.
 * 
 * @author Charles Bryan
 * @version Autumn 2019
 */
public final class Main {

    /**
     * Does not allow public construction.
     */
    private Main() {
    }
    
    /**
     * Set the look and feel for the GUI program.
     */
    private static void setLookAndFeel() {

        try {

            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            /* Turn off metal's use of bold fonts */
            UIManager.put("swing.boldMetal", Boolean.FALSE);

        } catch (final UnsupportedLookAndFeelException e) {
            System.out.println("UnsupportedLookAndFeelException");
        } catch (final ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
        } catch (final InstantiationException e) {
            System.out.println("InstantiationException");
        } catch (final IllegalAccessException e) {
            System.out.println("IllegalAccessException");
        }

    }

    /**
     * The start point for the Astonishing Race application.
     * 
     * @param theArgs command line arguments - ignored in this program
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setLookAndFeel();
                ControllerPanel.createAndShowGUI();
            }
        });
    }
}
