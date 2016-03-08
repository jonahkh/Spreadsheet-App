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

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * A Help drop-down menu.
 */
public class HelpMenu {

    /** JMenu to hold Help menu items. */
    private final JMenu myHelpMenu;
    
    /** MenuItem to display help contents. */
    private final JMenuItem helpContents;
    
    /** MenuItem to display info about PowerPaint. */
    private final JMenuItem about;
    
    /** Constructor to initialize fields. */
    public HelpMenu() {
        
        myHelpMenu = new JMenu("Help");
        
        helpContents = new JMenuItem("Help Contents");
        about = new JMenuItem("About");
        
        setupHelpMenu();
    }
    
    /**
     * Returns the help menu. 
     * 
     * @return the Options menu
     */
    public JMenu getHelpMenu() {
        
        return myHelpMenu;
    }
    
    /**
     * Adds ActionListener to helpContents menu item.
     */
    private void buildHelpContents() {
        
        helpContents.setEnabled(true);
        helpContents.setMnemonic(KeyEvent.VK_H);
        helpContents.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
            	JOptionPane.showMessageDialog(null, buildstring().toString(), "Help", JOptionPane.INFORMATION_MESSAGE);
            	
                
            }
        });
    }
    /**
     * String builder to build message in Help content memu.
     * @return StringBuilder.
     */
    private StringBuilder buildstring(){
    	String content;
    	content = "To do basics calculation follow this format:\n";
    	StringBuilder sb = new StringBuilder(content);
    	sb.append("Negative number have to be enclosed by parenthesis\n");
    	sb.append("Addition : A + B, (-A) + B or A + (-B)\n");
    	sb.append("Subtraction: A - B, A - (-B) or (-A) - B\n");
    	sb.append("Multiplication: A * B, A * (-B), or -A * B\n");
    	sb.append("Division: A/B, (-A)/B or A/(-B)\n");
    	sb.append("Power: A^B\n");
    	sb.append("A,B: could be integer or cell reference");
    	return sb;
    }
    
    /**
     * Adds ActionListener to about menu item.
     */
    private void buildAbout() {
        
        about.setEnabled(true);
        about.setMnemonic(KeyEvent.VK_A);
        about.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                JOptionPane.showMessageDialog(null, "TCSS 342 Spreadsheet, Spring 2016\n"
                		+ "By John Bui, Jonah Howard, Henry Lai, And Lisa Taylor.", 
                		"About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    /**
     * Adds menu items to HelpMenu and sets a Mnemonic.
     */
    private void setupHelpMenu() {
        
        buildHelpContents();
        buildAbout();
        
        myHelpMenu.add(helpContents);
        myHelpMenu.add(about);
    }
}
