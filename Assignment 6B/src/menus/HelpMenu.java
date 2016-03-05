/*
 * Lisa Taylor
 * Jonah Howard
 * Henry Lai
 * John Bui
 * 
 * TCSS 342 - Spring 2016
 * Assignment 6B
 */

package menus;

import actions.AboutAction;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * A Help drop-down menu.
 * 
 * @author Lisa Taylor
 * @version 4 March 2016
 */
public class HelpMenu {

    /** JMenu to hold Help menu items. */
    private final JMenu myHelpMenu;
    
    /** MenuItem to display info about PowerPaint. */
    private final JMenuItem about;
    
    /** Constructor to initialize fields. */
    public HelpMenu() {
        
        myHelpMenu = new JMenu("Help");
        
        about = new JMenuItem(new AboutAction());
        
        setupHelpMenu();
    }
    
    /**
     * Method to get myHelpMenu field.
     * 
     * @return the Options menu
     */
    public JMenu getHelpMenu() {
        
        return myHelpMenu;
    }
    
    /**
     * Method to add menu items to HelpMenu and
     * sets a Mnemonic.
     */
    private void setupHelpMenu() {
        
        myHelpMenu.setMnemonic(KeyEvent.VK_H);
        myHelpMenu.add(about);
    }
}
