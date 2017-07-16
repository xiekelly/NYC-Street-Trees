import java.util.ArrayList;

/**
 * This class inherits from the ArrayList<Tree> class and performs getter methods 
 * that retrieve specific information from the data file.
 * 
 * @author Kelly Xie (kyx203)
 */
public class TreeList extends ArrayList<Tree> {

	// default constructor that creates an empty list
	public TreeList() {
		new ArrayList<Tree>();
	}
	
	
	/**
	 * Returns the total number of Tree objects stored in this list.
	 * 
	 * @return integer representing the total number of trees
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int getTotalNumberOfTrees() {
		return size();
	}
	
	
	/**
	 * Returns the number of Tree objects in the list whose species 
	 * matches the speciesName specified by the parameter (case insensitive).
	 * 
	 * @param string 'speciesName' representing the tree species. It accepts only a string, and
	 * can be empty, but cannot be null.
	 * @return integer representing number of trees that contain the specified species name
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int getCountByTreeSpecies(String speciesName) { // includes all names that contain parameter as substring
		int countByTreeSpecies = 0;
		for (int i=0; i<size(); i++) {
			// make all names case insensitive
			if (get(i).getSpeciesName().toLowerCase().contains(speciesName.toLowerCase())) {
				countByTreeSpecies++;
			}
		}
		// if non-existent species, the return value will be 0
		return countByTreeSpecies;
	}
	
	
	/**
	 * Returns the number of Tree objects in the list that are located
	 * in the borough specified by the parameter (case insensitive).
	 * 
	 * @param string 'boroName' representing the borough's name. It accepts only a string with values:
	 * "Manhattan", "Brooklyn", "Bronx", "Queens", and "Staten Island".
	 * @return integer representing number of trees located in the specified borough
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int getCountByBorough(String boroName) {
		int countByBorough = 0;
		for (int i=0; i<size(); i++) {
			// make all names case insensitive
			if (get(i).getBoro().toLowerCase().contains(boroName.toLowerCase())) {
				countByBorough++;
			}
		}
		// if non-existent borough name, the return value will be 0
		return countByBorough; 
	}
	
	
	/**
	 * Returns the number of Tree objects in the list whose species matches
	 * the speciesName specified by the first parameter and which are located
	 * in the borough specified by the second parameter (case insensitive).
	 * 
	 * @param string 'speciesName' and string 'boroName' representing the tree species and borough 
	 * name, respectively. It accepts only strings of valid values.
	 * @return integer representing the number of trees of a certain species in a certain borough
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int getCountByTreeSpeciesBorough(String speciesName, String boroName) {
		int countByTreeSpeciesBorough = 0;
		for (int i=0; i<size(); i++) {
			// make all names case insensitive
			if (get(i).getSpeciesName().toLowerCase().contains(speciesName.toLowerCase()) &&
					get(i).getBoro().toLowerCase().contains(boroName.toLowerCase())) {
				countByTreeSpeciesBorough++;
			}
		}
		// if non-existent borough name or species, the return value will be 0
		return countByTreeSpeciesBorough;
	}
	
	
	/**
	 * Returns an ArrayList<String> object containing a list of all
	 * the actual tree species that match a given parameter string speciesName.
	 * The actual species matches speciesName if speciesName is a substring
	 * of the actual name (case insensitive).
	 * The list returned by this function does contain any duplicate names.
	 * 
	 * @param string 'speciesName' representing the tree species. It accepts only a string.
	 * @return ArrayList<String> of all tree species that at least partially 
	 * match the specified species name
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public ArrayList<String> getMatchingSpecies(String speciesName) {
		ArrayList<String> matchingSpecies = new ArrayList<String>(); // create new array list
		for (int i=0; i<size(); i++) {
			// make all names case insensitive
			// make sure to not add duplicate entries!
			if (get(i).getSpeciesName().toLowerCase().contains(speciesName.toLowerCase()) &&
					!(matchingSpecies.contains( get(i).getSpeciesName().toLowerCase() )) ) {
				// add all distinct matching species to list
				matchingSpecies.add(get(i).getSpeciesName());
			}
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
	public String toString() {
		return String.format("There are a total of %,d trees in NYC. Nice!", size());
	}

}
