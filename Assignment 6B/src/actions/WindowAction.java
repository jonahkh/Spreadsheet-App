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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;

import model.Spreadsheet;

/**
 * Action for a drawing tool.
 * 
 * @author Lisa Taylor
 * @author Jonah Howard
 * 
 * @version 4 March 2016
 */ 
public class WindowAction extends AbstractAction {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 6430385666227472422L;
    
    /** The JFrame that the spreadsheet is displayed on. */
    private final JFrame myFrame;
    
    /** The Spreadsheet. */
    private final Spreadsheet mySpreadsheet;

    /**
     * Constructs an action for a particular spreadsheet view.
     * 
     * @param actionName the view name
     */
    public WindowAction(final String actionName, final JFrame theFrame, final Spreadsheet theSpreadsheet) {
        
        super(actionName);        

        myFrame = theFrame;
        mySpreadsheet = theSpreadsheet;
        putValue(Action.SELECTED_KEY, true);
    }        
    
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        //System.out.println(theEvent.getActionCommand());
        
        if ("formulas".equals(theEvent.getActionCommand())) {
            
            // Update the spreadsheet
            //mySpreadsheet.setFormulaMode(true);
            
            // Fill each active cell with its corresponding formula
            for (int i = 0; i < mySpreadsheet.getRows(); i++) {
                for (int j = 1; j < mySpreadsheet.getColumns() + 1; j++) {
                    mySpreadsheet.getSpreadsheet()[i][j] = 
                            mySpreadsheet.getCells()[i][j].getFormula(); 
                }
            }
            myFrame.repaint();
            
        } else {
            
            // Update the spreadsheet
            //mySpreadsheet.setFormulaMode(false);
            
            // Fill each active cell with its corresponding value
            for (int i = 0; i < mySpreadsheet.getRows(); i++) {
                for (int j = 1; j < mySpreadsheet.getColumns() + 1; j++) {
                    if (mySpreadsheet.getCells()[i][j].hasInput()) {
                        mySpreadsheet.getSpreadsheet()[i][j] = 
                                mySpreadsheet.getCells()[i][j].getValue(); 
                    } else {
                        mySpreadsheet.getSpreadsheet()[i][j] = "";
                    }
                }
            }
            myFrame.repaint();
        } 
    }  
}
