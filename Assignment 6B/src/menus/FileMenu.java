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
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * A File drop-down menu.
 * 
 * @author Lisa Taylor
 * 
 * @version 4 March 2016
 */
public class FileMenu {

    /** JFrame containing spreadsheet application. */
    private final JFrame myFrame;
    
    /** JMenu to hold File menu items. */
    private final JMenu myFileMenu;
    
    /** MenuItem to open a file in the spreadsheet. */
    private final JMenuItem openFile;
    
    /** MenuItem to save spreadsheet to a file. */
    private final JMenuItem saveFile;
    
    /** MenuItem to exit Spreadsheet Application. */
    private final JMenuItem exitApp;
    
    /** 
     * Constructor to initialize fields.
     *
     * @param theFrame the JFrame
     * @param thePanel the CanvasPanel
     */
    public FileMenu(final JFrame theFrame) {
        
        myFrame = theFrame;
        
        myFileMenu = new JMenu("File");
        
        openFile = new JMenuItem("Open...");
        saveFile = new JMenuItem("Save...");
        exitApp = new JMenuItem("Exit");
        
        setupFileMenu();
    }
    
    /**
     * Method to get myMenu field.
     * 
     * @return the File menu
     */
    public JMenu getFileMenu() {
        
        return myFileMenu;
    }
    
    /**
     * Adds ActionListener to myUndoAll menu item.
     */
    private void buildOpenFile() {
        
        openFile.setEnabled(true);
        openFile.setMnemonic(KeyEvent.VK_O);
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        openFile.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                //Code here
            }
        });
    }
    
    /**
     * Adds ActionListener to myUndo menu item.
     */
    private void buildSaveFile() {
        
        saveFile.setEnabled(true);
        saveFile.setMnemonic(KeyEvent.VK_S);
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                //Code here
            }
        });
    }
    
    /**
     * Adds ActionListener to myExit menu item.
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
     * Method to add menu items to FileMenu and
     * sets a Mnemonic.
     */
    private void setupFileMenu() {
        
        buildOpenFile();
        buildSaveFile();
        buildExitApp();
        
        myFileMenu.setMnemonic(KeyEvent.VK_F);
        myFileMenu.add(openFile);
        //myMenu.addSeparator();
        myFileMenu.add(saveFile);
        myFileMenu.addSeparator();
        myFileMenu.add(exitApp);
    }
}
