/*
 * Lisa Taylor
 * Jonah Howard
 * Henry Lai
 * John Bui
 * 
 * TCSS 342 - Spring 2016
 * Assignment 6B
 */

package model;

/**
 * This class represents an individual Operator Token. 
 * 
 * @author Jonah Howard
 * @author Lisa Taylor
 * @version 3 March 2016
 */
public class OperatorToken extends Token{

    /** Represents an addition operator. */
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

    /**
     * Initialize a new Operator Token for char passed as argument.
     * 
     * @param theOperator the current operator
     */
    public OperatorToken(final char theOperator) { 
        this(theOperator + "");
    }
    
    /**
    * Initialize a new Operator Token for string passed as argument.
    * 
    * @param theOperator the current operator
    */
    public OperatorToken(final String theOperator) {
        if (theOperator != null)
            setToken(theOperator);
    }
    
    /**
     * Return this operator token.
     * 
     * @return this operator token
     */
    public char getOperatorToken() {
        return getToken().charAt(0);
    }
    
    /**
     * Return true if the char ch is an operator of a formula.
     * Current operators are: +, -, *, /, (, ^.
     * 
     * @param ch  a char
     * @return  whether ch is an operator
     */
    public boolean isValidOperator (char ch) {
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
        switch (getToken().charAt(0)) {
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
        if (!isValidOperator(ch)) {
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
     *   ^    : 2
     *   (    : 3
     *
     * @return  the priority of operatorToken
     */
    public int priority () {
        switch (this.getToken().charAt(0)) {
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
}
