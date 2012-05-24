/**	MohaWK DVD Rental Systems
 *	dvd_file.java
 *	@author Kelley, Christopher
 *	@author McAlear, Cassandra
 *	@author Watson, Ryan
 *	@version 1.03 Mar. 06, 2012
 */

import java.io.*;
import java.util.*;
//import java.lang.String;

/**	DVD_File Class manages the operations available for the DVD information
 *	
 */
class DVD_File{
	
	ArrayList dvds;
	
	//Constructors

	/**	
	 *	DVD_File Constructor if there are no DVDs in the currentDVDFile
	 */
	DVD_File(){
		
		dvds = new ArrayList();

	}
	
	/**	DVD_File Constructor if there are DVDs in the currentDVDFile
	 *	
	 *	@param filename the currentDVDFile file name
	 *	@throws IOException used to prevent errors from reading in the file
	 */
	DVD_File(String filename) throws IOException{
		
		dvds = new ArrayList();
		readFile(filename);

	}
	
	//Methods

	/**	readFile Method reads in the currentDVDFile and parses it into an 
	 *	ArrayList, dvds
	 *	
	 *	@param filename the currentDVDFile file name
	 *	@throws IOException used to prevent errors from reading in the file
	 */
	public void readFile(String filename) throws IOException{
		
		String title;						//Title of DVD
		String status;						//Status of DVD
		int copies;							//Copies of DVD
		double price;						//Price of DVD
		String line;						//Line for reading in strings
		String[] tokens;					//Separates strings into tokens
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		line = reader.readLine();
		
		while(line != null){
			
			tokens = line.split("[\\s]+"); //split and remove all whitespace
			title = tokens[0];
			copies = Integer.parseInt(tokens[1]);
			status = tokens[2];
			price = Double.parseDouble(tokens[3]);

			//DVDs from the file have underscores to prevent accidental splits
			title = title.replace("_", " "); 

			dvds.add(new DVD(title, status, copies, price));
			line = reader.readLine();

		}
		
		reader.close();
		
	}
	
	/**	hasTitle Method checks if a title exists in the currentDVDFile
	 *	
	 *	@param title title of a DVD
	 *	@return returns true if the title exists
	 */
	public boolean hasTitle(String title){
		
		DVD current = new DVD();			//DVD used for checking
		
		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);
			
