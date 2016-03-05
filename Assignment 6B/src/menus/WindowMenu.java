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

import actions.WindowAction;
import model.Spreadsheet;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 * A View drop-down menu.
 * 
 * @author Lisa Taylor
 * 
 * @version 4 March 2016
 */
public class WindowMenu {

    /** The JFrame that the spreadsheet is displayed on. */
    private final JFrame myFrame;
    
    /** JMenu to hold Window menu items. */
    private final JMenu myWindowMenu;
    
    /** The Spreadsheet. */
    private final Spreadsheet mySpreadsheet;
    
    /** A list of window actions. */
    private List<WindowAction> myWindowActions;
    
    /**
     * Constructs a Window menu.
     * 
     * @param thePanel the CanvasPanel
     */
    public WindowMenu(final JFrame theFrame, final Spreadsheet theSpreadsheet) {
        
        myFrame = theFrame;
        mySpreadsheet = theSpreadsheet;
        myWindowMenu = new JMenu("Window");
        
        setupWindowActions();
        
        setupWindowMenu();
    }
    
    /**
     * Method to add menu items to WindowMenu and sets a Mnemonic.
     * Puts window view actions into a button group.
     */
    private void setupWindowMenu() {
        
        myWindowMenu.setMnemonic(KeyEvent.VK_W);
        
        final ButtonGroup btngrp = new ButtonGroup();
        
        for (final WindowAction toolAction : myWindowActions) {
            
            final JRadioButtonMenuItem btn = new JRadioButtonMenuItem(toolAction);
            btngrp.add(btn);
            myWindowMenu.add(btn);
        }
    }
    
    /**
     * Method to get WindowMenu field.
     * 
     * @return the Options menu
     */
    public JMenu getWindowMenu() {
        
        return myWindowMenu;
    }
    
    /**
     * Method to get myWindowActions field.
     * 
     * @return the Tool actions
     */
    public List<WindowAction> getWindowActions() {
        
        return myWindowActions;
    }
    
    /**
     * Method to add menu items to WindowMenu and
     * sets a Mnemonic and short description.
     * 
     * @param thePanel the CanvasPanel
     */
    private void setupWindowActions() {
        
        final WindowAction formulaAction = new WindowAction("formulas", myFrame, mySpreadsheet);
        final WindowAction valueAction = new WindowAction("values", myFrame, mySpreadsheet);
        
        formulaAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, KeyEvent.VK_F);
        formulaAction.putValue(Action.SHORT_DESCRIPTION, "View Formulas");
        
        valueAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY,KeyEvent.VK_V);
        valueAction.putValue(Action.SHORT_DESCRIPTION, "View (Integer) Values");
        
        myWindowActions = new ArrayList<WindowAction>();
        
        myWindowActions.add(formulaAction);
        myWindowActions.add(valueAction);;
    }
}
