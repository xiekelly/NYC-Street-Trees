import java.util.NoSuchElementException;

/**
 * This class provides the implementation for a
 * binary search tree.
 * 
 * @author Joanna Klukowska lecture notes
 * @author Kelly Xie (kyx203)
 */
public class MyBST<E extends Comparable<E>> {

	// protected access data fields
	protected BSTNode<E> root;
	protected int size;
	
	/** 
	 * A default constructor that sets data fields to null.
	 */
	public MyBST() {
		root = null;
	}
	
	/** 
	 * Methods for adding data as a node object to the tree.
	 * It does not allow duplicate entries. 
	 * Helper method is implemented recursively.
	 * 
	 * @param Generic datatype E that represents data to be added to tree node
	 * @return true if the data was added successfully; false otherwise
	 * @throws ClassCastException; NullPointerException
	 */
	public boolean add(E e) throws NullPointerException { // public wrapper method        
        try {
        	if (e == null)
        		throw new NullPointerException();
        }
        catch (NullPointerException ex) {
        	System.err.print("NullPointerException thrown.");
        }
        
		if (root != null && contains(e)) {
			return false; // duplicate tree, not stored in collection
		}
		else {
			root = add(root, e);
			size++; // increment size
			return true;
		}
	}
	
    private BSTNode<E> add(BSTNode<E> current, E newData) 
    		throws ClassCastException, NullPointerException{ // private helper method
    	if (current == null) {
    		return (new BSTNode<E>(newData));
    	}
    	else if (newData.compareTo(current.getData()) < 0) { // left subtree
    		current.setLeft( add(current.getLeft(), newData) );
        }
        else if (newData.compareTo(current.getData()) > 0) { // right subtree
        	current.setRight( add(current.getRight(), newData) );
        }
        return current;
    }
	
	/** 
	 * Methods for removing node object from the tree using the
	 * predecessor method. Helper method is implemented recursively.
	 * 
	 * @param Object o that represents node to be removed from tree
	 * @return true if the element was removed successfully; false otherwise
	 * @throws ClassCastException; NullPointerException
	 */
	public boolean remove(Object o) throws Exception { // public wrapper method
		try {
			if (o == null)
				throw new NullPointerException();
		}
		catch (NullPointerException e) {
			System.err.print("NullPointerException thrown.");
		}
		
		if (root != null && !contains(o)) {
			return false; // object not in tree, no removal
		}
		else {
			root = recRemove(root, o);
			size--;
			return true;
		}
	}
	
	// recursive removal method
	@SuppressWarnings("unchecked")
	private BSTNode<E> recRemove(BSTNode<E> node, Object o) 
			throws ClassCastException, NullPointerException, Exception { // private helper method
		if (node == null) {
			// do nothing, the item is not in the tree
		}
		else if (((Comparable<E>) o).compareTo(node.getData()) < 0)
			node.setLeft( recRemove(node.getLeft(), o) );  // search in the left subtree
		else if (((Comparable<E>) o).compareTo(node.getData()) > 0)
			node.setRight( recRemove(node.getRight(), o) ); // search in the right subtree
		else // found node
			// remove the data stored in the node
			node = remove(node);
		return node;
	}
	
	// method that handles the actual removal of node
	private BSTNode<E> remove(BSTNode<E> node) throws Exception {
		// case of zero or one child
		if (node.getLeft() == null)
			return node.getRight();
		if (node.getRight() == null)
			return node.getLeft();
		// case of two children
		E data = getPredecessor(node);
		node.setData(data);
		node.setLeft( recRemove(node.getLeft(), data) );
		return node;
	}		

	// finding predecessor of a given node
	// aka rightmost node in the node's left subtree
    private E getPredecessor(BSTNode<E> node) throws Exception {
    	if (node.getLeft() == null)
    		throw new Exception();
    	else {	
    		BSTNode<E> current = node.getLeft();
    		while (current.getRight() != null)
    			current = current.getRight();
    		return current.getData();
    	}
    }
    	    	   
	/** 
	 * Methods for determining whether tree contains the specified node object.
	 * Helper method is implemented recursively.
	 * 
	 * @param Object o that represents node to be found
	 * @return true if the tree contains the node object; false otherwise
	 * @throws ClassCastException; NullPointerException
	 */
	public boolean contains(Object o) { // public wrapper method
		return contains(root, o);
	}
	
	@SuppressWarnings("unchecked")
	private boolean contains(BSTNode<E> node, Object o) 
			throws ClassCastException, NullPointerException { // private helper method
        if (node == null)
            return false;
        else if (((Comparable<E>) o).compareTo(node.getData()) < 0)
            return contains(node.getLeft(), o); // recursive call
        else if (((Comparable<E>) o).compareTo(node.getData()) > 0)
            return contains(node.getRight(), o); // recursive call
        else // compareTo returns 0: found a match
            return true;
	}
	
	/**
	 * Method that returns the first or lowest element.
	 * 
	 * @return Generic element E
	 * @throws NoSuchElementException if this set is empty
	 */
	public E first() throws NoSuchElementException {
		BSTNode<E> current = root;
		try {
			if( current == null )
				throw new NoSuchElementException();
			else {
	            while( current.getLeft() != null )
	                current = current.getLeft();
			}
		}
		catch (NoSuchElementException e) {
			System.err.print("NoSuchElementException thrown.");
		}
        return current.getData();
	}
	
	/**
	 * Method that returns the last or highest element.
	 * 
	 * @return Generic element E
	 * @throws NoSuchElementException if this set is empty
	 */
	public E last() throws NoSuchElementException {
		BSTNode<E> current = root;
		try {
			if( current == null )
				throw new NoSuchElementException();
			else {
	            while( current.getRight() != null )
	                current = current.getRight();
			}
		}
		catch (NoSuchElementException e) {
			System.err.print("NoSuchElementException thrown.");
		}
        return current.getData();
	}
	
	/**
	 * Method that overrides the toString method and returns
	 * a String representation of the binary search tree.
	 * Uses in-order traversal.
	 * 
	 * @return String representation of the tree
	 */
	public String toString() {
		return inOrderTraversal(root);
	}
	private String inOrderTraversal(BSTNode<E> node) { // helper method
		String str = new String();
		if (node != null) {
			inOrderTraversal(node.getLeft());
			str = str + ", " + node.getData();
			inOrderTraversal(node.getRight());
		}
		return str;
	}

}
