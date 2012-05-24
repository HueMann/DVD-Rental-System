/**	MohaWK DVD Rental Systems
 *	admin.java
 *	@author Kelley, Christopher
 *	@author McAlear, Cassandra
 *	@author Watson, Ryan
 *	@version 1.06 Mar. 06, 2012
 */

/**	
 *	Functionality of sell has been changed such that when changing the status 
 *	from 'sale' to 'rent', the user must give a confirmation. This was done 
 *	because the user simply had to input the name of the DVD to change, and 
 *	since no other information was required, it would change. Giving the user 
 *	one more step to completing the transaction is useful to prevent 
 *	unintentional errors on the user's part.
 *	We also chose to implement a confirmation on the status change from 'rent' 
 *	to 'sale' for consistency purposes.
 */

/**	
 *	Functionality for the remove transaction is unsure; currently we are setting
 *	a default price, since none is specified before.
 */

import java.io.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**	Admin Class is used to house the methods available for an admin user, 
 *	implements User class for skeleton methods	
 */
class Admin implements User{
	
	BufferedReader reader = null;
	Messages message;
	ArrayList<Transaction> transactions;
	DecimalFormat money = new DecimalFormat("0.00"); //setting the money format
	Timechecker timer;

	/**	Admin Constructor creating BufferedReader, Messages, and 
	 *	ArrayList<Transaction> objects
	 */
	Admin(BufferedReader reader){
		this.reader = reader;
		message = new Messages();
		transactions = new ArrayList<Transaction>();
		timer = new Timechecker();
	}	
	
	/** buy Method is used to buy DVDs
	 *	
	 *	@param file The currentDVDFile
	 *	@throws IOException prevents the user from crashing the system
	 */
	public int buy(DVD_File file, long lastCommandTime) throws IOException{

		timer.lastCommandTime = lastCommandTime;
		String title;
		String input;
		int copies = 0;

		System.out.println(message.WILL_BUY_ADM);
		System.out.println(message.ENTER_TITLE);
		title = reader.readLine();
		if(!checkTime())		//If timed out then exit
			return 0;		

		if(file.hasTitle(title)){ //If the title exists
			
			if(file.rentStatus(title, "s")){ //If the movie status is sale
		
				System.out.println(message.ENTER_COPIES);
				input = reader.readLine();	
				if(!checkTime())		//If timed out then exit
					return 0;
			
				try{
				
					copies = Integer.parseInt(input);

					if (copies <= 0 || copies > 999){

						System.out.println(message.INV_COPIES);

					}else if(copies > 0 && copies <= 999){

						//If the file has copies available
						if(file.hasCopies(title, copies)){	

							System.out.println(title + message.UNIT_COST + 
								money.format(file.getPrice(title)) + 
								message.UNIT_COST2 + 
								money.format(file.getPrice(title)*copies));	
							System.out.println(message.CONFIRM_PURCH);
							input = reader.readLine();
							if(!checkTime())		//If timed out then exit
								return 0;

							if(input.equalsIgnoreCase("yes")){					
					
								file.removeCopies(title, copies);
								System.out.println(message.PURCH_SUCCESS);
								transactions.add(new Transaction(6,	title, 
									copies, file.rentStatus(title, "r"), 
									file.getPrice(title)));

							}else{

								System.out.println(message.CONFIRM_FAIL);

							}

						}else{

							System.out.println(message.NOT_ENOUGH);

						}

					}else{

						System.out.println(message.NO_COPIES);

					}

				}catch(Exception e){
				
					System.out.println(message.INV_COPIES);

				}

			}else{

				System.out.println(message.INV_SALE);

			}

		}else if(title.compareTo(" ")==0 || title.compareTo("")==0){

			System.out.println(message.INV_NAME);
		
		}else{

			System.out.println(message.NAME_NOT_FOUND);

		}
		return 1;
	}
	
