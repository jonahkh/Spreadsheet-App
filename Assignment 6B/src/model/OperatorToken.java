/* Bui, John
 * Howard, Jonah
 * Lai, Henry
 * Taylor, Lisa
 * TCSS 342 - Data Structures
 * Professor Donald Chinn
 * Homework 6B
 * March 8, 2016
 */

package model;

/**
 * This class represents an individual Operator Token. 
 */
public class OperatorToken extends Token{

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
}
