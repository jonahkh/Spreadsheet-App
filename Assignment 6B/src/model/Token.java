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
 * This interface represents a Token. It allows for a generic instantiation of a Token.
 */
public abstract class Token {

	/** Represents the current token. */
    private String token = null;

    /**
     * Returns the current token.
     * 
     * @return the current token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets this token to the passed value.
     * 
     * @param token the new value for this token
     */
    public void setToken(final String token) {
        this.token = token;
    }
    
    @Override
    public String toString() {
        return token;
    }
}

