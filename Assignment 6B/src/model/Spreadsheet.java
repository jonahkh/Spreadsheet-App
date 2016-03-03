package model;

import java.awt.Color;
import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * This class represents a spreadsheet. It stores the information for all of the
 * cells of the spreadsheet.
 * 
 * @author Jonah Howard
 * @vesion 1 March 2016
 */
public class Spreadsheet extends DefaultTableModel implements TableModelListener, Observer {

	/** A generated Serial Version UID. */
	private static final long serialVersionUID = 9025127485326978066L;
	
	/** The width of the column of row numbers in pixels. */
	public static final int ROW_NUMBER_WIDTH = 30;
	
	/** Minimum size for the spreadsheet. */
	public static final int MIN_SPREADSHEET_SIZE = 3;
	
	/** Count of letters. */
	public static final int LETTERS = 26;

	/** How many columns are in this spreadsheet. */
	private int myColumns;
	
	/** How many rows are in this spreadsheet. */
	private int myRows;

	/** The current spreadsheet. */
	private Object[][] mySpreadsheet;
	
	/** The current spreadsheet of cells. */
	private Cell[][] myCells;

	/** Represents the names of the columns. */
	private final Object[] columnNames;

	/** Represents the current JTable. */
	private final JTable myTable;

	/** True if the Display Formulas button is pressed, false otherwise. */
	protected static boolean displayFormulas = true;

	/**
	 * Initializes a new Spreadsheet.
	 * 
	 * @param theWidth the width of this spreadsheet
	 * @param theHeight the height of this spreadsheet
	 */
	public Spreadsheet(final int theWidth, final int theHeight) {
		myColumns = theWidth;
		myRows = theHeight;
		initializeSpreadsheet();
		initializeCells();
		columnNames = new String[myColumns + 1];
		fillColumnNames();
		myTable = new JTable(mySpreadsheet, columnNames) {
			/** A generated serial version UID. */
			private static final long serialVersionUID = -8427343693180623327L;

			// This anonymous inner class disables the row numbers from
			// being editable.
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			}
		};
		setupAllCells();
		// Set interface properties for the table
		myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		myTable.getModel().addTableModelListener(this);
		myTable.getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public void tableChanged(final TableModelEvent theEvent) {
		// Save contents of cell before changing.
		Cell theCell = myCells[theEvent.getFirstRow()][theEvent.getColumn()];
		String oldformula = theCell.getFormula();
		try {
			// Tries to parse the expression entered by the user.
			((Cell) myCells[theEvent.getFirstRow()][theEvent.getColumn()])
					.parseInput((String) mySpreadsheet[theEvent.getFirstRow()][theEvent.getColumn()]);
		} catch (ArithmeticException e) {
			JOptionPane.showMessageDialog(myTable.getParent(), "HAHAHA NICE TRY. Please try again.", "Error!",
					JOptionPane.ERROR_MESSAGE);
			// Revert to old formula in spreadsheet and cells. 
			mySpreadsheet[theEvent.getFirstRow()][theEvent.getColumn()] = oldformula;
			((Cell) myCells[theEvent.getFirstRow()][theEvent.getColumn()]).setFormula(oldformula);
		} catch (Exception e){
			// Display an error and revert to old formula if invalid input.
			JOptionPane.showMessageDialog(myTable.getParent(), "Invalid expression entered. Please try again.", "Error!",
					JOptionPane.ERROR_MESSAGE);
			// Revert to old formula in spreadsheet and cells. 
			mySpreadsheet[theEvent.getFirstRow()][theEvent.getColumn()] = oldformula;
			((Cell) myCells[theEvent.getFirstRow()][theEvent.getColumn()]).setFormula(oldformula);
		}
//		printAllFormulas();
	}

	/**
	 * Returns the current JTable.
	 * 
	 * @return the current table
	 */
	public JTable getTable() {
		return myTable;
	}

	/**
	 * Initialize each cell.
	 * @return 
	 * 
	 * @return the initialized array of cells
	 */
	private void initializeCells() {
		myCells = new Cell[myRows][myColumns + 1];
		for (int i = 0; i < myRows; i++) {
			for (int j = 1; j < myColumns + 1; j++) {
				myCells[i][j] = new Cell(i, j, this);
			}
		}
	}

	/**
	 * updates the spreadsheet at the location with respect to the passed row
	 * and column.
	 * 
	 * @param theRow the row of the spreadsheet to be updated
	 * @param theColumn the column of the spreadsheet to be updated
	 */
	public void updateSpreadsheet(final int theRow, final int theColumn) {
		if (displayFormulas) {
			mySpreadsheet[theRow][theColumn] = myCells[theRow][theColumn].getFormula();
		} else {
			if (myCells[theRow][theColumn].getValue() == 0) {
				// Cell's formula is empty if the value is 0 initially 
				mySpreadsheet[theRow][theColumn] = "";
			} else {
				mySpreadsheet[theRow][theColumn] = myCells[theRow][theColumn].getValue();
			}
		}
	}

