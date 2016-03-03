package model;

/**
 * This class represents an individual Literal Token.
 * 
 * @author Jonah Howard
 * @version 28 Feb 2016
 */
public class LiteralToken implements Token {
	
	/** The value of this Literal Token. */
	private int myValue;
	
	/**
	 * Initialize a new Literal Token.
	 * 
	 * @param literal the value for this Token
	 */
	public LiteralToken(final int literal) {
		myValue = literal;
	}
	
	/**
	 * Set the value of this token to the passed myValue.
	 * 
	 * @param theValue The myValue to set this token to
	 */
	public void setValue(final int theValue) {
		myValue = theValue;
	}
	
	/**
	 * Returns the value associated with this Literal Token.
	 * 
	 * @return the value associated with this Literal Token
	 */
	public int getValue() {
		return myValue;
	}
	
	@Override
	public String toString() {
		return Integer.toString(myValue);
	}
}
