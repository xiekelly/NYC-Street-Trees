/**
 * This class creates a Tree object that stores information 
 * about a particular tree in New York City and creates getter methods
 * for accessing this data. It overrides the equals and compareTo methods
 * in the Comparable interface. 
 * 
 * @author Kelly Xie (kyx203)
 */

public class Tree implements Comparable<Tree> {
	
	// data fields storing information from dataset
	private int tree_id; // non-negative int
	private int tree_dbh; // non-negative int
	private String status; // valid values: "Alive", "Dead", "Stump", or empty string or null
	private String health; // valid values: "Good", "Fair", "Poor", or empty string or null
	private String spc_common; // possibly empty, string, cannot be null
	private int zip; // positive 5 digit int
	private String boroname; // valid values: "Manhattan", "Bronx", "Brooklyn", "Queens", "Staten Island"
	private double x_sp;
	private double y_sp;

	
	// create a 9 parameter constructor
	public Tree ( int id, int diam, String status, String health, String spc,
	           int zip, String boro, double x, double y ) throws IllegalArgumentException {
		
		// handle invalid arguments for all data fields
		try {
			if (id > 0)
				tree_id = id;
			else
				throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e) {
			System.err.println("Invalid argument for tree id!");
		}
		
		try {
			if (diam >= 0)
				tree_dbh = diam;
			else
				throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e) {
			System.err.println("Invalid argument for tree diameter!");
		}
		
		try {
			if ( status.equalsIgnoreCase("Alive") ||
					status.equalsIgnoreCase("Dead") ||
					status.equalsIgnoreCase("Stump") ||
					status.equals("") || status == null )
				this.status = status;
			else
				throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e) {
			System.err.println("Invalid argument for status!");
		}
		
		try {
			if ( health.equalsIgnoreCase("Good") ||
					health.equalsIgnoreCase("Fair") ||
					health.equalsIgnoreCase("Poor") ||
					health.equals("") || health == null )
				this.health = health;
			else
				throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e) {
			System.err.println("Invalid argument for health!");
		}
			
		try {
			if (!(spc == null))
				spc_common = spc;
			else
				throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e) {
			System.err.println("Invalid argument for species name!");
		}
			
		try {
			if (zip >= 0 && zip <= 99999)
				this.zip = zip;
			else
				throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e) {
			System.err.println("Invalid argument for zip code!");
		}
			
		try {
			if ( boro.equalsIgnoreCase("Manhattan") ||
					boro.equalsIgnoreCase("Bronx") ||
					boro.equalsIgnoreCase("Brooklyn") ||
					boro.equalsIgnoreCase("Queens") ||
					boro.equalsIgnoreCase("Staten Island") )
				boroname = boro;
			else
				throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e) {
			System.err.println("Invalid argument for borough name!");
		}
	}
	
	
	/**
	 * Accessor methods for retrieving all data fields specified above.
	 * 
	 * @return int for tree id, diameter and integer;
	 * double for x and y coordinates;
	 * string for status, health, and borough of the tree
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int getId() {
		return tree_id;
	}
	public int getDiam() {
		return tree_dbh;
	}
	public String getStatus() {
		return this.status;
	}
	public String getHealth() {
		return this.health;
	}
	public String getSpeciesName() {
		return spc_common;
	}
	public int getZip() {
		// format the string to display 5 place values
		String str = String.format("%05d", this.zip);
		int zipCode = Integer.parseInt(str);
		return zipCode;
	}
	public String getBoro() {
		return boroname;
	}
	public double getX() {
		return x_sp;
	}
	public double getY() {
		return y_sp;
	}
	
	
	/**
	 * Overrides the Comparable interface's equals method. Two trees are equal if their
	 * id and species name are the same. Method is case insensitive. 
	 * If two trees have the same id but different species name, an exception is thrown
	 * since tree id's are unique.
	 * 
	 * 
	 * @param Object to be compared to
	 * @return true if trees have identical id and species name, else return false
	 * @throws IllegalArgumentException occurs when the two trees have identical id's
	 * but different species names.
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	@Override
	public boolean equals(Object o) throws IllegalArgumentException {
		try {
			// identical id and species name
			if ( this.tree_id == ((Tree) o).tree_id &&
					this.spc_common.equalsIgnoreCase(((Tree) o).spc_common) ) {
				return true;
			}
			// identical id but different species name
			else if ( this.tree_id == ((Tree) o).tree_id &&
					!( this.spc_common.equalsIgnoreCase(((Tree) o).spc_common) ) ) {
				throw new IllegalArgumentException();
			}
			// different id or different species name
			else {
				return false;
			}
		}
		catch (IllegalArgumentException e) {
			System.err.print("Not possible to have two trees with identical id's "
					+ "and different species names.");
			return false;
		}
	}
	
	
	/**
	 * Overrides the toString method to display all information about
	 * a particular tree.
	 * 
	 * @return a string representation of a tree
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	@Override
	public String toString() {
		// capitalize the following data fields
		String capName = spc_common.substring(0, 1).toUpperCase()
				+ spc_common.substring(1).toLowerCase();
		String capStatus = status.substring(0, 1).toUpperCase()
				+ status.substring(1).toLowerCase();
		String capHealth = health.substring(0, 1).toUpperCase()
				+ health.substring(1).toLowerCase();
		String capBoro = boroname.substring(0, 1).toUpperCase()
				+ boroname.substring(1).toLowerCase();
		
		return (capName + " has id \'" + tree_id + "\', diameter of " + tree_dbh 
				+ ", status \'" + capStatus + "\', health \'" + capHealth 
				+ "\', zipcode " + zip + ", and is located in " + capBoro + ".");
	}

	
	/**
	 * Overrides the Comparable interface's compareTo method using species name
	 * as the primary key, and tree id as the secondary key. Method is case insensitive.
	 * 
	 * @param Tree object representing the tree being compared
	 * @return 0 if the trees have the same species name and tree id; 
	 * 1 if the calling tree appears before parameter tree (alphabetically or by id); 
	 * -1 if the calling tree appears after parameter tree (alphabetically or by id)
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	@Override
	public int compareTo(Tree o) {
		
		// same species name and tree id
		if (this.equals(o)) { // calls overridden equals method of Tree class
			return 0;
		}
		// same species name, different tree id
		// use secondary key to compare
		else if ( this.tree_id != ((Tree) o).tree_id &&
				this.spc_common.equalsIgnoreCase(((Tree) o).spc_common) ) {
			if ( this.tree_id > ((Tree) o).tree_id )
				return 1;
			else
				return -1;
		}
		// different species name
		// use primary key to compare
		else {
			if ( ((this.spc_common).compareToIgnoreCase( ((Tree)o).spc_common) ) > 0 ) // alphabetical order
				return 1;
			else
				return -1;
		}
	}
	
	/**
	 * Method for comparing species names of 2 trees. Method should not
	 * take tree id's into consideration. It is case insensitive. 
	 * 
	 * @param Tree object t
	 * @return true if this tree and object t have the same species name;
	 * false if they do not
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public boolean sameName(Tree t) {
		if ( (this.spc_common).equalsIgnoreCase(t.spc_common) )
			return true;
		else
			return false;
	}
	
	/**
	 * Method for comparing species names of 2 trees. Method does not take
	 * tree id's into consideration. It is case insensitive.
	 * 
	 * @param Tree object t
	 * @return 0 if this tree and t have the same species name;
	 * a negative value if this tree's species name is smaller than t's species name;
	 * a positive value if this tree's species name is larger than t's species name
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public int compareName(Tree t) {
		if ( (this.spc_common).compareToIgnoreCase(t.spc_common) < 0 )
			return -1;
		else if ( (this.spc_common).compareToIgnoreCase(t.spc_common) > 0 )
			return 1;
		else
			return 0;
	}

}