	/**
	 * Fill the columns with their respective letter representations.
	 */
	private void fillColumnNames() {
		columnNames[0] = "";
		for (int i = 1; i < myColumns + 1; i++) {
			columnNames[i] = convertToString(i - 1);
		}
	}

	/**
	 * Initialize the spreadsheet array.
	 * 
	 * @return the spreadsheet array
	 */
	private void initializeSpreadsheet() {
		mySpreadsheet = new Object[myRows][myColumns + 1];
		for (int i = 0; i < myRows; i++) {
			for (int j = 0; j < myColumns + 1; j++) {
				if (j == 0) {
					mySpreadsheet[i][j] = i;
				} else {
					mySpreadsheet[i][j] = "";
				}
			}
		}
	}

	/**
	 * This method centers all the cells be setting each columns default cell
	 * render to center the cell's data. It also colors the background of the
	 * row numbers to indicate that they are part of the UI and is uneditable.
	 */
	private void setupAllCells() {
		// For all data columns in the table, center their cell's alignment. 
		for (int i = 1; i < myColumns; i++) {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			myTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		// Special case for first column, set the background color to match the column headers
		// in addition to centering each cell. 
		TableColumn rowNums = myTable.getColumnModel().getColumn(0);
		rowNums.setCellRenderer(new DefaultTableCellRenderer() {
			
			/** A generated serial version UID. */
			private static final long serialVersionUID = 3565976393614019090L;
			@Override
			public Component getTableCellRendererComponent(final JTable table, 
					final Object value, final boolean isSelected, final boolean hasFocus, 
					final int row, final int column) {
				final Component cell = super.getTableCellRendererComponent(table, value, 
						isSelected, hasFocus, row, column);
				final Color lightGray = new Color(238, 238, 238);
				cell.setBackground(lightGray);
				setHorizontalAlignment(JLabel.CENTER);
				return cell;
			}
		});
		
		myTable.getColumnModel().getColumn(0).setPreferredWidth(ROW_NUMBER_WIDTH);
	}

	/**
	 * Return the column names.
	 * 
	 * @return the column names
	 */
	public Object[] getColumnNames() {
		return columnNames;
	}

	/**
	 * Return the spreadsheet.
	 * 
	 * @return the spreadsheet
	 */
	public Object[][] getSpreadsheet() {
		return mySpreadsheet;
	}
	
	/**
	 * Return the cells spreadsheet.
	 * 
	 * @return the cells spreadsheet
	 */
	public Cell[][] getCells() {
		return myCells;
	}

	/**
	 * Returns the String representation of the passed column.
	 * 
	 * @param theColumn the current column being converted
	 * @return the String representation of the passed column
	 */
	public static String convertToString(int theColumn) {
		StringBuilder result = new StringBuilder();
		do {
			// Solve rounding issue
			if (result.length() > 0) {
				theColumn--;
			}
			result.insert(0, (char) ((theColumn % LETTERS) + 65));
			theColumn /= LETTERS;
		} while (theColumn > 0);
		return result.toString();
	}

	/**
	 * Returns the integer version of the passed column.
	 * 
	 * @param theColumn the current column being converted
	 * @return the integer version of the passed column
	 */
	public static int convertToInt(final String theColumn) {
		int current = 0;
		int result = 0;
		int currentLetter;
		while (current < theColumn.length()) {
			if (current > 0) {
				result++;
			}
			currentLetter = theColumn.charAt(current) - 65;
			result *= LETTERS;
			result += currentLetter;
			current++;
		}
		return result;
	}

	/**
	 * Prints the formula for the passed cell token.
	 * 
	 * @param theToken The cell whose formula is being printed.
	 */
	public void printCellFormula(final CellToken theToken) {
		Cell theCell = myCells[theToken.getRow()][theToken.getColumn()];
		System.out.println(theCell.getFormula());
	}

	/**
	 * Prints the formulas of all cells.
	 */
	public void printAllFormulas() {
		for (int row = 0; row < myRows; row++) {
			for (int col = 1; col < myColumns; col++) {
				// Prints the Column and Row with colon (e.g. A4: )
				System.out.print(convertToString(col - 1) + row + ": ");
				// Prints the formula for that cell
				System.out.print(myCells[row][col].getFormula() + "   ");
			}
			// Line break at the end of a row.
			System.out.println();
		}
	}

	/**
	 * Returns the number of rows for this spreadsheet.
	 * 
	 * @return the number of rows for this spreadsheet
	 */
	public int getRows() {
		return myRows;
	}
	
	/**
	 * Returns the number of columns for this spreadsheet.
	 * 
	 * @return the number of columns for this spreadsheet
	 */
	public int getColumns() {
		return myColumns;
	}

	@Override
	public void update(final Observable theObservable, final Object theObject) {
		if (theObject instanceof Boolean) {
			displayFormulas = (boolean) theObject;
		}
	}
}