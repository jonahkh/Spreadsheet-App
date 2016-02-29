package model;

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
}
