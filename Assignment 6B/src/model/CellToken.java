package model;

public class CellToken implements Token {

	/** Represents a bad cell. */
	public static final int BadCell = -1;
	
	/** The column for this cell token. */
	private int column;

	/** The row for this cell token. */
	private int row;

	/**
	 * Returns the column for this cell token.
	 * 
	 * @return the column for this cell token
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Returns the row for this cell token.
	 * 
	 * @return the row for this cell token
	 */
	public int getRow() {
		return row;
	}

	public int getCellToken(String inputString, int startIndex, CellToken cellToken) {
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
		if (index == inputString.length()) {
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
	
	public void setColumn(final int theColumn) {
		column = theColumn;
	}
	
	public void setRow(final int theRow) {
		row = theRow;
	}
}
