package model;

public class OperatorToken implements Token{
	public static final char Plus = '+';
	public static final char Minus = '-';
	public static final char Mult = '*';
	public static final char Div = '/';
	public static final char LeftParen = '(';
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
	    return ((ch == Plus) ||
	            (ch == Minus) ||
	            (ch == Mult) ||
	            (ch == Div) ||
	            (ch == LeftParen) );
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
	        case Plus:
	            return 0;
	        case Minus:
	            return 0;
	        case Mult:
	            return 1;
	        case Div:
	            return 1;
	        case LeftParen:
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
	        case Plus:
	            return 0;
	        case Minus:
	            return 0;
	        case Mult:
	            return 1;
	        case Div:
	            return 1;
	        case LeftParen:
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
