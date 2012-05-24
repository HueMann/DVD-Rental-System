/**	MohaWK DVD Rental Systems
 *	transaction.java
 *	@author Kelley, Christopher
 *	@author McAlear, Cassandra
 *	@author Watson, Ryan
 *	@version 1.01 Feb. 24, 2012
 */

import java.text.DecimalFormat;

/**	Transaction Class is a structure designed to hold the information necessary 
 *	for creating transaction files easily
 */
class Transaction{

	int code;	
	String title;
	int copies;
	boolean status; // 'rent' is 'true'; converted to string before written
	double price;
	
	DecimalFormat codeFormat = new DecimalFormat("00");
	DecimalFormat copiesFormat = new DecimalFormat("000");
	DecimalFormat priceFormat = new DecimalFormat("000.00");
	DecimalFormat emptyFormat = new DecimalFormat("000000");
	
	/**	Transaction Constructor instantiates a new transaction
	 * 
	 *	@param code the transaction code
	 *	@param title the DVD title involved
	 *	@param copies the number of copies of the DVD
	 *	@param status the status of the DVD
	 *	@param price the price of the DVD
	 */
	public Transaction(int code, String title, int copies, boolean status, 
		double price){
		this.code = code;	
		this.title = title;
		this.copies = copies;
		this.status = status;
		this.price = price;
	}

	/**	toFileFormat Method turns lines into format to be output to the file
	 *	
	 */
	String toFileFormat(){

		String appendSpaces = "";

		for (int i = 0; i < (25-title.length()); i++)		
			appendSpaces += " ";


		String fileFormat = "";
		fileFormat += codeFormat.format(code) + " ";
		fileFormat += title.replaceAll(" ", "_") + appendSpaces + " ";
		fileFormat += copiesFormat.format(copies) + " ";

		//the logout command has no DVD, used "l" for status identifier	
		if(status && code == 0) 
			fileFormat += "l" + " ";

		else if(status)		
			fileFormat += "r" + " ";

		else
			fileFormat += "s" + " ";

		
		if (price != 0)	
			fileFormat += priceFormat.format(price) + "\n";

		else
			fileFormat += emptyFormat.format(price) + "\n";


		return fileFormat;
	}	

}
