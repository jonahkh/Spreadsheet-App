package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 * This class represents a single cell in a spreadsheet. It contains data
 * specific to this cell and can depend on the myValue of other cells for
 * mathematical computations.
 * 
 * @author Jonah Howard
 * @author Henry Lai
 * @author John Bui
 */
public class Cell {

	/** Represents the myFormula corresponding to this cell. */
	private String myFormula;

	/** The myValue contained in this cell. */
	private int myValue;

	/** The expression tree for this cell. */
	private final ExpressionTree expressionTree;

	/** The list of dependencies for this cell. */
	private final List<Cell> myDependencies;

	private final List<Cell> myDependents;

	/** The current row for this cell. */
	private final int myRow;

	/** The current column for this cell. */
	private final int myColumn;

	/** Maps an indegree to a list of cells that are dependent on this cell. */
	private final Map<Integer, List<Cell>> dependents;

	/**
	 * Initializes a new cell.
	 * 
	 * @param theColumn
	 *            The column where this cell is located.
	 * @param theRow
	 *            The row where this cell is located.
	 */
	public Cell(final int theColumn, final int theRow) {
		expressionTree = new ExpressionTree();
		myValue = 0;
		myDependencies = new ArrayList<Cell>();
		myDependents = new ArrayList<Cell>();
		myColumn = theColumn;
		myRow = theRow;
		dependents = new TreeMap<Integer, List<Cell>>();
	}

	/**
	 * Add the passed token to the list of dependents.
	 * 
	 * @param theToken
	 *            the token that depends on this cell
	 */
	public void addDependent(final Cell theCell) {
		// System.out.println("dependent added");
		// System.out.println(toString());
		myDependents.add(theCell);
	}

	/**
	 * Removes a dependent from the list of dependents.
	 * 
	 * @param theCell
	 *            the dependent to be removed
	 * @param inDegree
	 *            the inDegree of the passed cell
	 */
	public void removeDependent(final int inDegree, final Cell theCell) {
		dependents.get(inDegree).remove(theCell);
		for (Iterator<Cell> iterator = myDependents.iterator(); iterator.hasNext();) {
			Cell cell = iterator.next();
			if (cell == theCell) {
				iterator.remove();
			}
		}
	}

	public void addDependentVertice(final int inDegree, final Cell theCell) {
		if (dependents.containsKey(inDegree)) {
			dependents.get(inDegree).add(theCell);
		} else {
			dependents.put(inDegree, new ArrayList<Cell>());
			dependents.get(inDegree).add(theCell);
		}
	}

	public int getDependencyCount() {
		return myDependencies.size();
	}

	/**
	 * Notifies each cell in the list of dependents that a change was made to
	 * this cell. Evaluates in topological sorting order.
	 */
	public void updateDependents() {
		for (final int current : dependents.keySet()) {
			for (Cell cell : dependents.get(current)) {
				Spreadsheet.CELLS[cell.getRow()][cell.getColumn()].reEvaluate();

			}
		}
	}

	/**
	 * Parse and evaluate input for this cell.
	 * 
	 * @param input
	 *            The new input for this cell
	 */
	public void parseInput(final String input) {

		//if (isvalid(input)) {
			final Stack<Token> formula = getFormula(input);
			myFormula = input;
			expressionTree.BuildExpressionTree(formula, myDependents);
			myValue = expressionTree.evaluate();
			if (!myDependents.isEmpty()) {
				updateDependents();
			}
			Spreadsheet.updateSpreadsheet(myRow, myColumn);
		//} else {
		//	System.out.println("Wrong");
		//	throw new IllegalArgumentException("Invalid Input");
		//}
	}

