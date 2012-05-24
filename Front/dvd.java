/**	MohaWK DVD Rental Systems
 *	dvd.java
 *	@author Kelley, Christopher
 *	@author McAlear, Cassandra
 *	@author Watson, Ryan
 *	@version 1.03 Mar. 06, 2012
 */

/**	DVD Class is a structure to hold the DVD information after parsed from the 
 *	currentDVDFile
 */
class DVD{
	private String title;			//title of the dvd
	private String status;			//status of the dvd
	private int copies;				//total copies of the dvd
	private	double price;			//price of the dvd
	private boolean modified;		//create, add, sell commands set to true 
	private int unavailableCopies;	//copies unavailable in this session
	
	//Constructors

	/**	
	 *	DVD Constructor for new DVDs with no current information
	 */
	DVD(){
		title = "";
		status = "";
		copies = 0;
		price = 0.0;
		modified = true;
		unavailableCopies = 0;
	}
	
	/**	DVD Constructor for DVDs with current information available (NEW DVD)
	 *	
	 *	@param title title of the DVD
	 *	@param status status of the DVD
	 *	@param copies number of copies of the DVD
	 *	@param price price of a sale DVD
	 *	@param modified whether the create, add, or sell commands have been used
	 *	@param unavailableCopies number of copies unavailable in this session
	 */
	DVD(String title, String status, int copies, double price, boolean modified,
		 int unavailableCopies){
		this.title = title;
		this.status = status;
		this.copies = copies;
		this.price = price;
		this.modified = modified;
		this.unavailableCopies = unavailableCopies;
	}

	/**	DVD Constructor for DVDs with current information available (FROM FILE)
	 *	
	 *	@param title title of the DVD
	 *	@param status status of the DVD
	 *	@param copies number of copies of the DVD
	 *	@param price price of a sale DVD
	 */
	DVD(String title, String status, int copies, double price){
		this.title = title;
		this.status = status;
		this.copies = copies;
		this.price = price;
	}

	
	//Accessors

	/**	getTitle Method is a standard get
	 *	
	 *	@return title
	 */
	public String getTitle(){
		return title;
	}
	
	/**	getStatus Method is a standard get
	 *	
	 *	@return status
	 */
	public String getStatus(){
		return status;
	}
	
	/**	getCopies Method is a standard get
	 *	
	 *	@return copies
	 */
	public int getCopies(){
		return copies;
	}
	
	/**	getPrice Method is a standard get
	 *	
	 *	@return price
	 */
	public double getPrice(){
		return price;
	}

	/**	getModified Method is a standard get
	 *	
	 *	@return modified
	 */
	public boolean getModified(){
		return modified;
	}

	/**	getUnavailableCopies Method is a standard get
	 *	
	 *	@return unavailableCopies
	 */
	public int getUnavailableCopies(){
		return unavailableCopies;
	}

	//Mutators

	/**	setTitle Method is a standard set
	 *	
	 *	@param title
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**	setStatus Method is a standard set
	 *	
	 *	@param status
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**	setCopies Method is a standard set
	 *	
	 *	@param copies
	 */
	public void setCopies(int copies){
		this.copies = copies;
	}
		
	/**	setPrice Method is a standard set
	 *	
	 *	@param price
	 */
	public void setPrice(double price){
		this.price = price;
	}

	/**	setModified Method is a standard set
	 *	
	 *	@param modified
	 */
	public void setModified(boolean modified){
		this.modified = modified;
	}

	/**	setUnavailableCopies Method is a standard set
	 *	
	 *	@param unavailableCopies
	 */
	public void setUnavailableCopies(int unavailableCopies){
		this.unavailableCopies = unavailableCopies;
	}

}
