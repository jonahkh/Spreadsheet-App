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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.Spreadsheet;

/**
 * A File drop-down menu. Contains the open, save, and exit features.
 */
public class FileMenu {

    /** JMenu to hold File menu items. */
    private final JMenu myFileMenu;
    
    /** JFrame containing spreadsheet application. */
    private final JFrame myFrame;
    
    /** The Spreadsheet being used by the program. */
    private Spreadsheet mySpreadsheet;
    
    /** MenuItem to clear the entire spreadsheet. */
    private final JMenuItem clearAll;
    
    /** MenuItem to exit Spreadsheet Application. */
    private final JMenuItem exitApp;
    
    /** 
     * Initializes a new FileMenu.
     *
     * @param theFrame the current JFrame
     */
    public FileMenu(final JFrame theFrame, final Spreadsheet theSpreadsheet) {
        
        myFileMenu = new JMenu("File");
        myFrame = theFrame;
        mySpreadsheet = theSpreadsheet;
        
        clearAll = new JMenuItem("Clear All");
        exitApp = new JMenuItem("Exit");
        
        setupFileMenu();
    }
    
    /**
     * Gets this file menu.
     * 
     * @return the File menu
     */
    public JMenu getFileMenu() {
        
        return myFileMenu;
    }
    
   
    
    /**
     * Adds ActionListener to exitApp menu item.
     */
    private void buildExitApp() {
        
        exitApp.setMnemonic(KeyEvent.VK_X);
        exitApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        exitApp.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
    
    /**
     * Adds menu items to FileMenu and sets a Mnemonic.
     */
    private void setupFileMenu() {

        buildClearAll();
        buildExitApp();
        
        myFileMenu.add(clearAll);
        myFileMenu.addSeparator();
        myFileMenu.add(exitApp);
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
}
