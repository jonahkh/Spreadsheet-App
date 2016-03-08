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

import model.Cell;
import model.Spreadsheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * An Options drop-down menu.
 */
public class OptionsMenu implements PropertyChangeListener {
    
    /** JMenu to hold Options menu items. */
    private final JMenu myOptionsMenu;
    
    /** The JFrame that the spreadsheet is displayed on. */
    private final JFrame myFrame;
       
    /** The Spreadsheet. */
    private Spreadsheet mySpreadsheet;
    
    /** MenuItem to clear the entire spreadsheet. */
    private final JMenuItem clearAll;
    /** 
     * Initializes a new Options Menu.
     *
     * @param theFrame the current frame
     * @param theSpreadsheet the current spreadsheet
     * @param theTable the current table displaying all cells
     */
    public OptionsMenu(final JFrame theFrame, final Spreadsheet theSpreadsheet) {
        
        myOptionsMenu = new JMenu("Options");
        
        myFrame = theFrame;
        mySpreadsheet = theSpreadsheet;
        clearAll = new JMenuItem("Clear All");
        
        setupOptionsMenu();
    }
    
    /**
     * Get myOptionsMenu field.
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
        
        clearAll.setEnabled(true);
        clearAll.setMnemonic(KeyEvent.VK_C);
        clearAll.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	// Prompts the user for confirmation that they want to clear spreadsheet.
                int input = JOptionPane.showConfirmDialog(myFrame, 
                		"Are you sure you want to clear the spreadsheet?", 
                		"Clear All Cells", JOptionPane.YES_NO_OPTION);
                // Proceeds to clear spreadsheet upon confirmation.
                if (input == JOptionPane.YES_OPTION) {
                    mySpreadsheet.initializeCells();
                    for (int row = 0; row < mySpreadsheet.getRows(); row++) {
                    	for (int col = 1; col <= mySpreadsheet.getColumns(); col++) {
                    		mySpreadsheet.getSpreadsheet()[row][col] = "";
                    	}
                    }
                    myFrame.repaint();
                }
            }
        });
    }
    
    
    
    /** Add menu items to OptionsMenu and sets a Mnemonic. */
    private void setupOptionsMenu() {
        
        buildClearAll();
        myOptionsMenu.add(clearAll);
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("TableState".equals(theEvent.getPropertyName())) {
            clearAll.setEnabled((boolean) theEvent.getNewValue());
        }
    }
}
