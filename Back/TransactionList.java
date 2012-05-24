import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

@SuppressWarnings("serial")
public class TransactionList extends Vector<Transaction> 
{
	private static final String END_TRANSACTION = "00                           000   000.00";
	
	public TransactionList()
	{
		super();
	}
	
	/**
	 * loadTransactions method
	 * 
	 * This method creates a new scanner object to read in individual lines from the target transaction file. The method creates a new Transaction 
	 * object passing the current record to the constructor. The method then checks if the record represents the end transaction in which case the 
	 * execution is complete.
	 * 
	 * @throws IOException is thrown in the event their is an error with the IO
	 * @throws FatalErrorException 
	 */
	public void addTransaction(String path) throws IOException, FatalErrorException
	{
		Scanner in = new Scanner(new FileReader(path));
		
		boolean complete = false;
		boolean doubleEnd = false;
		int lineCounter = 0;
		
		while ( !complete )
		{
			lineCounter++;
			
			try
			{
				if ( !in.hasNextLine() ) 
				{
					throw new FatalErrorException(FatalErrorException.MSG_INVALID_DVD_FORMAT, path, lineCounter);
				}
				
				String record = in.nextLine();
				Transaction t = new Transaction (record);
								
				if ( record.equals(END_TRANSACTION)) 
				{
					/*if (!doubleEnd)
					{
						doubleEnd = true;
					}
					else
					{*/
						complete = true;
					//}
				}
				else 
				{	
					this.add (t);
					doubleEnd = false;
				}
			}
			catch (NumberFormatException ex)
			{
				throw new FatalErrorException(FatalErrorException.MSG_INVALID_TRANSACTION_FORMAT, ex, path, lineCounter);
			}
			catch (StringIndexOutOfBoundsException ex)
			{
				throw new FatalErrorException(FatalErrorException.MSG_INVALID_TRANSACTION_FORMAT, ex, path, lineCounter);
			}
		}
	}
}
