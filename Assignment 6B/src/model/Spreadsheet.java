package model;

public class Spreadsheet {
	
	/** How many columns are in this spreadsheet. */
	public static final int COLUMNS = 6;
	
	/** How many rows are in this spreadsheet. */
	public static final int ROWS = 6;
	
	/** The current spreadsheet. */
	private final Cell[][] spreadsheet;
	
	public Spreadsheet(final int size) {
		spreadsheet = new Cell[size][size];
	}
	
	public void printCellFormula(final CellToken theToken) {
		
	}
	
	public void printAllFormulas() {
		
	}
	
	public void getCellToken(String theInputCell, int n, CellToken theToken) {
		
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
	 * Returns the number of rows for this spreadsheet.
	 * 
	 * @return the number of rows for this spreadsheet
	 */
	public int getNumColumns() {
		return COLUMNS;
	}
}
