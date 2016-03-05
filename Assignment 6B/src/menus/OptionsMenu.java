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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * An Options drop-down menu.
 * 
 * @author Lisa Taylor
 * 
 * @version 4 March 2016
 */
public class OptionsMenu implements PropertyChangeListener {
    
    /** JMenu to hold Options menu items. */
    private final JMenu myOptionsMenu;
    
    /** MenuItem to clear the entire spreadsheet. */
    private final JMenuItem clearAll;
    
    /** MenuItem to resize spreadsheet. */
    private final JMenuItem resize;
    
    /** 
     * Constructor to initialize fields. 
     * 
     * @param thePanel the CanvasPanel
     */
    public OptionsMenu() {
        
        myOptionsMenu = new JMenu("Options");
        
        clearAll = new JMenuItem("Clear All");
        resize = new JMenuItem("Resize");
        
        setupOptionsMenu();
    }
    
    /**
     * Method to get myOptionsMenu field.
     * 
     * @return the Options menu
     */
    public JMenu getOptionsMenu() {
        
        return myOptionsMenu;
    }
    
    /**
     * Adds ActionListener to clearAll menu item.
     */
    private void buildClearAll() {
        
        clearAll.setEnabled(false);
        clearAll.setMnemonic(KeyEvent.VK_C);
        clearAll.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                //Code here
            }
        });
    }
    
    /**
     * Adds ActionListener to resize menu item.
     */
    private void buildResize() {
        
        resize.setEnabled(true);
        resize.setMnemonic(KeyEvent.VK_R);
        resize.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                //Code here
            }
        });
    }
    
    /** Method to add menu items to OptionsMenu and sets a Mnemonic. */
    private void setupOptionsMenu() {
        
        buildClearAll();
        buildResize();
        
        myOptionsMenu.setMnemonic(KeyEvent.VK_O);
        myOptionsMenu.add(clearAll);
        myOptionsMenu.addSeparator();
        myOptionsMenu.add(resize);
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if ("TableState".equals(theEvent.getPropertyName())) {
            
            clearAll.setEnabled((boolean) theEvent.getNewValue());
        }
    }
}
