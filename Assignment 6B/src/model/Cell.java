package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * This class represents a single cell in a spreadsheet. It contains data
 * specific to this cell and can depend on the myValue of other cells for
 * mathematical computations.
 * 
 * @author Jonah Howard
 * @author Henry Lai
 */
public class Cell {

	/** Represents the myFormula corresponding to this cell. */
	private String myFormula;

	/** The myValue contained in this cell. */
	private int myValue;

	/** The expression tree for this cell. */
	private ExpressionTree expressionTree;

	/** The list of dependencies for this cell. */
	private List<Cell> myDependencies;
	
	private List<Cell> myDependents;
	
	/** The current row for this cell. */
	private final int myRow;
	
	/** The current column for this cell. */
	private final int myColumn;

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
	}
	
	/**
	 * Add the passed token to the list of dependents.
	 * 
	 * @param theToken the token that depends on this cell
	 */
	public void addDependent(final Cell theCell) {
//		System.out.println("dependent added");
		myDependents.add(theCell);
	}
	
	/**
	 * Notifies each cell in the list of dependents that a change was made to this cell.
	 */
	public void updateDependents() {
//		System.out.println("Updating dependents");
		for (Cell cell : myDependents) {
//			System.out.println("Row = " + cell.getRow() + ", Column = " + cell.getColumn());
//			System.out.println(Spreadsheet.CELLS[cell.getRow()][cell.getColumn()].getValue());
			Spreadsheet.CELLS[cell.getRow()][cell.getColumn()].reEvaluate();
			
		}
	}

	/**
	 * Parse and evaluate input for this cell.
	 * 
	 * @param input The new input for this cell
	 */
	public void parseInput(final String input) {
		final Stack<Token> formula = getFormula(input);
		myFormula = input;
//		System.out.println("Formula: " + input);
		expressionTree.BuildExpressionTree(formula, myDependencies);
//    	System.out.println("My Tree:");
//		expressionTree.printTree();
		myValue = expressionTree.evaluate();
		if (!myDependents.isEmpty()) {
			updateDependents();
		}
		Spreadsheet.updateSpreadsheet(myRow, myColumn);
//		System.out.println("My value is: " + myValue);
//		System.out.println();
	}
	
	/**
	 * Evaluates this cell if a change is made to one of its dependencies.
	 */
	public void reEvaluate() {
		myValue = expressionTree.evaluate();
//		System.out.println("Value = " + myValue);
//		System.out.println(myRow + ", " + myColumn);
		Spreadsheet.updateSpreadsheet(myRow, myColumn);
		Spreadsheet.SPREADSHEET[myRow][myColumn] = myValue;
		
	}

	/**
	 * Return the myValue for this cell.
	 * 
	 * @return the myValue for this cell
	 */
	public int getValue() {
		return myValue;
	}

	/**
	 * Returns a stack representing the passed myFormula.
	 * 
	 * @param myFormula the current myFormula being considered
	 * @return A stack representing the passed myFormula
	 */
	public Stack<Token> getFormula(String myFormula) {
		Stack<Token> returnStack = new Stack<Token>(); // stack of Tokens
														// (representing a
														// postfix expression)
		boolean error = false;
		char ch = ' ';

		int literalValue = 0;

		CellToken cellToken;
		OperatorToken operatorToken = new OperatorToken('a');   // Temporary
																// holder
																// variable to
																// access
																// methods
																// inside
																// OperatorToken
		int column = 0;
		int row = 0;

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
				index = cellToken.getCellToken(myFormula, index);
				if (cellToken.getRow() == CellToken.BadCell) {
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
		return "[" + "Value: " + myValue + "]";
	}
	
	public int getRow() {
		return myRow;
	}
	
	public int getColumn() {
		return myColumn;
	}
}
