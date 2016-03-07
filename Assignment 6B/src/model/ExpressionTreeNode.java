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
 * This class represents an individual Expression Tree Node.
 */
public class ExpressionTreeNode {

	/** The token for this node. */
	protected Token token;
	
	/** References the left node to this node. */
	protected ExpressionTreeNode left;
	
	/** References the right node to this node. */
	protected ExpressionTreeNode right;
	
	/**
	 * Initializes a new ExpressionTreeNode.
	 * 
	 * @param theToken 	the token for this node
	 * @param theLeft	the left node for this node
	 * @param theRight	the right node for this node
	 */
	public ExpressionTreeNode(final Token theToken, final ExpressionTreeNode theLeft, 
			final ExpressionTreeNode theRight) {
		token = theToken;
		left = theLeft;
		right = theRight;
	}
	
	/**
	 * Initializes a new ExpressionTreeNode.
	 * 
	 * @param theToken the token for this node
	 */
	public ExpressionTreeNode(final Token theToken) {
		this(theToken, null, null);
	}
	
	@Override
	public String toString() {
		return token.toString();
	}
}
