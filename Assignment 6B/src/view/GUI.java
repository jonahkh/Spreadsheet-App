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

import model.Spreadsheet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * This class runs the GUI interface for the spreadsheet application.
 */
public class GUI {

	/** The minimum number of rows and columns allowed. */
	private static final int MIN_DIMENSION = 5;

	/** Horizontal offset for initial frame resizing. */
	private static final int HORIZONTAL_OFFSET = 49;
	
	/** Vertical offset for initial frame resizing. */
	private static final int VERTICAL_OFFSET = 127;
	
	/** The width of a cell in pixels. Default = 75 */
	private static final int CELL_WIDTH = 75;

	/** The height of a cell in pixels. Default = 16 */
	private static final int CELL_HEIGHT = 16;
	
    /** The JFrame that the spreadsheet is displayed on. */
    private final JFrame myFrame;
	
    /** JMenuBar to display Menus. */
    private final JMenuBar  myMenuBar;
    
    /** JToolBar to display commonly used tools. */
    private final JToolBar myToolBar;
    
    /** JMenu to display window views. */
    private final WindowMenu myWindowViews;
	
	/** The spreadsheet that contains all the data. */
	private Spreadsheet mySpreadsheet;
	
	/** The user-inputed number of rows in the spreadsheet. */
	private int rows;
	
	/** The user-inputed number of columns in the spreadsheet. */
	private int cols;

	/** Initializes the interface for the Spreadsheet application. */
	public GUI() {

        myFrame = new JFrame("TCSS 342 Spreadsheet - Group 8");		
	    final Dimension dimension = initialize();
	    mySpreadsheet = new Spreadsheet((int) dimension.getWidth(), (int) dimension.getHeight(), myFrame);
		myMenuBar = new JMenuBar();
        myToolBar = new JToolBar();
        myWindowViews = new WindowMenu(myFrame, mySpreadsheet);

		resizeComponents();
	}
	
	/**
	 * This method adjusts the initial and minimum component size based on the
	 * number of rows and columns.
	 */
	private void resizeComponents() {
	    
		// Calculate the width and height of all components based on table dimensions.
		int minWidth = (HORIZONTAL_OFFSET + CELL_WIDTH * cols);
		int minHeight = (VERTICAL_OFFSET + CELL_HEIGHT * rows);
		
		// Gets the screen resolution of user's system.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final double width = screenSize.getWidth();
		final double height = screenSize.getHeight();
		
		// Sets the initial window size to be the minimum of either screen resolution or calculated dimensions.
		myFrame.setSize((int) Math.min(minWidth, width), (int) Math.min(minHeight, height));
		
		// Sets the minimum window size to the the default of 6 columns and 8 rows unless it is smaller.
		minWidth = (int) Math.min(minWidth, HORIZONTAL_OFFSET + CELL_WIDTH * 6);
		minHeight = (int) Math.min(minHeight, VERTICAL_OFFSET + CELL_HEIGHT * 8);
		
		// Sets the minimum window size to be the table dimension unless it is larger than resolution.
		myFrame.setMinimumSize(new Dimension(minWidth, minHeight));
	}

	/**
	 * Prompts the user to enter the desired size of the spread sheet.
	 * 
	 * This code in this method was used from
	 * http://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
	 * Some minor modifications have been made to variable names.
	 * 
	 * @return the size of the spread sheet
	 */
	private Dimension initialize() {

	    // Format the text fields
		final Dimension dim = new Dimension();
		JTextField rowSize = new JTextField(5);
		JTextField columnSize = new JTextField(5);
		
		// The main panel for the dialog
		JPanel panel = new JPanel();
		
		// Holds the text boxes
		JPanel bottomPanel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel textPanel = new JPanel();
		textPanel.add(new JLabel("Minimum size is " + MIN_DIMENSION + "x" + MIN_DIMENSION + ".")); 
		panel.add(textPanel); 
		
		// Format the text fields
		bottomPanel.add(new JLabel("Rows:"));
		bottomPanel.add(rowSize);
		bottomPanel.add(Box.createVerticalStrut(15));
		bottomPanel.add(new JLabel("Columns"));
		bottomPanel.add(columnSize);
	
		panel.add(bottomPanel);
		
		// Prompt user to set the size of the spreadsheet
		try {
			int result = JOptionPane.showConfirmDialog(myFrame, panel, 
					"Please enter the size of the spreadsheet:", 
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				// Assert the minimum size of MIN_DIMENSION x MIN_DIMENSION
				cols = Math.max(Integer.parseInt(columnSize.getText()), MIN_DIMENSION);
				rows = Math.max(Integer.parseInt(rowSize.getText()), MIN_DIMENSION);
			} else {
				System.exit(0);	// Terminate the program
			}
			
		} catch (NumberFormatException e) {	
		    // If invalid input is entered
			// Both dimensions set to minimum size
			rows = cols = MIN_DIMENSION;
			JOptionPane.showMessageDialog(myFrame.getContentPane(), 
					"Invalid dimensions entered! " + 
					"Using default " + MIN_DIMENSION + "x" + MIN_DIMENSION + " table.", 
					"Error! Invalid Dimensions!",
					JOptionPane.ERROR_MESSAGE);
		}
		
		dim.setSize(cols, rows);
		return dim;
    }

    /**
     * Fills the GUI with its contents.
     */
    public void run() {
        
        setupJFrame();
        addMenuBar();
        addToolBar();
        
        myFrame.setVisible(true);
        myFrame.setLocationRelativeTo(null);
	}
	
    /**
     * Sets up the JFrame configuration.
     */
	private void setupJFrame() {
		// Override default close behavior.
		myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Anonymous listener class prompts user for confirmation on window close.
		myFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we) { 
				// Prompts the user for confirmation that they want to quit.
                int input = JOptionPane.showConfirmDialog(myFrame, 
                		"Are you sure you want to quit?", 
                		"Quit?", JOptionPane.YES_NO_OPTION);
                // Proceeds to quit the program upon confirmation.
                if (input == JOptionPane.YES_OPTION) {
                	System.exit(0);
                }
			}  
		});
		// Adds scroll bars to the table by putting Spreadsheet in a JScrollPane.
        myFrame.add(new JScrollPane(mySpreadsheet.getTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
	}

	/**
	 * Adds the menu bar to this frame.
	 */
	private void addMenuBar() {
	    
		myMenuBar.add(new FileMenu(myFrame).getFileMenu());
		myMenuBar.add(new OptionsMenu(myFrame, mySpreadsheet, mySpreadsheet.getTable()).getOptionsMenu());
		myMenuBar.add(myWindowViews.getWindowMenu());
		myMenuBar.add(new HelpMenu().getHelpMenu());
		
		myFrame.setJMenuBar(myMenuBar);
	}
	
    /**
     * Adds the tool bar to this frame.
     */
    private void addToolBar() {
        
        final List<WindowMenu.WindowAction> windowActions = myWindowViews.getWindowActions();
        final ButtonGroup btngrp = new ButtonGroup();
        // comment
        for (final WindowMenu.WindowAction winAction : windowActions) {
            
            final JToggleButton tbtn = new JToggleButton(winAction);
            btngrp.add(tbtn);
            myToolBar.add(tbtn);
        }
        
        myToolBar.setFloatable(false);
        myToolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        myFrame.add(myToolBar,  BorderLayout.SOUTH);
    }
}
