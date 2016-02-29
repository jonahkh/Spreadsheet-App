package model;

public class OperatorToken implements Token{
	public static final char PLUS = '+';
	public static final char MINUS = '-';
	public static final char MULT = '*';
	public static final char DIV = '/';
	public static final char LT_PAREN = '(';
	private char operatorToken;

	public OperatorToken(final char theOperator) {
		operatorToken = theOperator;
	}
	
	public char getOperatorToken() {
		return operatorToken;
	}
	
	public Token evaluate() {
		return null;
	}
	
	/**
	 * Return true if the char ch is an operator of a formula.
	 * Current operators are: +, -, *, /, (.
	 * @param ch  a char
	 * @return  whether ch is an operator
	 */
	boolean isOperator (char ch) {
	    return ((ch == PLUS) ||
	            (ch == MINUS) ||
	            (ch == MULT) ||
	            (ch == DIV) ||
	            (ch == LT_PAREN) );
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
	            return 2;

	        default:
	            // This case should NEVER happen
	            System.out.println("Error in operatorPriority.");
	            System.exit(0);
	            break;
	    }
	    return -1;
	}

	/*
	 * Return the priority of this OperatorToken.
	 *
	 * priorities:
	 *   +, - : 0
	 *   *, / : 1
	 *   (    : 2
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
	        case LT_PAREN:
	            return 2;

	        default:
	            // This case should NEVER happen
	            System.out.println("Error in priority.");
	            System.exit(0);
	            break;
	    }
	    return -1;
	}
	
	public String toString() {
		return operatorToken + "";
	}
}