	/**	rent Method allows determines whether a user can rent copies of a DVD
	 * 
	 *	@param file The currentDVDFile
	 *	@throws IOException prevents the user from crashing the system
	 */
	public int rent(DVD_File file, long lastCommandTime) throws IOException{

		timer.lastCommandTime = lastCommandTime;
		String title;					//The title of the movie rented
		String input;					//Input from the user
		int copies = 0;					//Number of copies wanting to be rented
		
		System.out.println(message.WILL_RENT);
		System.out.println(message.ENTER_TITLE);
		title = reader.readLine();
		if(!checkTime())		//If timed out then exit
			return 0;
		
		if(file.hasTitle(title)){		//If the title exists
			if(file.getCopies(title) - file.getUnavailableCopies(title) > 0){
				

				//If the movie status is rental
				if(file.rentStatus(title, "r")){
		
					System.out.println(message.ENTER_COPIES);
					input = reader.readLine();
					if(!checkTime())		//If timed out then exit
						return 0;
		
					try{
			
						copies = Integer.parseInt(input);

						//If copies rented is more than 3 or less than or equal to 0
						if(copies > 3 || copies <= 0){
		
							System.out.println(message.INV_COPIES);
			
						//If copies is between 0 and 3 exclusively
						}else if (copies <= 3 && copies > 0){
				
							//If the file has copies available
							if(file.hasCopies(title, copies)){						
				
								file.removeCopies(title, copies);
								System.out.println(message.RENT_SUCCESS);
								transactions.add(new Transaction(1,	title, copies, 
									file.rentStatus(title, "r"), 
									file.getPrice(title)));

							}else{

								System.out.println(message.NOT_ENOUGH);

							}

						}else{

							System.out.println(message.NO_COPIES);

						}

					}catch(Exception e){
			
						System.out.println(message.INV_COPIES);
					}
			
				}else{

					System.out.println(message.INV_RENTAL);

				}

			}
			else
				System.out.println(message.DVD_UNAVAILABLE);

		}else if(title.compareTo(" ")==0 || title.compareTo("")==0){

			System.out.println(message.INV_NAME);

		}else if(file.getModified(title)){

			System.out.println(message.DVD_UNAVAILABLE);

		}else{

			System.out.println(message.NAME_NOT_FOUND);

		}
		return 1;
	}
	
/** returns Method allows the user to return copies of a DVD
	 *	
	 *	@param file The currentDVDFile
	 *	@throws IOException prevents the user from crashing the system
	 */
	public int returns(DVD_File file, long lastCommandTime) throws IOException{

		timer.lastCommandTime = lastCommandTime;		
		String title;						//Title of returned DVD
		String input;						//Input from the user
		int copies = 0;						//Number of copies to be returned
		
		System.out.println(message.WILL_RETURN);
		System.out.println(message.ENTER_TITLE);
		title = reader.readLine();
		if(!checkTime())		//If timed out then exit
			return 0;
		
		if(file.hasTitle(title)){
			
			System.out.println(message.ENTER_COPIES);
			input = reader.readLine();
			if(!checkTime())		//If timed out then exit
				return 0;
			
			try{
				
				copies = Integer.parseInt(input);

				//if copies returned is more than 3 or less than or equal to 0
				if(copies > 3 || copies <= 0){							
		
					System.out.println(message.INV_COPIES);
		
				}else{
			
					if(file.getCopies(title) + copies <= 999){

						file.addCopies(title, copies);
						System.out.println(message.RETURN_SUCCESS);
						transactions.add(new Transaction(2,	title, copies, 
							file.rentStatus(title, "r"), file.getPrice(title)));

					}

				}

			}catch(Exception e){
				
				System.out.println(message.INV_COPIES);

			}

		}else if(title.compareTo(" ")==0 || title.compareTo("")==0){			
			System.out.println(message.INV_NAME);
			
		}else{
			
			System.out.println(message.NAME_NOT_FOUND);

		}
		return 1;
	}
	
