
/** 
 * This class implements a generic node class for
 * a binary search tree. It stores data and references
 * to its children for every node in the tree.
 * It implements the Comparable class.
 * 
 * @author Joanna Klukowska lecture notes
 * @author Kelly Xie (kyx203)
 */
public class BSTNode<E extends Comparable<E>>
	implements Comparable<BSTNode<E>>{
	
	// all private data fields	
	 private E data;
     private BSTNode<E> left;
     private BSTNode<E> right;
     
     /**
      * One parameter constructor for a node.
      * @param data to be stored in the node
      */
     public BSTNode(E data) {
         this.data = data;
         // set references to null
         this.left = null;
         this.right = null;
     }
     
     /**
      * Getter method for data.
      * @return data stored in this node
      */
     public E getData() {
    	 return data;
     }
     
     /**
      * Getter method for left subtree.
      * @return reference to this node's left subtree
      */
     public BSTNode<E> getLeft() {
    	 return left;
     }
     
     /**
      * Getter method for right subtree.
      * @return reference to this node's right subtree
      */
     public BSTNode<E> getRight() {
    	 return right;
     }
     
     /**
      * Setter method for this node's data.
      * @param new data to be stored in node
      */
     public void setData(E data) {
    	 this.data = data;
     }
     
     /**
      * Setter method for left subtree.
      * @param new left subtree reference
      */
     public void setLeft(BSTNode<E> left) {
    	 this.left = left;
     }
     
     /**
      * Setter method for right subtree.
      * @param new right subtree reference
      */
     public void setRight(BSTNode<E> right) {
    	 this.right = right;
     }
     
     /** 
      * Implements compareTo method in the Comparable interface.
      * 
      * @param another node to be compared to
      * @return 0 if the data in the nodes are equal;
      * positive int if this node's data is greater than the parameter node's;
      * negative int if this node's data is less than the parameter node's
      */
     @Override
     public int compareTo(BSTNode<E> other) {
         return this.data.compareTo(other.data);
     }
     
     /** 
      * Overrides toString method to display data in
      * the current node.
      * 
      * @return String representation of the data in this node
      */
     @Override
     public String toString() {
         return ("The current node is: " + data.toString());
     }
	
}