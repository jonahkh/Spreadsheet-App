package model;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * This class represents a spreadsheet. It stores the information for all of the
 * cells of the spreadsheet.
 * 
 * @author Jonah Howard
 */
public class Spreadsheet extends DefaultTableModel implements TableModelListener, Observer {

	/** A generated Serial Version UID. */
	private static final long serialVersionUID = 9025127485326978066L;

	/** How many columns are in this spreadsheet. */
	public static final int COLUMNS = 35;

	/** How many rows are in this spreadsheet. */
	public static final int ROWS = 20;
	
	/** Count of letters. */
	public static final int LETTERS = 26;
	
	/** Represents each cell of the spreadsheet. */
	public static final Cell[][] CELLS = initializeCells();

	/** The current spreadsheet. */
	public static final Object[][] SPREADSHEET = initializeSpreadsheet();

	/** The current spreadsheet. */
	private final Object[][] spreadsheet;

	/** Represents the names of the columns. */
	private final Object[] columnNames;

	/** Represents the current JTable. */
	private final JTable myTable;
	
	protected static boolean displayFormulas = true;

	/**
	 * Initializes a new Spreadsheet.
	 */
	public Spreadsheet() {
		spreadsheet = new Object[ROWS][COLUMNS + 1];
		columnNames = new String[COLUMNS + 1];
		// CELLS = new Cell[ROWS][COLUMNS + 1];
		// initializeCells();
		fillColumnNames();
		myTable = new JTable(SPREADSHEET, columnNames) {
			// This anonymous inner class disables the row numbers from
			// being editable.
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			}
		};
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalTextPosition(DefaultTableCellRenderer.CENTER);
		TableModel model = null;
		myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		myTable.getModel().addTableModelListener(this);
		myTable.getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public void tableChanged(final TableModelEvent theEvent) {
		// Prints the contents of the cell
		((Cell) CELLS[theEvent.getFirstRow()][theEvent.getColumn()])
				.parseInput((String) SPREADSHEET[theEvent.getFirstRow()][theEvent
						.getColumn()]);
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
	 * 
	 * @return the initialized array of cells
	 */
	private static Cell[][] initializeCells() {
		final Cell[][] newcells = new Cell[ROWS][COLUMNS + 1];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 1; j < COLUMNS + 1; j++) {
				// cells[i][j] = new Cell();
				newcells[i][j] = new Cell(j, i);
			}
		}
		return newcells;
	}
	
	/**
	 * updates the spreadsheet at the location with respect to the passed row and column.
	 * 
	 * @param theRow the row of the spreadsheeet to be updated
	 * @param theColumn the column of the spreadsheet to be updated
	 */
	public static void updateSpreadsheet(final int theRow, final int theColumn) {
//		System.out.println(CELLS[theRow][theColumn].getValue());
//		System.out.println(CELLS[theRow][theColumn].getFormula());
//		System.out.println();
		if(CELLS[theRow][theColumn].getValue() == 0) {
			SPREADSHEET[theRow][theColumn] = "";
		}
		if (displayFormulas) {
			SPREADSHEET[theRow][theColumn] = CELLS[theRow][theColumn].getFormula();
		} else {
			SPREADSHEET[theRow][theColumn] = CELLS[theRow][theColumn].getValue();
		}
	}

	/**
	 * Fill the columns with their respective letter representations.
	 */
	private void fillColumnNames() {
		columnNames[0] = "";
		for (int i = 1; i < COLUMNS + 1; i++) {
			columnNames[i] = convertToString(i - 1);
		}
		// for (int i = 0; i < ROWS; i++) {
		// for (int j = 0; j < COLUMNS + 1; j++) {
		// if (j == 0) {
		// spreadsheet[i][j] = i;
		// } else {
		// spreadsheet[i][j] = "";
		// }
		// }
		// }
	}

	/**
	 * Initialize the spreadsheet array.
	 * 
	 * @return the spreadsheet array
	 */
	private static Object[][] initializeSpreadsheet() {
		final Object[][] newSheet = new Object[ROWS][COLUMNS + 1];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS + 1; j++) {
				if (j == 0) {
					newSheet[i][j] = i;
				} else {
					newSheet[i][j] = "";
				}
			}
		}
		return newSheet;
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
		return spreadsheet;
	}

	/**
	 * Returns the String representation of the passed column.
	 * 
	 * @param theColumn
	 *            the current column being converted
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
	 * @param theColumn
	 *            the current column being converted
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
	 * @param theToken
	 *            the cell token being considered
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

	@Override
	public void update(final Observable theObservable, final Object theObject) {
		if (theObject instanceof Boolean) {
			displayFormulas = (boolean) theObject;
		}
	}
}