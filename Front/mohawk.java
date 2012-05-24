/**	MohaWK DVD Rental Systems
 *	mohawk.java
 *	@author Kelley, Christopher
 *	@author McAlear, Cassandra
 *	@author Watson, Ryan
 *	@version 1.05 Mar. 06, 2012
 */

/**	TODO:
 *	Add instructions to internal documentation
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

/** Class Mohawk is used to organize the system and create many of the objects
 *  and structures needed to execute the program
 */
class Mohawk{
	Messages message;		//Messages file that contains all outputs to user
	Admin admin;			//Admin user to the system
	Default user;			//Standard user to the system
	DVD_File file;			//DVD_File containing all dvds
	boolean exiting;		//Bool marking if the system is exiting or running
	boolean loggedIn;		//Bool to decide whether user is logged in or not
	String userType;		//The type of user currently logged in
	String lastCommand;		//The last command typed in by the user
	BufferedReader reader;	//Buffered reader to input user commands
	Timechecker timer;		//Timechecker object to confirm the session timeout

	//Constructors

	/**	
	 *	Mohawk Constructor if there is no currentDVDFile input
	 */
	Mohawk(){
		reader = new BufferedReader(new InputStreamReader(System.in));
		message = new Messages();
		admin = new Admin(reader);
		user = new Default(reader);
		file = new DVD_File();
		exiting = false;
		loggedIn = false;
		userType = "";
		lastCommand = "";
		timer = new Timechecker();
	}

	/**	
	 *	Mohawk Constructor if there is a currentDVDFile input
	 */
	Mohawk(String filename) throws IOException{
		reader = new BufferedReader(new InputStreamReader(System.in));
		message = new Messages();
		admin = new Admin(reader);
		user = new Default(reader);
		file = new DVD_File(filename);
		exiting = false;
		loggedIn = false;
		userType = "";
		lastCommand = "";
		timer = new Timechecker();
	}

	//Methods
	
	/**	main Method is used to instantiate a new Mohawk object and begin the 
	 *	system
	 *
	 *	@param args	String array holding arguments from CLI; currentDVDFil.txt 
	 *	@throws IOException Input/Output Exception for loading the file
	 */
	public static void main(String[]args) throws IOException{
		Mohawk program = new Mohawk(args[0]);
		
		//program.print(); //debug
		program.run();
		//program.print(); //debug
	}

	/**	run Method is used to run the system, and handles the login process
	 *	
	 *	@throws IOException prevents the user from crashing the system
	 */
	public void run() throws IOException{
		
		do{				//Run the software while the exit command is not used
			
			lastCommand = "";								//Reset lastCommand
			loggedIn = false;								//Reset loggedIn
			timer.lastCommandTime = System.currentTimeMillis();	//Reset timer
			timer.currentTime = System.currentTimeMillis();	//Reset timer
			
			System.out.println(message.WELCOME);
			lastCommand = reader.readLine();
			
			if(lastCommand.equalsIgnoreCase("exit"))		//exit command
				exiting = true;

			else if(!lastCommand.equalsIgnoreCase("login"))
				System.out.println(message.INV_COMMAND);

			else{

				timer.lastCommandTime = System.currentTimeMillis(); //reset time
				login();
									
				if(!loggedIn){			//If timed out then dont enter system

				}else if (userType.equalsIgnoreCase("standard") 
					|| userType.equalsIgnoreCase("admin")){
					
					do{					//While logged in give command options
						
						if(optionSelect() == 0)
							loggedIn = false;
					}
					while(loggedIn);
					
				}else{

					System.out.println(message.INV_USER);					
					
				}
				
			}	
			
		}
		while(!exiting);	
		System.out.println(message.GOOD_NIGHT);
		
	}

	/**	writeTransactions Method writes new DVD transactions to a file
	 *	
	 *	@throws IOException to prevent file IO problems
	 */
	public void writeTransactions(ArrayList<Transaction> transactions) 
		throws IOException{

		Calendar calender = Calendar.getInstance();
		int year = calender.get(Calendar.YEAR);
		int month = calender.get(Calendar.MONTH);
		int day = calender.get(Calendar.DAY_OF_MONTH);
		
		//month doesn't display correctly; seems to index from 0
		String fileName = "transactions/transaction-" + year + "-" + (month+1) 
			+ "-" + day + "-" + System.currentTimeMillis() + ".txt";

		try{

			BufferedWriter transactionWriter = new BufferedWriter(
											 new FileWriter(fileName));
			//write out all the transactions
			for (Transaction t : transactions){

				transactionWriter.write(t.toFileFormat());

			}

			transactionWriter.close();

		}catch(IOException ioe){

			System.out.println(message.IO_ERROR);

		}

	}

	/**	login Method is used to determine the type of user
	 * 
	 *	@throws IOException prevents the user from crashing the system
	 */	
	public void login() throws IOException{
		
		System.out.println(message.USER_TYPE);

		userType = reader.readLine();
		checkTime();


	}
	
	/**	optionSelect Method directs the program into the correct transaction 
	 *	according to the user input
	 * 
	 *	@throws IOException prevents the user from crashing the system
	 */	
	public int optionSelect() throws IOException{
		
		if(userType.equals("admin")){
			
			System.out.println(message.ADMIN_COMMANDS);
			lastCommand = reader.readLine();
			
			if(!checkTime())				//If timed out then exit
				return 0;

			if(lastCommand.equalsIgnoreCase("create")){
				if(admin.create(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("add")){
				if(admin.add(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("remove")){
				if(admin.remove(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("rent")){
				if(admin.rent(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("return")){
				if(admin.returns(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("sell")){
				if(admin.sell(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("buy")){
				if(admin.buy(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("logout")){
				admin.logout();
				loggedIn = false;			
				writeTransactions(admin.transactions);

			}else			
				System.out.println(message.INV_COMMAND);
			
		}
			
		else{
			System.out.println(message.USER_COMMANDS);
			lastCommand = reader.readLine();

			if(!checkTime())			//If timed out then exit
				return 0;
			
			if(lastCommand.equalsIgnoreCase("create"))
				System.out.println(message.PRIV_TRANS);
				
			else if(lastCommand.equalsIgnoreCase("add"))
				System.out.println(message.PRIV_TRANS);
				
			else if(lastCommand.equalsIgnoreCase("remove"))
				System.out.println(message.PRIV_TRANS);
				
			else if(lastCommand.equalsIgnoreCase("rent")){
				if(user.rent(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("return")){
				if(user.returns(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("sell"))
				System.out.println(message.PRIV_TRANS);
			
			else if(lastCommand.equalsIgnoreCase("buy")){
				if(user.buy(file, timer.lastCommandTime) == 0)
					return 0;
			}	
			else if(lastCommand.equalsIgnoreCase("logout")){
				user.logout();
				loggedIn = false;
				writeTransactions(user.transactions);

			}else				
				System.out.println(message.INV_COMMAND);
		}
		return 1;
	}
	
	/**	print Method used for Ad-Hoc testing
	 *
	 */
	public void print(){
		
		DVD current = new DVD();
		for(int i = 0; i < file.dvds.size(); i++){
			current = (DVD)file.dvds.get(i);
			System.out.println(current.getTitle() + " " + current.getCopies() 
				+ " " + current.getStatus() + " " + current.getPrice());
		}
	}

	/**	checkTime Method used to check the session time
	 *	
	 */
	public boolean checkTime() throws IOException{

		if (!timer.timeoutCheck()){

			loggedIn = false;
			writeTransactions(user.transactions);

		}
		else
			loggedIn = true;
		
		return loggedIn;
	}

}
