package model;

public class ExpressionTree {
    
    /** The root of this ExpressionTree. */
    private ExpressionTreeNode root;
    
    public ExpressionTree() {
        root = null;
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
            System.out.println("Something");  //We can figure this out when we finish coding the tokens
        }
    }
    
    /**
     * Evaluates this tree in post-order traversal.
     */
    public void evaluate() {
        
    }
}
