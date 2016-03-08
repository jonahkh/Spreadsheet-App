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
import javax.swing.JTable;

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
    
    /** MenuItem to resize spreadsheet. */
    private final JMenuItem resize;
    
    /** MenuItem to add row(s) to spreadsheet. */
    private final JMenuItem addRows;
    
    /** MenuItem to add column(s) to spreadsheet. */
    private final JMenuItem addColumns;
    
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
        resize = new JMenuItem("Resize");
        addRows = new JMenuItem("Add Row(s)");
        addColumns = new JMenuItem("Add Column(s)");
        
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
    
    /**
     * Adds ActionListener to resize menu item.
     */
    private void buildResize() {
        
        resize.setEnabled(true);
        resize.setMnemonic(KeyEvent.VK_S);
        resize.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                // code here
            }
        });
    }
    
    /**
     * Adds ActionListener to addRows menu item.
     */
    private void buildAddRows() {
        
        addRows.setEnabled(true);
        addRows.setMnemonic(KeyEvent.VK_R);
        addRows.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                String input = JOptionPane.showInputDialog("Enter the number of rows to add.");
                
                while(!input.matches("[0-9]+")) {
                    
                    input = JOptionPane.showInputDialog("Please enter integer values only.");
                }
                
                int numRows = mySpreadsheet.getRows();
                int numCols = mySpreadsheet.getColumns();
                Cell[][] prevCells = mySpreadsheet.getCells();
                mySpreadsheet = new Spreadsheet(numRows + Integer.parseInt(input), numCols, myFrame);
                Cell[][] cells = mySpreadsheet.getCells();
                for (int r = 0; r < numRows; r++) {
                    for (int c = 0; c < numCols + 1; c++) {
                        cells[r][c] = prevCells[r][c];
                    }
                }
                myFrame.repaint();
            }
        });
    }
    
    /**
     * Adds ActionListener to addColumns menu item.
     */
    private void buildAddColumns() {
        
        addColumns.setEnabled(true);
        addColumns.setMnemonic(KeyEvent.VK_C);
        addColumns.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                String input = JOptionPane.showInputDialog("Enter the number of rows to add.");
                
                while(!input.matches("[0-9]+")) {
                    
                    input = JOptionPane.showInputDialog("Please enter integer values only.");
                }
            }
        });
    }
    
    /** Add menu items to OptionsMenu and sets a Mnemonic. */
    private void setupOptionsMenu() {
        
        buildClearAll();
        buildResize();
        buildAddRows();
        buildAddColumns();
        
        myOptionsMenu.add(clearAll);
        myOptionsMenu.addSeparator();
        myOptionsMenu.add(resize);
        myOptionsMenu.add(addRows);
        myOptionsMenu.add(addColumns);
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("TableState".equals(theEvent.getPropertyName())) {
            clearAll.setEnabled((boolean) theEvent.getNewValue());
        }
    }
}