			if((current.getTitle()).equals(title))
				return true;

		}
		
		return false;
	}

	/**	hasCopies Method checks if the title has enough copies available
	 *	
	 *	@param title title of the DVD
	 *	@param copies number of copies of the DVD
	 *	@return returns true if there are as many or more copies than needed
	 */
	public boolean hasCopies(String title, int copies){
		
		DVD current = new DVD();
		
		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);
			
			if((current.getTitle()).equals(title)){
				
				if(current.getCopies() - current.getUnavailableCopies() 
					>= copies)
					return true;
				
				else
					return false;
			}
				
		}
		
		return false;
	}
	
	/** getCopies Method returns the total number of copies a DVD title has
	 *	
	 *	@param title the title of a DVD
	 *	@return amount of copies
	 */
	public int getCopies(String title){

		DVD current = new DVD();
		int amount = 0;

		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);

			if((current.getTitle()).equals(title)){

				amount = current.getCopies();

			}

		}
	
		return amount;

	}

	/**	rentStatus Method checks the status of the movie
	 *	
	 *	@param title the title of the DVD
	 *	@param status the status of the DVD
	 *	@return returns true if the provided status matches the file
	 */
	public boolean rentStatus(String title, String status){
		
		DVD current = new DVD();
		
		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);
			
			if((current.getTitle()).equals(title)){
				
				if((current.getStatus()).equalsIgnoreCase(status))
					return true;
				
				else
					return false;
			}
				
		}
		
		return false;
	}

	/**	changeStatus Method changes the status of the movie
	 *	
	 *	@param title the title of the DVD
	 */
	public void changeStatus(String title){

		DVD current = new DVD();

		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);
			
			if((current.getTitle()).equals(title)){

				if((current.getStatus()).equalsIgnoreCase("r")){

					current.setStatus("s");

				}else{

					current.setStatus("r");

				}
			
			}

		}

	}

	/**	removeCopies Method removes copies from the DVD
	 *	
	 *	@param title the title of the DVD
	 *	@param copies the number of copies of the DVD
	 */
	public void removeCopies(String title, int copies){
		
		DVD current = new DVD();
		int amount;							//amount of copies before change
		
		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);
			
			if((current.getTitle()).equals(title)){
				
				amount = current.getCopies();
				current.setCopies(amount - copies);

			}
				
		}
		
	}

	/**	addCopies Method adds copies to the DVD
	 *	
	 *	@param title the title of the DVD
	 *	@param copies the number of copies of the DVD
	 */
	public void addCopies(String title, int copies){
		
		DVD current = new DVD();
		int amount;							//amount of copies before change
		
		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);
			
			if((current.getTitle()).equals(title)){
				
				amount = current.getCopies();
				current.setCopies(amount + copies);
				current.setModified(true);
				current.setUnavailableCopies(copies);

			}
				
		}
		
	}

	/**	addDVD Method adds DVD to the DVD_File
	 *	
	 *	@param title the title of the DVD
	 *	@param copies the number of copies of the DVD
	 */
	public void addDVD(String title, int copies){
		
		//New DVDs are set to rental and $0, since they are not for sale
		DVD current = new DVD(title, "r", copies, 0.0, true, copies);
		dvds.add(current);

	}

	/**	setPrice Method sets the price for a DVD
	 *	
	 *	@param title the title of the DVD
	 *	@param price the price of the DVD
	 */
	public void setPrice(String title, double price){

		DVD current = new DVD();
		
		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);

			if((current.getTitle()).equals(title)){

				current.setPrice(price);

			}

		}

	}

	/**	setModified Method sets the modified attribute for a DVD
	 *	
	 *	@param title the title of the DVD
	 *	@param modified the modified state of the DVD
	 */
	public void setModified(String title, boolean modified){

		DVD current = new DVD();
		
		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);

			if((current.getTitle()).equals(title)){

				current.setModified(modified);

			}

		}

	}

	/**	setUnavailableCopies Method sets the price for a DVD
	 *	
	 *	@param title the title of the DVD
	 *	@param unavailableCopies the number of unavailable copies of the DVD
	 */
	public void setUnavailableCopies(String title, int unavailableCopies){

		DVD current = new DVD();
		
		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);

			if((current.getTitle()).equals(title)){

				current.setUnavailableCopies(unavailableCopies);

			}

		}

	}

	/** getPrice Method returns the price of a DVD title
	 *	
	 *	@param title the title of the DVD
	 *	@return price the price of the DVD
	 */
	public double getPrice(String title){

		DVD current = new DVD();
		double price = 0.0;

		for(int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);

			if((current.getTitle()).equals(title)){

				price = current.getPrice();

			}

		}
	
		return price;

	}

	/**	getModified Method gets the modified boolean from the dvd
	 *	
	 *	@param title the title of the dvd
	 *	@return modified the modified status
	 */
	public boolean getModified(String title){
		
		DVD current = new DVD();
		boolean modified = false;
		
		for (int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);

			if((current.getTitle()).equals(title)){
				
				modified = current.getModified();

			}

		}

		return modified;

	}

	/**	getUnavailableCopies Method gets the number of unavailable copies of 
	 *	the dvd
	 *	
	 *	@param title the title of the dvd
	 *	@return modified the modified status
	 */
	public int getUnavailableCopies(String title){
		
		DVD current = new DVD();
		int unavailableCopies = 0;
		
		for (int i = 0; i < dvds.size(); i++){
			
			current = (DVD)dvds.get(i);

			if((current.getTitle()).equals(title)){
				
				unavailableCopies = current.getUnavailableCopies();

			}

		}

		return unavailableCopies;

	}

	/**	endOfDayReset Method changes the modified and unavailableCopies 
	 *	variables to their standard 'none' settings
	 */
	public void endOfDayReset(){
		
		DVD current = new DVD();

		for(int i = 0; i < dvds.size(); i++){

			current.setModified(false);
			current.setUnavailableCopies(0);

		}
	}

}
