package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Spreadsheet;

/**
 * This class runs the GUI interface for the spreadsheet application.
 * 
 * @author Jonah Howard
 * @author Henry Lai
 */
public class GUI extends Observable {
	
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
	
	/** Represents the spreadsheet. */
	private final Object[][] mySpreadsheetCells;

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
		mySpreadsheetCells = mySpreadsheet.getSpreadsheet();
	}

	/**
	 * Fills the GUI of its contents.
	 */
	public void run() {
		final JPanel panel = new JPanel(new FlowLayout());
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Adds the scrollable pane to the JFrame.
		myFrame.add(myJScrollPane, BorderLayout.CENTER);
		// Sets the minimum size for the window.
		myFrame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		
		JButton formulas = new JButton("Display Formulas");
		JButton values = new JButton("Display Values");
		
//		formulas.setEnabled(false);
		formulas.doClick();
		addListeners(formulas, values);

		panel.add(formulas, BorderLayout.SOUTH);
		panel.add(values, BorderLayout.SOUTH);
		
		myFrame.add(panel, BorderLayout.SOUTH);
		myFrame.pack();
		myFrame.setVisible(true);
	}
	
	
	
	/**
	 * Adds the action listeners for the "Display Values" and "Display Formulas" buttons.
	 * 
	 * @param buttonOne the Display Formulas button
	 * @param buttonTwo the Display Values button
	 */
	private void addListeners(final JButton formulaButton, final JButton valuesButton) {
		formulaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				valuesButton.setEnabled(true);
				formulaButton.setEnabled(false);
				notifyObservers(true);
				for (int i = 0; i < Spreadsheet.ROWS; i++) {
					for (int j = 1; j < Spreadsheet.COLUMNS + 1; j++) {
						if (Spreadsheet.CELLS[i][j].getValue() != 0) {
							Spreadsheet.SPREADSHEET[i][j] = Spreadsheet.CELLS[i][j].getFormula(); 
						}
					}
				}
			}
		});
		
		valuesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				formulaButton.setEnabled(true);
				valuesButton.setEnabled(false);
				notifyObservers(false);
				for (int i = 0; i < Spreadsheet.ROWS; i++) {
					for (int j = 1; j < Spreadsheet.COLUMNS + 1; j++) {
						if (Spreadsheet.CELLS[i][j].getValue() != 0) {
							Spreadsheet.SPREADSHEET[i][j] = Spreadsheet.CELLS[i][j].getValue(); 
						}
					}
				}
			}
		});
	}

	/**
	 * Main driver for this program.
	 * 
	 * @param theArgs command line arguments, to be ignored
	 */
	public static void main(String... theArgs) {
		GUI gui = new GUI();
		gui.run();
	}
}
