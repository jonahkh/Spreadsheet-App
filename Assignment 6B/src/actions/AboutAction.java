/*
 * Lisa Taylor
 * Jonah Howard
 * Henry Lai
 * John Bui
 * 
 * TCSS 342 - Spring 2016
 * Assignment 6B
 */

package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

/**
 * Displays program information.
 * 
 * @author Lisa Taylor
 * @version 4 March 2016
 */
public class AboutAction extends AbstractAction {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = -7818553059841113587L;

    /**
     * Constructs an action with the specified name
     * to display information about the program.
     */
    public AboutAction() {
        
        super("About...");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
    }
    
    /**
     * 
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        
        JOptionPane.showMessageDialog(null, "TCSS 342 Spreadsheet, Spring 2016");
    }
    
}