	/**
	 * Method will being the check of input (parenthesis, operator...);
	 * 
	 * @param String
	 *            input
	 * @return true: valid , false:invalid
	 */
	private boolean isvalid(final String input) {
		int isvalid = checkparenthesis(input);
		// checkoperands(input);
		if (isvalid == 0 && checkoperands(input) && checkoperator(input)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will return if the operands are corrected
	 * 
	 * @param String input
	 * @return true:valid false:invalid
	 */
	private boolean checkoperands(final String input) {
		String upper = input.toUpperCase();
		char first = upper.charAt(0);
		if (first == '-') {
			if (upper.length() == 1) {
				return false;
			}
		} else if (first == '*' || first == '+' || first == '/') {
			return false;
		}

		// The above is checking the first character.
		if (function(input)) {
			char temp;

			if (first == '-' || first >= 'A' && first <= 'Z' || first >= '0' && first <= '9') {
				// This for loop is also checking for first char to see if it between(A-Z or a-z)
				for (int i = 0; i < upper.length(); i++) {
					temp = upper.charAt(i);

					if (temp >= 'A' && temp <= 'Z') {
						int j = 1;
						while (j < upper.length()) {
							if (upper.charAt(j) > '0' && upper.charAt(j) < '9') {
								break;
							} else {
								return false;
							}

						}
						if (i + 1 >= upper.length()) {
							return false;

						} else if (upper.charAt(i + 1) >= 'A' && upper.charAt(i + 1) <= 'Z') {
							if (i + 2 >= upper.length()) {
								return false;

							} else if (upper.charAt(i + 2) >= '0' || upper.charAt(i + 2) <= '9') {
								if (i + 3 >= upper.length()) {
									return false;
								} else if (upper.charAt(i + 3) >= '0' || upper.charAt(i + 3) <= '9') {
									return true;
								}
							}
						}
						return true;
					}
				}

			} else {
				return false;
			}
		}
		return true;

	}

	private boolean function(String input) {
		String temp = input.replaceAll("\\s", "");

		char[] operator = { '*', '+', '-', '/' };
		for (int i = 0; i < temp.length(); i++) {
			for (int j = 0; j < operator.length; j++) {
				if (operator[j] == temp.charAt(i)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * checking what follow after operator; input String input return true:valid
	 * false:invalid
	 */
	private boolean checkoperator(String input) {
		String temp = input.replaceAll("\\s", "").toUpperCase();
		char[] operator = { '*', '+', '-', '/' };
		for (int i = 0; i < operator.length; i++) { // This loop will check if
													// the last index is an
													// operator.
			if (operator[i] == temp.charAt(temp.length() - 1)) {
				System.out.println("Operator at the end");
				return false;

			}
		}

		for (int i = 0; i < temp.length(); i++) {// after the first loop, this
													// will check to see what is
													// after the operator.
			if (temp.charAt(i) == '+' || temp.charAt(i) == '-' || temp.charAt(i) == '*' || temp.charAt(i) == '/') {
				if (temp.charAt(i + 1) < '0' || temp.charAt(i + 1) > '9' && temp.charAt(i + 1) < 'A'
						|| temp.charAt(i + 1) > 'Z') {
					System.out.println("Operator wrong");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * This method will return if the parenhesises are correct.
	 * 
	 * @param String
	 *            input
	 * @return 0: valid, otherwise is invalid
	 */
	private int checkparenthesis(final String input) {
		int temp = 0;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '(') {
				temp++;
			} else if (c == ')') {
				temp--;
			}
		}
		return temp;
	}

	/**
	 * Evaluates this cell if a change is made to one of its dependencies.
	 */
	public void reEvaluate() {
		myValue = expressionTree.evaluate();
		updateDependents();
		Spreadsheet.updateSpreadsheet(myRow, myColumn);
	}

	/**
	 * Return the myValue for this cell.
	 * 
	 * @return the myValue for this cell
	 */
	public int getValue() {
		return myValue;
	}

	public void removeAllDependencies() {
		for (final Cell cell : myDependencies) {
			cell.removeDependent(myDependencies.size(), this);
		}
		myDependencies.clear();
	}

	/**
	 * Returns a stack representing the passed myFormula.
	 * 
	 * @param myFormula
	 *            the current myFormula being considered
	 * @return A stack representing the passed myFormula
	 */
	public Stack<Token> getFormula(String myFormula) {
		removeAllDependencies();
		Stack<Token> returnStack = new Stack<Token>(); // stack of Tokens
														// (representing a
														// postfix expression)
		boolean error = false;
		char ch = ' ';

		int literalValue = 0;

		CellToken cellToken;
		// Temporary holder variable to access methods inside OperatorToken
		OperatorToken operatorToken = new OperatorToken('a');

		int index = 0; // index into myFormula
		Stack<Token> operatorStack = new Stack<Token>(); // stack of operators

		while (index < myFormula.length()) {
			// get rid of leading whitespace characters
			while (index < myFormula.length()) {
				ch = myFormula.charAt(index);
				if (!Character.isWhitespace(ch)) {
					break;
				}
				index++;
			}

			if (index == myFormula.length()) {
				error = true;
				break;
			}

			// ASSERT: ch now contains the first character of the next token.
			if (operatorToken.isOperator(ch)) {
				// We found an operator token
				switch (ch) {
				case OperatorToken.POW:
				case OperatorToken.PLUS:
				case OperatorToken.MINUS:
				case OperatorToken.MULT:
				case OperatorToken.DIV:
				case OperatorToken.LT_PAREN:
					// push operatorTokens onto the output stack until
					// we reach an operator on the operator stack that has
					// lower priority than the current one.
					OperatorToken stackOperator;
					while (!operatorStack.isEmpty()) {
						stackOperator = (OperatorToken) operatorStack.peek();
						if ((stackOperator.priority() >= operatorToken.operatorPriority(ch))
								&& (stackOperator.getOperatorToken() != OperatorToken.LT_PAREN)) {

							// output the operator to the return stack
							operatorStack.pop();
							returnStack.push(stackOperator);
						} else {
							break;
						}
					}
					break;

				default:
					// This case should NEVER happen
					System.out.println("Error in getFormula.");
					System.exit(0);
					break;
				}
				// push the operator on the operator stack
				operatorStack.push(new OperatorToken(ch));

				index++;

			} else if (ch == ')') { // maybe define OperatorToken.RightParen ?
				OperatorToken stackOperator;
				stackOperator = (OperatorToken) operatorStack.pop();
				// This code does not handle operatorStack underflow.
				while (stackOperator.getOperatorToken() != OperatorToken.LT_PAREN) {
					// pop operators off the stack until a LeftParen appears and
					// place the operators on the output stack
					returnStack.push(stackOperator);
					stackOperator = (OperatorToken) operatorStack.pop();
				}

				index++;
			} else if (Character.isDigit(ch)) {
				// We found a literal token
				literalValue = ch - '0';
				index++;
				while (index < myFormula.length()) {
					ch = myFormula.charAt(index);
					if (Character.isDigit(ch)) {
						literalValue = (literalValue * 10) + (ch - '0');
						index++;
					} else {
						break;
					}
				}
				// place the literal on the output stack
				returnStack.push(new LiteralToken(literalValue));

			} else if (Character.isUpperCase(ch)) {
				// We found a cell reference token
				cellToken = new CellToken(myFormula, index);
				Spreadsheet.CELLS[cellToken.getRow()][cellToken.getColumn()].addDependent(this);
				myDependencies.add(Spreadsheet.CELLS[cellToken.getRow()][cellToken.getColumn()]);
				index = cellToken.getCellToken(myFormula, index);
				if (cellToken.getRow() == CellToken.BAD_CELL) {
					error = true;
					break;
				} else {
					// place the cell reference on the output stack
					returnStack.push(cellToken);
				}

			} else {
				error = true;
				break;
			}
		}

		// pop all remaining operators off the operator stack
		while (!operatorStack.isEmpty()) {
			returnStack.push(operatorStack.pop());
		}

		if (error) {
			// a parse error; return the empty stack
			returnStack.empty();
		}
		// Add reference to this cell and its in-degree to each dependency
		for (final Cell cell : myDependencies) {
			cell.addDependentVertice(myDependencies.size(), this);
		}
		return returnStack;
	}

	/**
	 * Returns the myFormula for this cell.
	 * 
	 * @return Returns a string that represents this cell's myFormula.
	 */
	public String getFormula() {
		return myFormula;
	}

	/**
	 * Prints out a representation of this cell's internal status. (Useful for
	 * debugging.)
	 * 
	 * @return A string representation of the cell's internal status.
	 */
	public String toString() {
		// We can also print out the dependencies later if needed.
		return "Cell: (" + myRow + ", " + myColumn + ")\nValue = " + myValue + "\nFormula = " + myFormula + "\n";
	}

	/**
	 * Return the current row for this cell.
	 * 
	 * @return the current row for this cell
	 */
	public int getRow() {
		return myRow;
	}

	/**
	 * Return the current column for this cell.
	 * 
	 * @return the current column for this cell
	 */
	public int getColumn() {
		return myColumn;
	}
}
