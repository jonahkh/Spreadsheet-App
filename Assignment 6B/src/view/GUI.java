package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Spreadsheet;

/**
 * This class runs the GUI interface for the spreadsheet application.
 * 
 * @author Jonah Howard 
 * @author Henry Lai
 */
public class GUI {
	/** The minimum width allowed for the window. */
	private static final int MIN_WIDTH = 400;
	/** The minimum height allowed for the window. */
	private static final int MIN_HEIGHT = 300;
	/** The JFrame that the spreadsheet is displayed on. */
	private final JFrame myFrame;
	/** The JScrollPane that the spreadsheet table resides in. */
	private final JScrollPane myJScrollPane;
	/** The spreadsheet that contains all the data. */
	private final Spreadsheet mySpreadsheet;
	
	/**
	 * This constructor initializes the GUI interface.
	 */
	public GUI() {
		// Sets the title of the program in the title bar.
		myFrame = new JFrame("TCSS 342 Spreadsheet - Group 8");
		mySpreadsheet = new Spreadsheet();
		// Adds the spreadsheet table to a JScrollPane that allows for resizing.
		myJScrollPane = new JScrollPane(mySpreadsheet.getTable(), 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// Adds the scrollable pane to the JFrame.
		myFrame.add(myJScrollPane, BorderLayout.CENTER);
		// Sets the minimum size for the window.
		myFrame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		myFrame.pack();
	}
	
	public void run() {
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
	}
	
	public static void main(String... args) {
		GUI gui = new GUI();
		gui.run();
	}
}