	/**	add Method adds more copies to an existing DVD title
	 *	
	 *	@param file The currentDVDFile
	 *	@throws IOException prevents the user from crashing the system
	 */
	public int add(DVD_File file, long lastCommandTime) throws IOException{

		timer.lastCommandTime = lastCommandTime;		
		String title;						//Title of returned DVD
		String input;						//Input from the user
		int copies = 0;						//Number of copies to be added
		
		System.out.println(message.WILL_ADD);
		System.out.println(message.ENTER_TITLE);
		title = reader.readLine();
		if(!checkTime())		//If timed out then exit
			return 0;

		if(file.hasTitle(title)){

			System.out.println(message.ENTER_COPIES);
			input = reader.readLine();
			if(!checkTime())		//If timed out then exit
				return 0;
			
			try{
				
				copies = Integer.parseInt(input);

				if(copies > 0 && copies <= 999){

					if(file.getCopies(title) + copies <= 999){

						file.addCopies(title, copies);
						System.out.println(message.ADD_SUCCESS);
						transactions.add(new Transaction(4,	title, copies, 
							file.rentStatus(title, "r"), file.getPrice(title)));

					}else{

						System.out.println(message.TOTAL_999);

					}

				}else{

					System.out.println(message.INV_COPIES);

				}

			}catch(Exception e){
				
				System.out.println(message.INV_COPIES);

			}

		}else if(title.compareTo(" ")==0 || title.compareTo("")==0){
			
			System.out.println(message.INV_NAME);			

		}else{

			System.out.println(message.NAME_NOT_FOUND);

		}
		return 1;
	}
	
	/**	sell Method changes the status of a DVD
	 *	
	 *	@param file The currentDVDFile
	 *	@throws IOException prevents the user from crashing the system
	 */
	public int sell(DVD_File file, long lastCommandTime) throws IOException{

		timer.lastCommandTime = lastCommandTime;		
		String title;				//Title of DVD to be created
		String input;				//Input from user
		double price = 0.0;			//Sale price to be set by the user
		
		System.out.println(message.WILL_SELL);
		System.out.println(message.ENTER_TITLE);
		title = reader.readLine();
		if(!checkTime())		//If timed out then exit
			return 0;

		if(file.hasTitle(title) && file.getModified(title)){

			System.out.println(message.DVD_UNAVAILABLE);

		}else if(file.hasTitle(title) && !file.getModified(title)){	
			
			//currently set to sale & not modified
			if(file.rentStatus(title, "s") && !file.getModified(title)){	

				System.out.println(message.WILL_RENTAL);
				System.out.println(message.CONFIRM_STAT);
				input = reader.readLine();
				if(!checkTime())		//If timed out then exit
					return 0;

				if(input.equalsIgnoreCase("yes")){					
			
					file.changeStatus(title);
					System.out.println(message.STAT_CHANGE_SUCCESS);
					transactions.add(new Transaction(7,	title, 
						file.getCopies(title), file.rentStatus(title, "r"), 
						file.getPrice(title)));

				}else{

					System.out.println(message.CONFIRM_FAIL);

				}
			
			//currently set to rental & not modified
			}else if (!file.getModified(title)){	
				
				System.out.println(message.WILL_SALE);
				System.out.println(message.SALE_PRICE);
				input = reader.readLine();
				if(!checkTime())		//If timed out then exit
					return 0;
			
				try{
				
					price = Double.parseDouble(input);
				
					if(price <= 0.00){

						System.out.println(message.INV_PRICE);

					}else if(price > 150.00){

						System.out.println(message.PRICE_EXCEED);

					}else if(price > 0.00 && price <= 150.00){ //formatting

						//check if valid format   
						if(price == Double.parseDouble(money.format(price))){   

							System.out.println(message.CONFIRM_STAT);
							input = reader.readLine();
							if(!checkTime())		//If timed out then exit
								return 0;

							if(input.equalsIgnoreCase("yes")){					
			
								file.changeStatus(title);
								file.setPrice(title, price);
								System.out.println(message.STAT_CHANGE_SUCCESS);
								transactions.add(new Transaction(7,	title, 
									file.getCopies(title), 
									file.rentStatus(title, "r"), 
									file.getPrice(title)));

							}else{

								System.out.println(message.CONFIRM_FAIL);

							}

						}else{

							System.out.println(message.INV_PRICE);

						}

					}

				}catch(Exception e){
				
					System.out.println(message.INV_PRICE);

				}

			}

		}else if(title.compareTo(" ")==0 || title.compareTo("")==0){
			
			System.out.println(message.INV_NAME);			

		}else{

			System.out.println(message.NAME_NOT_FOUND);

		}
		return 1;
	}
	
