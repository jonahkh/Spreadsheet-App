package model;

import java.util.Stack;

/**
 * This class represents an expression tree for calculating formulas
 * entered into a cell.
 * 
 * @author Lisa Taylor
 */
public class ExpressionTree {
    
    /** The root of this ExpressionTree. */
    protected ExpressionTreeNode root;
    
    /** Constructor to initialize root to null. */
    public ExpressionTree() {
        this(null);
    }
    
    public ExpressionTree(final ExpressionTreeNode theNode) {
    	root = theNode;
    }
    
    /**
     * Removes all nodes from this tree.
     */
    public void makeEmpty() {
        root = null;
    }
    
    /**
     * Prints this tree in post-order traversal.
     */
    public void printTree() {
        if(root == null)
            System.out.println( "Empty tree" );
        else 
            printTree(root);
    }
    
    /**
     * Internal method that prints this tree in post-order traversal.
     * 
     * @param t the node that roots the (sub)tree
     */
    public void printTree(ExpressionTreeNode t) {
        if(t != null) {
            printTree(t.left);
            printTree(t.right);
            System.out.println(t.token.toString());
        }
    }
    
    /**
     * Evaluates this tree and returns the result.
     * 
     * @return the result of the evaluated tree
     */
    public int evaluate() {
    	System.out.println("After evaluate: " + evaluate(root));
    	return evaluate(root);
    }
    
    /**
     * Evaluates this tree in post-order traversal.
     * 
     * @param t the node that roots the (sub)tree
     * @param value the 
     */
    private int evaluate(ExpressionTreeNode t) {
        int total = 0;
        int value1 = 0;
        int value2 = 0;
        
        if (t != null && t.token instanceof OperatorToken) {
            //evaluate left subtree and store value as value1
            if (t.left.token instanceof OperatorToken)
                value1 = evaluate(t.left);
            else if(t.left.token instanceof LiteralToken) 
                value1 = ((LiteralToken) t.left.token).getValue();
            else if (t.left.token instanceof CellToken)
                value1 = Spreadsheet.CELLS[((CellToken) t.left.token).getRow()][((CellToken) t.left.token).getColumn()].getValue();
            //evaluate right subtree and store value as value2
            if (t.right.token instanceof OperatorToken)
                value2 = evaluate(t.right);
            else if(t.right.token instanceof LiteralToken)
                value2 = ((LiteralToken) t.right.token).getValue();
            else if (t.right.token instanceof CellToken)
                value2 = Spreadsheet.CELLS[((CellToken) t.right.token).getRow()][((CellToken) t.right.token).getColumn()].getValue();;
            //calculate total using the values from the two subtrees
            switch(((OperatorToken) t.token).getOperatorToken()) {
                case OperatorToken.PLUS:
                    total = value1 + value2;
                case OperatorToken.MINUS:
                    total = value1 - value2;
                case OperatorToken.MULT:
                    total = value1 * value2;
                case OperatorToken.DIV:
                    total = value1 / value2;
            }
        }
        return total;
    }
    
 // Build an expression tree from a stack of ExpressionTreeTokens
    public void BuildExpressionTree (Stack<Token> s) {
    	root = getExpressionTree(s);
    	if (!s.isEmpty()) {
    		System.out.println("Error in BuildExpressionTree.");
    	}
 	}

    private ExpressionTreeNode getExpressionTree(Stack<Token> s) {
    	ExpressionTreeNode returnTree;
    	Token token;
    	if (s.isEmpty())
    		return null;
    	token = s.pop(); // need to handle stack underflow
    	if ((token instanceof LiteralToken) || (token instanceof CellToken) ) {
    		// Literals and Cells are leaves in the expression tree
    		returnTree = new ExpressionTreeNode(token, null, null);
    		return returnTree;
    	} else { // if (token instanceof OperatorToken) {
    		// Continue finding tokens that will form the
    		// right subtree and left subtree.
    		ExpressionTreeNode rightSubtree = getExpressionTree (s);
    		ExpressionTreeNode leftSubtree = getExpressionTree (s);
    		returnTree = new ExpressionTreeNode(token, leftSubtree, rightSubtree);
    		return returnTree;
    	}
     }
}
