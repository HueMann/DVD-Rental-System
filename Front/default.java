/**	MohaWK DVD Rental Systems
 *	default.java
 *	@author Kelley, Christopher
 *	@author McAlear, Cassandra
 *	@author Watson, Ryan
 *	@version 1.05 Mar. 04, 2012
 */

import java.io.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**	Default Class is used to house the methods available for a standard user, 
 *	implements User class for skeleton methods	
 */
class Default implements User{
	
	BufferedReader reader = null;
	Messages message;
	ArrayList<Transaction> transactions;
	DecimalFormat money = new DecimalFormat("0.00"); //setting the money format
	Timechecker timer;
	
	/**	Default Constructor creating BufferedReader, Messages, and 
	 *	ArrayList<Transaction> objects
	 */
	Default(BufferedReader reader){
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

		System.out.println(message.WILL_BUY_STD);
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

					if (copies <= 0 || copies > 5){

						System.out.println(message.INV_COPIES);

					}else if(copies > 0 && copies <= 5){

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
		String title = "";					//The title of the movie rented
		String input = "";					//Input from the user
		int copies = 0;					//Number of copies wanting to be rented
		
		System.out.println(message.WILL_RENT);
		System.out.println(message.ENTER_TITLE);
		title = reader.readLine();
		if(!checkTime())		//If timed out then exit
			return 0;
		
		if(file.hasTitle(title)){		//If the title exists

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

		}else if(title.compareTo(" ")==0 || title.compareTo("")==0){

			System.out.println(message.INV_NAME);

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
	
	/** logout Method prints a logout prompt and sets the loggedIn bool to false
	 *	
	 *	@return loggedIn boolean
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
