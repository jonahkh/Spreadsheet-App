/* Bui, John
 * Howard, Jonah
 * Lai, Henry
 * Taylor, Lisa
 * TCSS 342 - Data Structures
 * Professor Donald Chinn
 * Homework 6B
 * March 8, 2016
 */

package view;

import java.awt.EventQueue;

/**
 * Runs Spreadsheet app by instantiating and starting the SpreadsheetGUI.
 */
public final class GUIMain {
    
    /**
     * Prevents instantiation of this class.
     */
    private GUIMain() {
        throw new IllegalStateException();
    }

    /**
     * Invokes the SpreadsheetGUI. Command line arguments
     * are ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().run();
            }
        });
    }

}
