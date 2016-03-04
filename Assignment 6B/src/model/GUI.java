package model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * This class runs the GUI interface for the spreadsheet application.
 * 
 * @author Jonah Howard
 * @author Henry Lai
 * @author Lisa Taylor
 * 
 * @version 3 March 2016
 */
public class GUI {
	
	/** The minimum number of rows and columns allowed. */
	private static final int MIN_DIMENSION = 3;

	/** Horizontal offset for initial frame resizing. */
	private static final int HORIZONTAL_OFFSET = 50;
	
	/** Vertical offset for initial frame resizing. */
	private static final int VERTICAL_OFFSET = 122;
	
	/** The width of a cell in pixels. Default = 75 */
	private static final int CELL_WIDTH = 75;

	/** The height of a cell in pixels. Default = 16 */
	private static final int CELL_HEIGHT = 16;
	
	/** The JFrame that the spreadsheet is displayed on. */
	private final JFrame myFrame;
	
	/** The spreadsheet that contains all the data. */
	private Spreadsheet mySpreadsheet;
	
	/** The user-inputed number of rows in the spreadsheet. */
	private int rows;
	
	/** The user-inputed number of columns in the spreadsheet. */
	private int cols;

	/**
	 * This constructor initializes the GUI interface.
	 */
	public GUI() {
		// Sets the title of the program in the title bar.
		myFrame = new JFrame("TCSS 342 Spreadsheet - Group 8");
		final Dimension dimension = initialize();
		mySpreadsheet = new Spreadsheet((int) dimension.getWidth(), (int) dimension.getHeight());
		resizeComponents();
	}
	
	/**
	 * This method adjusts the initial and minimum component size based on the
	 * number of rows and columns.
	 */
	private void resizeComponents() {
		// Calculate the width and height of all components based on table dimensions.
		final int newWidth = (HORIZONTAL_OFFSET + CELL_WIDTH * cols);
		final int newHeight = (VERTICAL_OFFSET + CELL_HEIGHT * rows);
		
		// Gets the screen resolution of user's system.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final double width = screenSize.getWidth();
		final double height = screenSize.getHeight();
		
		// Sets the initial window size to be the minimum of either screen resolution or calculated dimensions.
		myFrame.setSize((int) Math.min(newWidth, width), (int) Math.min(newHeight, height));
		
		// Sets the minimum window size to the the minimum of 6 columns and 8 rows unless it is smaller.
		final int minWidth = (int) Math.min(newWidth, HORIZONTAL_OFFSET + CELL_WIDTH * 6);
		final int minHeight = (int) Math.min(newHeight, VERTICAL_OFFSET + CELL_HEIGHT * 8);
		
		// Sets the maximum window size to be the table dimension unless it is larger than resolution.
		myFrame.setMinimumSize(new Dimension(minWidth, minHeight));
	}

