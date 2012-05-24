import java.io.IOException;

public class Backend 
{
	/**
	 * Main Method for program
	 * 
	 * this method hold the file paths for the files to be used throughout the program. The method creates a new library object using the 4 file paths.
	 * the method uses the three commands within the try statement to load the collections and transactions and then process each transaction. if any of
	 * the three commands cause a failure that is not dealt with the catch statement is executed and a fatal error exception is caught.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException
	{
		String inTransactionPath;
		String inMasterDVDPath;
		String outMasterDVDPath;
		String outCurrentDVDPath;
		
		// input and output file paths and names
		
		inTransactionPath = "transaction.out";
		inMasterDVDPath = "master.in";
		outMasterDVDPath = "master.out";
		outCurrentDVDPath = "current.in";
		
		if ( args.length > 0  )
			inTransactionPath = args [0];
		
		if ( args.length > 1 )
			inMasterDVDPath = args [1];
		
		if ( args.length > 2 )
			outMasterDVDPath = args [2];
		
		if ( args.length > 3 )
			outCurrentDVDPath = args [3];
		
		Library lib = new Library();
		TransactionList log = new TransactionList ();
		try 
		{
			lib.loadCollection(inMasterDVDPath);
			log.addTransaction(inTransactionPath);
			
			for ( Transaction t : log )
			{
				try
				{
					lib.processTransaction(t);	
				}		
				catch (ConstraintWarningException ex)
				{
					System.out.println("Warning: Non-Fatal Warning: " + ex.getMessage());
					//System.out.println(ex.getMessage());
				}
				catch (ConstraintFailureException ex)
				{
					System.out.println("Error: Failed Constraint: " + ex.getMessage());
					//System.out.println(ex.getMessage());
				}
			}	
			
			lib.createMasterDVDFile(outMasterDVDPath);
			lib.createCurrentDVDFile(outCurrentDVDPath);
		} 
		catch (FatalErrorException e) 
		{
			System.out.println("Error: Fatal Error: " + e.getMessage() + " on Line #" + e.getLine () + " in Path: " + e.getPath());
		}
	}
}

// "00001 0001 0001 R 000.00 K-Pax                    " <----- sample master.in

// "03 street fighter            50 R 000.00"   <--- create transaction
