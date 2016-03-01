package model;

import java.util.List;
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
//            System.out.println(t.token.toString());
        }
    }
    
    /**
     * Evaluates this tree and returns the result.
     * 
     * @return the result of the evaluated tree
     */
    public int evaluate() {
//    	System.out.println("After evaluate: " + evaluate(root));
//    	System.out.println("Root = " + root.toString());
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
            else if(t.left.token instanceof LiteralToken) {
                value1 = ((LiteralToken) t.left.token).getValue();
                System.out.println("Left: " + value1);
            } else if (t.left.token instanceof CellToken)
                value1 = Spreadsheet.CELLS[((CellToken) t.left.token).getRow()][((CellToken) t.left.token).getColumn()].getValue();
            //evaluate right subtree and store value as value2
            if (t.right.token instanceof OperatorToken)
                value2 = evaluate(t.right);
            else if(t.right.token instanceof LiteralToken) {
                value2 = ((LiteralToken) t.right.token).getValue();
                System.out.println("Right: " + value2);
            } else if (t.right.token instanceof CellToken)
                value2 = Spreadsheet.CELLS[((CellToken) t.right.token).getRow()][((CellToken) t.right.token).getColumn()].getValue();;
            //calculate total using the values from the two subtrees
                System.out.println("Token: " + t.token);
            switch(((OperatorToken) t.token).getOperatorToken()) {
                case OperatorToken.PLUS:
                    return value1 + value2;
                case OperatorToken.MINUS:
                    return value1 - value2;
                case OperatorToken.MULT: {
                	System.out.println("New Total" + total);
                    return value1 * value2;
                } case OperatorToken.DIV:
                    return value1 / value2;
            }
            System.out.println("Total: " + total);
        } else {// if (t != null){
        	if (t.token instanceof LiteralToken) {
        		return ((LiteralToken) t.token).getValue();
        	} else {
        		CellToken temp = ((CellToken) t.token);
        		return Spreadsheet.CELLS[temp.getRow()][temp.getColumn()].getValue();
        	}
        }
        return -1; // Means there's an issue
    }
    
 // Build an expression tree from a stack of ExpressionTreeTokens
    public void BuildExpressionTree (Stack<Token> s, final List<Cell> theDependants) {
    	root = getExpressionTree(s, theDependants);
    	if (!s.isEmpty()) {
    		System.out.println("Error in BuildExpressionTree.");
    	}
 	}

    private ExpressionTreeNode getExpressionTree(Stack<Token> s, final List<Cell> theDependants) {
    	ExpressionTreeNode returnTree;
    	Token token;
    	if (s.isEmpty()) {
    		return null;
    	}
    	token = s.pop(); // need to handle stack underflow
    	if ((token instanceof LiteralToken) || (token instanceof CellToken) ) {
    		// Literals and Cells are leaves in the expression tree
    		returnTree = new ExpressionTreeNode(token, null, null);
//    		theDependants.add(new CellToken())
    		return returnTree;
    	} else { // if (token instanceof OperatorToken) {
    		// Continue finding tokens that will form the
    		// right subtree and left subtree.
    		ExpressionTreeNode rightSubtree = getExpressionTree (s, theDependants);
    		ExpressionTreeNode leftSubtree = getExpressionTree (s, theDependants);
    		returnTree = new ExpressionTreeNode(token, leftSubtree, rightSubtree);
    		return returnTree;
    	}
     }
}
