package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.table.DefaultTableModel;

/**
 * This class represents a single cell in a spreadsheet. It contains data
 * specific to this cell and can depend on the value of other cells for 
 * mathematical computations.
 * 
 * @author Jonah Howard
 * @author Henry Lai
 */
public class Cell {
	
	/** Represents the formula corresponding to this cell. */
	private String formula;
	
	/** The value contained in this cell. */
	private int value;
	
	/** The expression tree for this cell. */
	private ExpressionTree expressionTree;
	
	/** The list of dependencies for this cell. */
	private List<Cell> myDependencies;
	
	
	/**
	 * Initializes a new cell.
	 * 
	 * @param theColumn The column where this cell is located.
	 * @param theRow The row where this cell is located.
	 */
	public Cell() {
		expressionTree = new ExpressionTree();
		value = 0;
		myDependencies = new ArrayList<Cell>();
		
	}
	
	/**
	 * Parse and evaluate input for this cell.
	 * 
	 * @param input The new input for this cell
	 */
	public void parseInput(final String input) {
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) < 48 || input.charAt(i) > 57) { // If not an integer
				value = 1;
			}
		}
	}
	
	/**
	 * Return the value for this cell.
	 * 
	 * @return the value for this cell
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * evaluates this cell.
	 * 
	 * @param theSpreadsheet the current spreadsheet
	 */
	public void evaluate (final Spreadsheet theSpreadsheet) {
		// Stub
	}

	
	public Stack<Token> getFormula(String formula) {
	    Stack<Token> returnStack = new Stack<Token>();  // stack of Tokens (representing a postfix expression)
	    boolean error = false;
	    char ch = ' ';

	    int literalValue = 0;

	    CellToken cellToken;
	    OperatorToken operatorToken = new OperatorToken('a');	// Temporary holder variable to access methods inside OperatorToken
	    int column = 0;
	    int row = 0;

	    int index = 0;  // index into formula
	    Stack<Token> operatorStack = new Stack<Token>();  // stack of operators

	    while (index < formula.length() ) {
	        // get rid of leading whitespace characters
	        while (index < formula.length() ) {
	            ch = formula.charAt(index);
	            if (!Character.isWhitespace(ch)) {
	                break;
	            }
	            index++;
	        }

	        if (index == formula.length() ) {
	            error = true;
	            break;
	        }

	        // ASSERT: ch now contains the first character of the next token.
	        if (operatorToken.isOperator(ch)) {
	            // We found an operator token
	            switch (ch) {
	                case OperatorToken.Plus:
	                case OperatorToken.Minus:
	                case OperatorToken.Mult:
	                case OperatorToken.Div:
	                case OperatorToken.LeftParen:
	                    // push operatorTokens onto the output stack until
	                    // we reach an operator on the operator stack that has
	                    // lower priority than the current one.
	                    OperatorToken stackOperator;
	                    while (!operatorStack.isEmpty()) {
	                        stackOperator = (OperatorToken) operatorStack.peek();
	                        if ((stackOperator.priority() >= operatorToken.operatorPriority(ch)) &&
	                            (stackOperator.getOperatorToken() != OperatorToken.LeftParen) ) {

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

	        } else if (ch == ')') {    // maybe define OperatorToken.RightParen ?
	            OperatorToken stackOperator;
	            stackOperator = (OperatorToken) operatorStack.pop();
	            // This code does not handle operatorStack underflow.
	            while (stackOperator.getOperatorToken() != OperatorToken.LeftParen) {
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
	            while (index < formula.length()) {
	                ch = formula.charAt(index);
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
	            cellToken = new CellToken();
	            index = cellToken.getCellToken(formula, index, cellToken);
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
	 * Returns the formula for this cell.
	 * @return Returns a string that represents this cell's formula.
	 */
	public String getFormula() {
		return formula;
	}
	
	/**
	 * Prints out a representation of this cell's internal status. 
	 * (Useful for debugging.)
	 * @return A string representation of the cell's internal status.
	 */
	public String toString() {
		// We can also print out the dependencies later if needed.
		return "[" +  "Value: " + value + "]";
	}
}
