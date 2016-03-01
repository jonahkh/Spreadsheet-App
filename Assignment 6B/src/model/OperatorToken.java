package model;

/**
 * This class represents an individual Operator Token. 
 * 
 * @author Jonah Howard
 * @version 28 Feb 2016
 */
public class OperatorToken implements Token{
	
	/** Represetns an addition operator. */
	public static final char PLUS = '+';
	
	/** Represents a subtraction operator. */
	public static final char MINUS = '-';
	
	/** Represents a multiplication operator. */
	public static final char MULT = '*';
	
	/** Represents a division operator. */
	public static final char DIV = '/';
	
	/** Represents a parentheses operator. */
	public static final char LT_PAREN = '(';
	
	/** Represents a power operator. */
	public static final char POW = '^';
	
	/** Represents this Token's operator. */
	private char operatorToken;

	/**
	 * Initialize a new OperatorToken.
	 * 
	 * @param theOperator the current operator
	 */
	public OperatorToken(final char theOperator) {
		operatorToken = theOperator;
	}
	
	/**
	 * Return this operator token.
	 * 
	 * @return this operator token
	 */
	public char getOperatorToken() {
		return operatorToken;
	}
	
	/**
	 * Return true if the char ch is an operator of a formula.
	 * Current operators are: +, -, *, /, (.
	 * @param ch  a char
	 * @return  whether ch is an operator
	 */
	public boolean isOperator (char ch) {
	    return ((ch == PLUS) ||
	            (ch == MINUS) ||
	            (ch == MULT) ||
	            (ch == DIV) ||
	            (ch == LT_PAREN) ||
	            (ch == POW));
	}
	
	/**
	 * Evaluates the two passed values based on the current operator.
	 * 
	 * @param value1 the first value to be considered
	 * @param value2 the second value to be considered
	 * @return the result based on the current operator
	 */
	public int evaluate(final int value1, final int value2) {
		int result = 0;
		switch (operatorToken) {
			case PLUS: {
				result = value1 + value2;
				break;
			} case MINUS: {
				result = value1 - value2;
				break;
			} case MULT: {
				result = value1 * value2;
				break;
			} case DIV: {
				result = value1 / value2;
				break;
			} case POW: {
				result = (int) Math.pow(value1, value2);
				break;
			}
		}
		return result;
	}
	
	/**
	 * Given an operator, return its priority.
	 *
	 * priorities:
	 *   +, - : 0
	 *   *, / : 1
	 *   (    : 2
	 *
	 * @param ch  a char
	 * @return  the priority of the operator
	 */
	int operatorPriority (char ch) {
	    if (!isOperator(ch)) {
	        // This case should NEVER happen
	        System.out.println("Error in operatorPriority.");
	        System.exit(0);
	    }
	    switch (ch) {
	        case PLUS:
	            return 0;
	        case MINUS:
	            return 0;
	        case MULT:
	            return 1;
	        case DIV:
	            return 1;
	        case LT_PAREN:
	            return 3;
	        case POW:
        		return 2;

	        default:
	            // This case should NEVER happen
	            System.out.println("Error in operatorPriority.");
	            System.exit(0);
	            break;
	    }
	    return -1;
	}

	/**
	 * Return the priority of this OperatorToken.
	 *
	 * priorities:
	 *   +, - : 0
	 *   *, / : 1
	 *   ^	  : 2
	 *   (    : 3
	 *
	 * @return  the priority of operatorToken
	 */
	public int priority () {
	    switch (this.operatorToken) {
	        case PLUS:
	            return 0;
	        case MINUS:
	            return 0;
	        case MULT:
	            return 1;
	        case DIV:
	            return 1;
	        case POW:
	            return 2;
	        case LT_PAREN:
	        	return 3;

	        default:
	            // This case should NEVER happen
	            System.out.println("Error in priority.");
	            System.exit(0);
	            break;
	    }
	    return -1;
	}
	
	@Override
	public String toString() {
		return operatorToken + "";
	}
}
