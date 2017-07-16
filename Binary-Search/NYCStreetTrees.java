import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;


public class NYCStreetTrees {
	
	/**
	 * This class handles File input and output, opens and reads data, 
	 * and processes user input. It also handles any exceptions raised by the program.
	 * 
	 * The program can be run from the command line in terminal OR
	 * by setting an argument (file path) in Run Configurations in Eclipse.
	 * 
	 * @throws FileNotFoundException occurs when the file does not exist.
	 * ArrayIndexOutOfBoundsException occurs when the user does not enter an argument
	 * for the main method from the command line.
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public static void main(String[] args) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
	
		// ============== OPENING FILE ==============
		
		// input validation
		try {
			args[0].equals("");	
		}
		catch (ArrayIndexOutOfBoundsException e) {
			// program terminates if user enters no argument
			System.err.println("Error: the program expects a file name as an argument.");
			System.exit(1);
		}
		
		File file = new File(args[0]); // create File object
		
		// more input validation
		try {
			Scanner testing = new Scanner(file); // test whether the file exists on your computer
		} 
		catch(FileNotFoundException e) { 
			// program terminates if user enters invalid input or if file cannot be opened
			System.err.println("Error: the file '" + args[0] + "' does not exist or cannot be opened.");
			System.exit(1);
		}
		
		
		// ============== READING FILE ==============
		
		Scanner in = new Scanner(System.in);
		TreeCollection treeCollect = new TreeCollection(); // create instance for storing all tree objects later on
		readFile(treeCollect, file); // call method for file processing
		
		// loop for checking frequency of a given tree name as long as user doesn't quit program
		String choice = "";
		while (!choice.equalsIgnoreCase("quit")) {
			
			// prompt user for a name
			System.out.print("\nEnter a tree species to learn more about it (\"quit\" to stop): ");
			choice = in.nextLine();
			
			// check if the tree species that user entered exists
			boolean treeExists = false;
			Collection<String> match = treeCollect.getMatchingSpecies(choice);
			if (match.size() > 0) {
				treeExists = true;
			}

			if (treeExists) { // if tree exists
				outputData(choice, treeCollect, match); // call method for displaying data about the given tree
			}
			else { // if tree does not exist
				if (!choice.equalsIgnoreCase("quit"))
					System.out.print("\nThere are no records of '" + choice + "' on NYC streets.\n");	
			}
		}
		in.close();
		System.out.println("\nEnd of Program.");

	}
	

	/**
	 * Reads and processes file, and creates Tree objects that store data about each tree.
	 * 
	 * @param an array list 'treeList' that will hold all Tree objects,
	 * and the file that is opened and read by the program
	 * @throws FileNotFoundException occurs when a file either does not exist or is null, 
	 * and IllegalArgumentException occurs when an argument passed to a Tree object as 
	 * a parameter is not the appropriate data type.
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public static void readFile(TreeCollection treeCollect, File file) throws FileNotFoundException,
		IllegalArgumentException {
		
		if (file.exists() && file != null) { // input validation
			Scanner info = new Scanner(file);
			while (info.hasNext()) { // read each line in file
				String nextLine = info.nextLine();
				ArrayList<String> treeData = splitCSVLine(nextLine); // call method for splitting CSV lines
				
				if (treeData.size() != 41) {
					// skip over lines that are not valid (they don't have 41 entries exactly)
					continue;
				}
				else {
					// skip over invalid arguments for all parameters
					try {
						if ( !( (Integer)Integer.parseInt(treeData.get(0)) instanceof Integer) || // is tree_id an int?
								!((Integer)Integer.parseInt(treeData.get(3)) instanceof Integer) || // is tree_dbh an int?
								!((Integer)Integer.parseInt(treeData.get(25)) instanceof Integer) ||  // is zip an int?
								!((Double)Double.parseDouble(treeData.get(39)) instanceof Double) || // is x_sp a double?
								!((Double)Double.parseDouble(treeData.get(40)) instanceof Double) ) { // is y_sp a double?
							throw new IllegalArgumentException();
						}
						if ( ((Integer)Integer.parseInt(treeData.get(0)) < 0) ) { // is tree_id positive?
							throw new IllegalArgumentException();
						}
						if ( ((Integer)Integer.parseInt(treeData.get(3)) < 0) ) { // is tree_dbh positive?
							throw new IllegalArgumentException();
						}
						if ( !( treeData.get(6).equalsIgnoreCase("Alive") || // is status alive?
								treeData.get(6).equalsIgnoreCase("Dead") || // is status dead?
								treeData.get(6).equalsIgnoreCase("Stump") || // is status stump?
								treeData.get(6).equals("") || // is status an empty string?
								treeData.get(6) == null) ) { // is status null?
							throw new IllegalArgumentException();
						}
						if ( !( treeData.get(7).equalsIgnoreCase("Good") || // is health good?
								treeData.get(7).equalsIgnoreCase("Fair") || // is health fair?
								treeData.get(7).equalsIgnoreCase("Poor") || // is health poor?
								treeData.get(7).equals("") || // is health an empty string?
								treeData.get(7) == null) ) { // is health null?
							throw new IllegalArgumentException();
						}
						if ( treeData.get(9) == null ) { // is spc_common null?
							throw new IllegalArgumentException();
						}
						if ( ((Integer)Integer.parseInt(treeData.get(25)) < 0) || // is zip a number from 0-99999?
								((Integer)Integer.parseInt(treeData.get(25)) > 99999) ) {
							throw new IllegalArgumentException();
						}
						if ( !( treeData.get(29).equalsIgnoreCase("Manhattan") || // is boro manhattan?
								treeData.get(29).equalsIgnoreCase("Bronx") || // is boro bronx?
								treeData.get(29).equalsIgnoreCase("Brooklyn") || // is boro brooklyn?
								treeData.get(29).equalsIgnoreCase("Queens") || // is boro queens?
								treeData.get(29).equalsIgnoreCase("Staten Island")) ) { // is boro staten island?
							throw new IllegalArgumentException();
						}
					} 
					catch (IllegalArgumentException e) {
						continue; // ignore the exception and move on to next line
					}
					
					// create tree object using Tree class with 9 parameters passed
					// only store data from the following indexes: 0, 3, 6, 7, 9, 25, 29, 39, 40
					// format all character strings to lowercase
					// format all numeric strings to Integer or Double
					Tree treeObject = new Tree(Integer.parseInt(treeData.get(0)), 
							Integer.parseInt(treeData.get(3)), 
							treeData.get(6).toLowerCase(), 
							treeData.get(7).toLowerCase(), 
							treeData.get(9).toLowerCase(), 
							Integer.parseInt(treeData.get(25)),
							treeData.get(29).toLowerCase(), 
							Double.parseDouble(treeData.get(39)), 
							Double.parseDouble(treeData.get(40)));
					
					// add this tree object to the TreeCollection list
					treeCollect.add(treeObject);
				}
			}
			info.close();
		}
		else {
			throw new FileNotFoundException("File not found.");
		}
	}
	
	
	/**
	 * Displays output of data for corresponding tree species.
	 * Uses methods from the TreeList class to process data.
	 * 
	 * @param a string representing the user's input from the main method, 
	 * and treeList that is an array list of Tree objects
	 * 
	 * @author Kelly Xie (kyx203)
	 */
	public static void outputData(String choice, 
			TreeCollection treeCollect, Collection<String> match) {
		
		
		// ============== DISPLAY ALL MATCHING TREE SPECIES ==============
		
		//Collection<String> match = treeCollect.getMatchingSpecies(choice); // call corresponding method
		System.out.println("\nAll matching species: ");
		for (int i=0; i<match.size(); i++) { // iterate through the list
			System.out.printf("   %s\n", ((ArrayList<String>) match).get(i)); 
		}
		
		
		//  ============== DISPLAY POPULARITY IN THE CITY ==============
		
		System.out.println("\nPopularity in the city: ");
		// display the number and percentage of the tree species in all of NYC
		
		double percentageNYC = 0;
		// handle divide by zero errors
		if ((double) treeCollect.getTotalNumberOfTrees() == 0) { 
			percentageNYC = 0;
		}
		else {
			// explicitly cast int as double
			percentageNYC = 100 * ((double) treeCollect.getCountByTreeSpecies(choice) 
				/ (double) treeCollect.getTotalNumberOfTrees() );
		}
		
		// formatted print statement with 4 arguments
		System.out.printf("   %-15s:%,10d(%,d)%7.2f%%\n", 
				"NYC",
				treeCollect.getCountByTreeSpecies(choice), // total species in NYC
				treeCollect.getTotalNumberOfTrees(), // total trees in NYC
				percentageNYC); // percentage of species of total trees
		
		// display the number and percentage of the tree species in each borough
		String[] boroName = {"Manhattan", "Bronx", "Brooklyn", "Queens", "Staten Island"};
		for (int i=0; i<boroName.length; i++) {
			double percentage = 0;
			// handle divide by zero errors
			if ((double) treeCollect.getCountByBorough(boroName[i].toLowerCase()) == 0 ) {
				percentage = 0;
			}
			else {
				// explicitly cast int as double
				percentage = 100 * ((double) treeCollect.getCountByTreeSpeciesBorough(choice, boroName[i].toLowerCase()) 
						/ (double) treeCollect.getCountByBorough(boroName[i].toLowerCase()) );
			}
			
			// formatted print statement with 4 arguments
			System.out.printf("   %-15s:%,10d(%,d)%7.2f%%\n", 
					boroName[i],
					treeCollect.getCountByTreeSpeciesBorough(choice, boroName[i].toLowerCase()), // total species in borough
					treeCollect.getCountByBorough(boroName[i].toLowerCase()), // total trees in borough
					percentage); // percentage of species of total trees
		}
		
	}
	
	
	/**
	 * This class splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries that may contain commas).
	 *
	 * @param textLine line of text to be parsed
	 * @return an ArrayList object containing all individual entries/tokens found on the line. 
	 * 
	 * @author Joanna Klukowska
	 */
	public static ArrayList<String> splitCSVLine(String textLine) { 
		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer(); 
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;

		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) { 
			nextChar = textLine.charAt(i);
			
			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar == '\u201D') {
				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false; 
					insideEntry = false;
				}
				else {
					insideQuotes = true;
					insideEntry = true;
				}
			}
			else if (Character.isWhitespace(nextChar)) { 
				if (insideQuotes || insideEntry) {
					// add it to the current entry
					nextWord.append(nextChar);
				}
				else { // skip all spaces between entries
					continue;
				}
			}
			else if (nextChar == ',') {
				if (insideQuotes) { // comma inside an entry 
					nextWord.append(nextChar);
				}
				else { // end of entry found 
					insideEntry = false; 
					entries.add(nextWord.toString()); 
					nextWord = new StringBuffer();
				}
			}
			else {
				// add all other characters to the nextWord 
				nextWord.append(nextChar);
				insideEntry = true;
			}
		}
		// add the last word (assuming not empty)
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}
		return entries;
	}

}
