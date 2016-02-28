package model;

public class LiteralToken implements Token {
	/** The value of this Literal Token. */
	private int value;
	
	/**
	 * Initialize a new Literal Token.
	 * 
	 * @param literal the value for this Token
	 */
	public LiteralToken(final int literal) {
		value = literal;
	}
	
	/**
	 * Set the value of this token to the passed value.
	 * 
	 * @param theValue The value to set this token to
	 */
	public void setValue(final int theValue) {
		value = theValue;
	}
	
	/**
	 * Returns the value associated with this Literal Token.
	 * 
	 * @return the value associated with this Literal Token
	 */
	public int getValue() {
		return value;
	}
}
