package model;

public class Spreadsheet {

	/** How many columns are in this spreadsheet. */
	public static final int COLUMNS = 6;

	/** How many rows are in this spreadsheet. */
	public static final int ROWS = 6;
	
	public static final int LETTERS = 26;

	/** The current spreadsheet. */
	private final Object[][] spreadsheet;
	
	private final Object[] columnNames;

	public Spreadsheet() {
		spreadsheet = new Cell[ROWS][COLUMNS + 1];
		columnNames = new String[COLUMNS + 1];
		fillColumnNames();
	}
	
	private void fillColumnNames() {
		for (int i = 1; i < COLUMNS + 1; i++) {
			columnNames[i] = convertToString(i);
		}
	}
	
	public Object[] getColumnNames() {
		return columnNames;
	}

	public Object[][] getSpreadsheet() {
		return spreadsheet;
	}
	
	public void print() {
		System.out.println("Hello");
	}

	public String convertToString(int n) {
		StringBuilder result = new StringBuilder();
		do {
			// Solve rounding issue
			if (result.length() > 0) {
				n--;
			}
			result.insert(0, (char) ((n % LETTERS) + 65));
			n /= LETTERS;
		} while (n > 0);
		return result.toString();
	}

	public int convertToInt(String s) {
		int current = 0;
		int result = 0;
		int currentLetter;
		while (current < s.length()) {
			if (current > 0) {
				result++;
			}
			currentLetter = s.charAt(current) - 65;
			result *= LETTERS;
			result += currentLetter;
			current++;
		}
		return result;
	}

	int getCellToken(String inputString, int startIndex, CellToken cellToken) {
		char ch;
		int column = 0;
		int row = 0;
		int index = startIndex;

		// handle a bad startIndex
		if ((startIndex < 0) || (startIndex >= inputString.length())) {
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		}

		// get rid of leading whitespace characters
		while (index < inputString.length()) {
			ch = inputString.charAt(index);
			if (!Character.isWhitespace(ch)) {
				break;
			}
			index++;
		}
		if (index == inputString.length) {
			// reached the end of the string before finding a capital letter
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		}

		// ASSERT: index now points to the first non-whitespace character

		ch = inputString.charAt(index);
		// process CAPITAL alphabetic characters to calculate the column
		if (!Character.isUpperCase(ch)) {
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		} else {
			column = ch - 'A';
			index++;
		}

		while (index < inputString.length()) {
			ch = inputString.charAt(index);
			if (Character.isUpperCase(ch)) {
				column = ((column + 1) * 26) + (ch - 'A');
				index++;
			} else {
				break;
			}
		}
		if (index == inputString.length()) {
			// reached the end of the string before fully parsing the cell
			// reference
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		}

		// ASSERT: We have processed leading whitespace and the
		// capital letters of the cell reference

		// read numeric characters to calculate the row
		if (Character.isDigit(ch)) {
			row = ch - '0';
			index++;
		} else {
			cellToken.setColumn(BadCell);
			cellToken.setRow(BadCell);
			return index;
		}

		while (index < inputString.length()) {
			ch = inputString.charAt(index);
			if (Character.isDigit(ch)) {
				row = (row * 10) + (ch - '0');
				index++;
			} else {
				break;
			}
		}

		// successfully parsed a cell reference
		cellToken.setColumn(column);
		cellToken.setRow(row);
		return index;
	}

	public void printCellFormula(final CellToken theToken) {

	}

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
	 * Returns the number of rows for this spreadsheet.
	 * 
	 * @return the number of rows for this spreadsheet
	 */
	public int getNumColumns() {
		return COLUMNS;
	}
}
