import java.util.ArrayList;
import java.util.Collection;

/**
 * This class inherits from the MyBST<Tree> class 
 * and implements getter methods that retrieve specific information from 
 * the data file. It is used to store all Tree objects.
 * 
 * @author Kelly Xie (kyx203)
 */
public class TreeCollection extends MyBST<Tree> {
	
	// private data fields
	private String[] boro = {"manhattan", "brooklyn", "bronx", "queens", "staten island"};
	private int[] boroCount = {0, 0, 0, 0, 0};
	private ArrayList<String> uniqueSpecies = new ArrayList<String>();
	private Collection<String> matchingSpecies;
	
	// default constructor that creates an empty tree
	public TreeCollection() {
		super();
	}
	
	
	/** 
	 * Overrides the add method from MyBST class.
	 * Helper method is implemented recursively.
	 * 
	 * @param Tree object t
	 * @return true if tree was successfully added; false otherwise
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	@Override
	public boolean add(Tree t) {
		if (t == null)
        	throw new NullPointerException();
		if (root != null && contains(t)) {
			return false; // duplicate tree, not stored in collection
		}
		else {
			for (int i=0; i<boro.length; i++) {
				if ((t.getBoro()).equalsIgnoreCase(boro[i]))
					boroCount[i]++; // increment corresponding borough in list
			}
			if (!uniqueSpecies.contains( t.getSpeciesName() )) {
					uniqueSpecies.add( t.getSpeciesName() ); // add all unique species to list
			}
			root = add(root, t);
			size++; // increment size
			return true;
		}
	}
	
    private BSTNode<Tree> add(BSTNode<Tree> current, Tree data) 
    		throws ClassCastException, NullPointerException { // private helper method
    	if (current == null) {
    		return (new BSTNode<Tree>(data));
    	}
    	else if (data.compareTo(current.getData()) < 0) {
    		current.setLeft( add(current.getLeft(), data) );
        }
        else if (data.compareTo(current.getData()) > 0) {
        	current.setRight( add(current.getRight(), data) );
        }
        return current;
    }
	
    
	/**
	 * Returns the total number of Tree objects stored in this list.
	 * Efficient implementation of this method is O(1).
	 * 
	 * @return integer representing the total number of trees
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int getTotalNumberOfTrees() {
		return size;
	}
	
	
	/**
	 * Returns the number of Tree objects in the list whose species 
	 * matches the speciesName specified by the parameter. 
	 * Method is case insensitive. Efficient implementation only traverses 
	 * the smallest number of branches required to discover all trees with 
	 * the matching species.
	 * 
	 * @param string 'speciesName' representing the tree species. It accepts only a string, 
	 * and can be empty, but cannot be null.
	 * @return integer representing number of trees that contain the specified species name
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int getCountByTreeSpecies(String speciesName) { // includes all names that contain parameter as substring
		int countByTreeSpecies = 0;
		//ArrayList<String> matching = (ArrayList<String>)getMatchingSpecies(speciesName);
		ArrayList<Tree> tempTreeList = new ArrayList<Tree>();
		for (String name : matchingSpecies) {
			Tree tempTree = new Tree(1, 0, "", "", name, 0, "Manhattan", 0, 0);
			tempTreeList.add(tempTree);
		}
		for (Tree tempTree : tempTreeList) {
			BSTNode<Tree> current = root;
			countByTreeSpecies += count(current, tempTree);
		}
		// if non-existent species, the return value will be 0
		return countByTreeSpecies;
	}
	
	private int count(BSTNode<Tree> current, Tree tempTree) { // helper method
		  // base case: does not exist
		  if (current == null) 
			  return 0;
		  else if (current.getData().sameName(tempTree))
			  return ( 1 + count(current.getLeft(), tempTree) + 
					  count(current.getRight(), tempTree) );
		  else
			  return ( count(current.getLeft(), tempTree) + 
					  count(current.getRight(), tempTree) );
		}
	
	
	/**
	 * Returns the number of Tree objects in the list that are located
	 * in the borough specified by the parameter. Method is case insensitive.
	 * Efficient implementation of this method is O(B), where B is the number of boroughs.
	 * 
	 * @param string 'boroName' representing the borough's name. It accepts only a string 
	 * with values: "Manhattan", "Brooklyn", "Bronx", "Queens", and "Staten Island".
	 * @return integer representing number of trees located in the specified borough
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int getCountByBorough(String boroName) {
		int countByBorough = 0;
		for (int i=0; i<boro.length; i++) {
			if ( boro[i].equalsIgnoreCase(boroName) )
				countByBorough = boroCount[i];
		}
		// if non-existent borough name, the return value will be 0
		return countByBorough;
	}
	
	
	/**
	 * Returns the number of Tree objects in the list whose species matches
	 * the speciesName specified by the first parameter and which are located
	 * in the borough specified by the second parameter. Method is case insensitive.
	 * Efficient implementation of this method only traverses the smallest number 
	 * of the tree branches required to discover all trees with the matching species.
	 * 
	 * @param string 'speciesName' and string 'boroName' representing the tree species and borough 
	 * name, respectively. It accepts only strings of valid values.
	 * @return integer representing the number of trees of specified species in specified borough
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int getCountByTreeSpeciesBorough(String speciesName, String boroName) {
		int countByTreeSpeciesBoro = 0;
		ArrayList<Tree> tempTreeList = new ArrayList<Tree>();
		for (String name : matchingSpecies) {
			Tree tempTree = new Tree(1, 0, "", "", name, 0, boroName, 0, 0);
			tempTreeList.add(tempTree);
		}
		for (Tree tempTree : tempTreeList) {
			BSTNode<Tree> current = root;
			countByTreeSpeciesBoro += count2(current, tempTree, boroName);
		}
		// if non-existent species, the return value will be 0
		return countByTreeSpeciesBoro;
	}
	
	private int count2(BSTNode<Tree> current, Tree tempTree, String boroName) { // helper method
		// base case: does not exist 
		if (current == null) 
			  return 0;
		  else if ((current.getData().sameName(tempTree)) &
				  (current.getData().getBoro().toLowerCase().equals(boroName)) )
			  return ( 1 + count2(current.getLeft(), tempTree, boroName) + 
					  count2(current.getRight(), tempTree, boroName) );
		  else
			  return ( count2(current.getLeft(), tempTree, boroName) + 
					  count2(current.getRight(), tempTree, boroName) );
		}
	
	
	/**
	 * Returns a list of all the actual tree species that match a given parameter 
	 * speciesName. The actual species matches speciesName if speciesName is a 
	 * substring of the actual name. Method is case insensitive.
	 * The list returned by this function does contain any duplicate names.
	 * Efficient implementation of this method is O(S), where S is the number of 
	 * unique tree species.
	 * 
	 * @param string speciesName representing the tree species
	 * @return Collection<String> of all tree species that at least partially 
	 * match the specified species name
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public Collection<String> getMatchingSpecies(String speciesName) {
		matchingSpecies = new ArrayList<String>(); // create new list
		for (int i=0; i<uniqueSpecies.size(); i++) {
			if ((uniqueSpecies.get(i)).contains(speciesName))
				matchingSpecies.add(uniqueSpecies.get(i));
		}
		return matchingSpecies;
	}
	
	
	/**
	 * Overrides the toString method to display the total number of trees
	 * stored in this list as a string.
	 * 
	 * @return a string that states how many trees there are across all boroughs
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	@Override
	public String toString() {
		return String.format("There are a total of %,d trees in NYC. Nice!", size);
	}

}
