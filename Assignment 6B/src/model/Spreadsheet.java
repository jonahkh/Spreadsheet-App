package model;

import java.util.Stack;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * This class represents a spreadsheet. It stores the information for all of the cells of the
 * spreadsheet.
 * 
 * @author Jonah Howard
 */
public class Spreadsheet implements TableModelListener {

	protected static final Cell[][] CELLS = initializeCells();
	
	/** How many columns are in this spreadsheet. */
	public static final int COLUMNS = 35;

	/** How many rows are in this spreadsheet. */
	public static final int ROWS = 20;
	
	public static final int LETTERS = 26;

	/** The current spreadsheet. */
	private final Object[][] spreadsheet;
	
	/** Represents each cell of the spreadsheet. */
	private final Object[][] cells;
	
	/** Represents the names of the columns. */
	private final Object[] columnNames;
	
	/** Represents the current JTable. */
	private final JTable myTable;

	/**
	 * Initializes a new Spreadsheet.
	 */
	public Spreadsheet() {
		spreadsheet = new Object[ROWS][COLUMNS + 1];
		columnNames = new String[COLUMNS + 1];
		cells = new Cell[ROWS][COLUMNS + 1];
//		initializeCells();
		fillColumnNames();
		myTable = new JTable(spreadsheet, columnNames);
		TableModel model = null;
		myTable.getModel().addTableModelListener(this);
		myTable.getTableHeader().setReorderingAllowed(false);
	}
	
	@Override
	public void tableChanged(final TableModelEvent theEvent) {
		// Prints the contents of the cell
		System.out.println(spreadsheet[theEvent.getFirstRow()][theEvent.getColumn()]);
		
		((Cell) CELLS[theEvent.getFirstRow()][theEvent.getColumn()]).parseInput(
				(String) spreadsheet[theEvent.getFirstRow()][theEvent.getColumn()]);
	}
	
	public JTable getTable() {
		return myTable;
	}
	
	private static Cell[][] initializeCells() {
		final Cell[][] newCells = new Cell[ROWS][COLUMNS + 1];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 1; j < COLUMNS + 1; j++) {
//				cells[i][j] = new Cell();
				newCells[i][j] = new Cell();
			}
		}
		return newCells;
	}
	
	private void fillColumnNames() {
		columnNames[0] = "";
		for (int i = 1; i < COLUMNS + 1; i++) {
			columnNames[i] = convertToString(i - 1);
		}
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS + 1; j++) {
				if (j == 0) {
					spreadsheet[i][j] = i;
				} else {
					spreadsheet[i][j] = "";
				}
			}
		}
	}
	
	public Object[] getColumnNames() {
		return columnNames;
	}

	public Object[][] getSpreadsheet() {
		return spreadsheet;
	}
	
	/**
	 * Returns the String representation of the passed column.
	 * 
	 * @param theColumn the current column being converted
	 * @return the String representation of the passed column
	 */
	public String convertToString(int theColumn) {
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
	public int convertToInt(final String theColumn) {
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
	 * @param theToken the cell token being considered
	 */
	public void printCellFormula(final CellToken theToken) {

	}

	/**
	 * Prints the formulas of all cells.
	 */
	public void printAllFormulas() {

	}

	/**
	 * Returns the number of rows for this spreadsheet.
	 * 
	 * @return the number of rows for this spreadsheet
	 */
	public int getNumRows() {
		return ROWS;
	}

	/**
	 * Returns the number of columns for this spreadsheet.
	 * 
	 * @return the number of columns for this spreadsheet
	 */
	public int getNumColumns() {
		return COLUMNS;
	}
}