	/**
	 * Prompts the user to enter the desired size of the spread sheet.
	 * 
	 * @return the size of the spread sheet
	 */
	private Dimension initialize() {
		// This code in this method was used from
		// http://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
		// Some minor modifications have been made to variable names
		final Dimension dim = new Dimension();
		JTextField rowSize = new JTextField(5);
		JTextField columnSize = new JTextField(5);
		// Format the text fields
		
		// The main panel for the dialog
		JPanel panel = new JPanel();
		// Holds the text boxes
		JPanel bottomPanel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel textPanel = new JPanel();
		textPanel.add(new JLabel("Minimum size is 3x3."));
		panel.add(textPanel); // This is asserted
		
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
				// Assert the minimum size of 3x3
				cols = Math.max(Integer.parseInt(columnSize.getText()), MIN_DIMENSION);
				rows = Math.max(Integer.parseInt(rowSize.getText()), MIN_DIMENSION);
			} else {
				System.exit(0);	// Terminate the program
			}
		} catch (NumberFormatException e) {	// If invalid input is entered
			// Both dimensions set to minimum size
			rows = cols = MIN_DIMENSION;
			JOptionPane.showMessageDialog(myFrame.getContentPane(), 
					"Invalid dimensions entered! Using default 3 x 3 table.", 
					"Error! Invalid Dimensions!",
					JOptionPane.ERROR_MESSAGE);
		}
		dim.setSize(cols, rows);
		return dim;
	}

	/**
	 * Fills the GUI of its contents.
	 */
	public void run() {
		final JPanel panel = new JPanel(new FlowLayout());
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Adds the scrollable pane to the JFrame to enable the scrollbar
		myFrame.add(new JScrollPane(mySpreadsheet.getTable(), 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		// Sets the minimum size for the window.
		//myFrame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		myFrame.setLocationRelativeTo(null);
		
		// Add buttons to the frame
		JButton formulas = new JButton("Display Formulas");
		JButton values = new JButton("Display Values");
		
		
		formulas.setEnabled(false);
		formulas.doClick();
		addListeners(formulas, values);

		panel.add(formulas, BorderLayout.SOUTH);
		panel.add(values, BorderLayout.SOUTH);
		addMenuBar(formulas, values);
		myFrame.add(panel, BorderLayout.SOUTH);
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
				// Reverse the enabling of the buttons
				valuesButton.setEnabled(true);
				formulaButton.setEnabled(false);
				// Update the spreadsheet
				mySpreadsheet.setFormulaMode(true);
				// Fill each active cell with its corresponding formula
				for (int i = 0; i < mySpreadsheet.getRows(); i++) {
					for (int j = 1; j < mySpreadsheet.getColumns() + 1; j++) {
						mySpreadsheet.getSpreadsheet()[i][j] = 
								mySpreadsheet.getCells()[i][j].getFormula(); 
					}
				}
				myFrame.repaint();
			}
		});
		valuesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				// Reverse the enabling of the buttons
				formulaButton.setEnabled(true);
				valuesButton.setEnabled(false);
				// Update the spreadsheet
				mySpreadsheet.setFormulaMode(false);
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
		});
	}
	
	/**
	 * Adds the menu bar to this frame.
	 * 
	 * @param theFormulas the formulas button
	 * @param theValues the values button
	 */
	private void addMenuBar(final JButton theFormulas, final JButton theValues) {
		final JMenuBar menu = new JMenuBar();
		final JMenu file = new JMenu("File");
		final JMenu options = new JMenu("Options");
		fillFileMenu(file);
		fillOptionsMenu(options, theFormulas, theValues);
		menu.add(file);
		menu.add(options);
		myFrame.setJMenuBar(menu);
	}
	
	/**
	 * Fills the file menu of its components.
	 * 
	 * @param theFile the file menu
	 */
	public void fillFileMenu(final JMenu theFile) {
		final JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				myFrame.dispose();
			}
		});
		final JMenuItem open = new JMenuItem("Open");
		final JMenuItem save = new JMenuItem("save");
		
		theFile.add(open);
		theFile.add(save);
		theFile.addSeparator();
		theFile.add(exit);
	}
	
	/**
	 * Fills the options menu of its components.
	 * 
	 * @param theOptions the options menu
	 * @param theFormulas the formulas button
	 * @param theValues the values button
	 */
	public void fillOptionsMenu(final JMenu theOptions, final JButton theFormulas, 
			final JButton theValues) {
		final JRadioButtonMenuItem form = new JRadioButtonMenuItem(theFormulas.getAction());
		final JRadioButtonMenuItem vals = new JRadioButtonMenuItem(theValues.getAction());
		form.setAccelerator(KeyStroke.getKeyStroke("control F"));
		vals.setAccelerator(KeyStroke.getKeyStroke("control V"));
		form.setSelected(true);
		// Add components to the menu
		final ButtonGroup group = new ButtonGroup();
		group.add(form);
		group.add(vals);
		final AbstractAction clearAll = new AbstractAction() {
			/** A generated serial version UID.*/
			private static final long serialVersionUID = -5502251240488341976L;

			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				// Zero out all active cells
				for (int rows = 0; rows < mySpreadsheet.getRows(); rows++) {
					for (int cols = 1; cols < mySpreadsheet.getColumns(); cols++) {
						if (mySpreadsheet.getCells()[rows][cols].getValue() != 0) {
							mySpreadsheet.getCells()[rows][cols].setValue(0);
							mySpreadsheet.getCells()[rows][cols].setFormula("");
						}
					}
				}
				// Reset values in cells and go back to formula mode
				theValues.doClick();
				theFormulas.doClick();
			}
		};
		final JMenuItem clear = new JMenuItem(clearAll);
		clear.setText("Clear All");
		final AbstractAction resizeAction = new AbstractAction() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				Dimension dim = initialize();
				rows = (int) dim.getWidth();
				cols = (int) dim.getHeight();
				Spreadsheet newSheet = new Spreadsheet(rows, cols);
				for (int i = 0; i < mySpreadsheet.getRows(); i++) {
					for (int j = 1; j < mySpreadsheet.getColumns(); j++) {
						if (mySpreadsheet.getCells()[i][j].getValue() != 0 && i <= rows && j <= cols) {
							newSheet.getCells()[i][j] = mySpreadsheet.getCells()[rows][cols];
							newSheet.getSpreadsheet()[i][j] = mySpreadsheet.getSpreadsheet()[i][j];
						}
					}
				}
				mySpreadsheet = newSheet;
				theValues.doClick();
				theFormulas.doClick();
				myFrame.removeAll();
				myFrame.add(new JScrollPane(mySpreadsheet.getTable(), 
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
			}
		};
		final JMenuItem resize = new JMenuItem(resizeAction);
		resize.setText("Resize");
		theOptions.add(form);
		theOptions.add(vals);
		theOptions.addSeparator();
		theOptions.add(clear);
		theOptions.add(resize);
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