	/**	create Method creates a new dvd in the system
	 *	
	 *	@param file The currentDVDFile
	 *	@throws IOException prevents the user from crashing the system
	 */	
	public int create(DVD_File file, long lastCommandTime) throws IOException{

		timer.lastCommandTime = lastCommandTime;		
		String title;				//Title of DVD to be created
		String input;				//Input from user
		int copies = 0;				//Number of copies of the DVD at creation
		
		System.out.println(message.WILL_CREATE);
		System.out.println(message.ENTER_TITLE);
		title = reader.readLine();
		if(!checkTime())		//If timed out then exit
			return 0;
		
		if(file.hasTitle(title)){	
	
			System.out.println(message.NAME_TAKEN);
			
		}else if(title.length() > 25){
	
			System.out.println(message.TOO_MANY_CHAR);

		}else if(title.compareTo(" ")==0 || title.compareTo("")==0){
			
			System.out.println(message.INV_NAME);
			
		}else{		

			System.out.println(message.ENTER_COPIES);
			input = reader.readLine();
			if(!checkTime())		//If timed out then exit
				return 0;
			
			try{
				
				copies = Integer.parseInt(input);
			
				if(copies > 999){
			
					System.out.println(message.TOTAL_999);
				
				}else if(copies < 0){
			
					System.out.println(message.INV_COPIES);
				
				}else{
				
					file.addDVD(title, copies);
					System.out.println(message.CREATE_SUCCESS);
					transactions.add(new Transaction(3,	title, copies, 
						file.rentStatus(title, "r"), file.getPrice(title)));

				}

			}catch(Exception e){
				
				System.out.println(message.INV_COPIES);

			}
			
		}
		return 1;
	}
	
	/** remove Method sets the status of a title to rent, removes any copies, 
	 *	and sets a default price for the sale
	 *	
	 *	@param file The currentDVDFile
	 *	@throws IOException prevents the user from crashing the system
	 */
	public int remove(DVD_File file, long lastCommandTime) throws IOException{

		timer.lastCommandTime = lastCommandTime;		
		String title;				//Title of DVD to be created
		String input;				//Input from user
		double DEFAULT_PRICE = 10.00;
		
		System.out.println(message.WILL_REMOVE);
		System.out.println(message.ENTER_TITLE);
		title = reader.readLine();
		if(!checkTime())		//If timed out then exit
			return 0;

		if(file.hasTitle(title)){

			//if rental, set to sale; if sale, don't change
			if(file.rentStatus(title, "r")){ 	

				file.changeStatus(title);

			}
			
			file.removeCopies(title, file.getCopies(title)); //remove copies
			file.setPrice(title, DEFAULT_PRICE);
			System.out.println(message.REMOVE_SUCCESS);
			transactions.add(new Transaction(5,	title, file.getCopies(title), 
				file.rentStatus(title, "r"), file.getPrice(title)));

		}else if(title.compareTo(" ")==0 || title.compareTo("")==0){
			
			System.out.println(message.INV_NAME);			

		}else{

			System.out.println(message.NAME_NOT_FOUND);

		}
		return 1;
	}

	/** logout Method logs the user out of the system
	 *	
	 *	@return returns false to indicate the user is logged out
	 */
	public void logout(){
		
		System.out.println(message.THANK_YOU);
		transactions.add(new Transaction(0,	"LOGOUT_COMMAND", 0, 
			true, 0));
	}

	/**	checkTime Method used to check the session time
	 *	
	 */
	public boolean checkTime(){

		if (!timer.timeoutCheck()){
			logout();
			return false;
		}
		return true;

	}

}
