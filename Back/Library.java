import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;
/**
 * The library class accepts individual records from the target transaction file and MasterDVD file. Based on the execution from MasterDVD for
 * the individual transactions in each record the corresponding process is then executed and logged in the proper file (quantities updated, dvds removed/created).
 * If the current record represents the end of the transaction file the class simply executes the final command and then exits execution. The Class after
 * each transaction is completed writes the MasterDVD file and the CurrentDVD file using the appropriate toString() methods.
 * The class catches any  obvious outstanding errors and handles them accordingly.
 *
 *
 * @version 0.8 02/24/12
 *
 */

public class Library
{
	// constants
	private static final double INITIAL_PRICE = 0.0;
	private static final int MAX_QUANTITY = 9999;

	private static final String END_DVD = "END                       000   000.00";

	// data structures used to manage transactions and DVDs
	private Vector<Integer> unused = new Vector<Integer>();
	private HashMap<String, MasterDVD> inv = new HashMap<String, MasterDVD>();
	private int pos;

	/**
	 * load collection method
	 *
	 * This method creates a scanner object to read in the individual records from the MasterDVDfile.The method reads in each record and then
	 * creates a DVD object and adds it to the names hashmap. The for loop updates the unused vector which contains the unique DVD numbers.
	 *
	 * @throws FileNotFoundException used if there is an error with the file being read.
	 * @throws FatalErrorException
	 */
	public void loadCollection(String path) throws FileNotFoundException, FatalErrorException
	{
		Scanner in = new Scanner(new FileReader(path));

		int pos = 0;
		int lineCounter = 0;

		while ( in.hasNextLine() )
		{
			try
			{
				lineCounter++;
				String record = in.nextLine();				
				MasterDVD dvd = new MasterDVD(record);				
				inv.put(dvd.getTitle(), dvd);
		
				for ( int i = pos; i < dvd.getNumber(); i++ )
				{
					unused.add(i);
				}

				pos = dvd.getNumber()+1;
			}
			catch (NumberFormatException ex)
			{
				throw new FatalErrorException(FatalErrorException.MSG_INVALID_DVD_FORMAT, ex, path, lineCounter);
			}
			catch (StringIndexOutOfBoundsException ex)
			{
				throw new FatalErrorException(FatalErrorException.MSG_INVALID_DVD_FORMAT, ex, path, lineCounter);
			}
		}

		this.pos = pos;
	}

	/**
	 * processTransaction method
	 *
	 * This method checks the current transaction code against the possible transaction types. when the correct transaction type is found the
	 * transaction is sent to the corresponding process method. if the current transaction is 'end' the transaction is processed and then execution
	 * is finished.
	 *
	 * @throws IOException used if there is an error with IO
	 */
	public void processTransaction(Transaction t) throws ConstraintFailureException, ConstraintWarningException
	{
		Transaction.Code c = t.getCode();

		if ( c.equals(Transaction.Code.RENT) )
		{
			processRent(t);
		}
		else if ( c.equals(Transaction.Code.RETURN) )
		{
			processReturn(t);
		}
		else if ( c.equals(Transaction.Code.CREATE) )
		{
			processCreate(t);
		}
		else if ( c.equals(Transaction.Code.ADD) )
		{
			processAdd(t);
		}
		else if ( c.equals(Transaction.Code.REMOVE) )
		{
			processRemove(t);
		}
		else if ( c.equals(Transaction.Code.BUY) )
		{
			processBuy(t);
		}
		else if ( c.equals(Transaction.Code.SELL) )
		{
			processSell(t);
		}
	}

	/**
	 * process rent method
	 *
	 * this method receives a transaction object 't' and creates a MasterDVD object using the title from the transaction object. the method then
	 * updates the quantity for the current dvd in the MasterDVD object. if there is an error with the quantity or the dvd does not exist
	 * the appropriate exception is thrown.
	 *
	 * @param transaction represents the current transaction being processed
	 * @throws ConstraintWarningException
	 * @throws TitleConflictException used if there is an error with the dvd title
	 * @throws InvalidTransactionException used if there is an error with the transaction
	 */
	private void processRent(Transaction transaction) throws ConstraintFailureException, ConstraintWarningException
	{
		if ( !inv.containsKey(transaction.getTitle()) )
			throw new ConstraintWarningException(ConstraintWarningException.MSG_TITLE_DOES_NOT_EXIST, transaction);

		MasterDVD dvd = inv.get(transaction.getTitle());

		if (dvd.getStatus () != DVD.Status.RENTAL)
			throw new ConstraintWarningException(ConstraintWarningException.MSG_INCORRECT_STATUS, transaction);

		int quantity = dvd.getQuantity();
		int rentalQuantity = transaction.getQuantity();

		if ( rentalQuantity <= quantity )
		{
			dvd.setQuantity(quantity-rentalQuantity);
		}
		else
		{
			throw new ConstraintFailureException(ConstraintFailureException.MSG_QUANTITY_BELOW_ZERO, transaction);
		}
	}

	/**
	 * processReturn method
	 *
	 * this method will deal with a return transaction. if the dvd title does not exist within 'names' the method will create a new
	 * dvd object corresponding to the title, generate a unique DVD number that is unused and then put the dvd in the names hashmap. If the dvd
	 * title does exist, a new MasterDVD object is created using the dvd title in question and then updates the Quantity value associated with the
	 * MasterDVD object. if the current dvd quantity plus the return transaction quantity exceed the dvd total quantity then the total quantity is
	 * set the the returnQuantity + current dvd quantity.
	 *
	 * @param transaction represents the current transaction being processed
	 * @throws ConstraintWarningException
	 * @throws TitleConflictException used if there is an error with the dvd title
	 */
	private void processReturn(Transaction transaction) throws ConstraintWarningException
	{
		if ( !inv.containsKey(transaction.getTitle()) )
		{
			int id;

			if ( unused.size() > 0 )
			{
				id = unused.elementAt(0);
				unused.remove(0);
			}
			else
			{
				id = pos;
				pos++;
			}

			MasterDVD dvd = new MasterDVD(DVD.Status.SALE, transaction.getTitle(), transaction.getQuantity(), transaction.getPrice(), id, transaction.getQuantity());

			inv.put(dvd.getTitle(), dvd);
		}
		else
		{
			MasterDVD dvd = inv.get(transaction.getTitle());

			int totalQuantity = dvd.getTotalQuantity();
			int quantity = dvd.getQuantity();
			int returnQuantity = transaction.getQuantity();

			if ( returnQuantity + quantity > MAX_QUANTITY )
				throw new ConstraintWarningException(ConstraintWarningException.MSG_TOTAL_QUANTITY_ABOVE_MAX_QUANTITY, transaction);

			dvd.setQuantity(returnQuantity + quantity);

			if ( returnQuantity + quantity > totalQuantity )
				dvd.setTotalQuantity(returnQuantity + quantity);
		}
	}

	/**
	 * processCreate method
	 *
	 * This method recieves a transaction object and carries out the create process. The method first checks if the dvd title for the transaction exists
	 * within the names hashmap. If the title does not exist the method will generate a unique dvd number, create a MasterDVD object and add the object
	 * to the names hashmap. since this is the create transaction if the title already exists the method throws a titleConflictException
	 *
	 * @param transaction represents the current transaction being processed
	 * @throws ConstraintFailureException
	 * @throws TitleConflictException used if there is an error with the dvd title
	 */
	private void processCreate(Transaction transaction) throws ConstraintFailureException
	{
		if ( !inv.containsKey(transaction.getTitle()) )
		{
			int id;

			if ( unused.size() > 0 )
			{
				id = unused.elementAt(0);
				unused.remove(0);
			}
			else
			{
				id = pos;
				pos++;
			}

			MasterDVD dvd = new MasterDVD(DVD.Status.RENTAL, transaction.getTitle(), transaction.getQuantity(), INITIAL_PRICE, id, transaction.getQuantity());
			inv.put(dvd.getTitle(), dvd);
		}
		else
		{
			throw new ConstraintFailureException(ConstraintFailureException.MSG_TITLE_ALREADY_EXISTS, transaction);
		}
	}

	/**
	 * processAdd Transaction
	 *
	 * The method accepts a transaction object t and then processes the Add transaction. the method first created a MasterDVD object using the
	 * dvd title from the transaction object. the quantity is then updated based on the values for the transaction quantity, the current dvd quantity.
	 * the Master dvd object totalQuantity is set to the current totalqauntity plus the added quantity. the dvd quantity is then set to the quantity
	 * plus the added quantity. if the added quantity causes the total quantity to exceed the boundary value then the method throws the
	 * invalidTransactionException.
	 *
	 * @param transaction t represents the current transaction being processed
	 * @throws TitleConflictException represents an error with the title name
	 * @throws InvalidTransactionException represents and error with the transaction itself
	 */
	private void processAdd(Transaction transaction) throws ConstraintWarningException
	{
		if ( !inv.containsKey(transaction.getTitle()) )
			throw new ConstraintWarningException(ConstraintWarningException.MSG_TITLE_DOES_NOT_EXIST, transaction);

		MasterDVD dvd = inv.get(transaction.getTitle());

		int totalQuantity = dvd.getTotalQuantity();
		int quantity = dvd.getQuantity();
		int addQuantity = transaction.getQuantity();

		if ( totalQuantity + addQuantity <= MAX_QUANTITY )
		{
			dvd.setTotalQuantity(totalQuantity+addQuantity);
			dvd.setQuantity(quantity+addQuantity);
		}
		else
		{
			throw new ConstraintWarningException(ConstraintWarningException.MSG_TOTAL_QUANTITY_ABOVE_MAX_QUANTITY, transaction);
		}
	}

	/**
	 * remove method
	 *
	 * this method effectively removes a given dvd from the system. the method accepts a transaction object and then using that data removes the
	 * corresponding dvd object from the inv hashmap and then removes the unique DVD number from the unused vector
	 *
	 * @param transaction represents the data for the current transaction
	 * @throws TitleConflictException used when system encounters an error with the current transaction
	 * @throws ConstraintFailureExceptiontion
	 * @throws ConstraintWarningException
	 */
	private void processRemove(Transaction transaction) throws ConstraintWarningException
	{
		if ( !inv.containsKey(transaction.getTitle()) )
			throw new ConstraintWarningException(ConstraintWarningException.MSG_TITLE_DOES_NOT_EXIST, transaction);

		MasterDVD dvd = inv.get(transaction.getTitle());

		unused.add(dvd.getNumber());
		inv.remove(dvd.getTitle());
	}

	/**
	 * buy method
	 *
	 * this method will carry out the buy transaction using the transaction object recieved 't'. the method first checks if the dvd from the
	 * transaction can in fact be bought (status == 'sale'). If this is the case the method will then update the corresponding quantity values
	 * in the MasterDVD object that is created using the dvd in question. The method also checks if the sale quantity specified in the transaction
	 * exceeds the number of the dvd that is available, in which case the method throws the invalidTransactionException. if the dvd status is not Sale
	 * the method then throws an invalidTransactionException.
	 *
	 * @param transaction represents the current transaction object for the method
	 * @throws ConstraintWarningException
	 * @throws TitleConflictException used if there is an error with the dvd title in question
	 * @throws InvalidTransactionException used if there is an error with the transaction
	 */
	private void processBuy(Transaction transaction) throws ConstraintFailureException, ConstraintWarningException
	{
		if ( !inv.containsKey(transaction.getTitle()) )
			throw new ConstraintWarningException(ConstraintWarningException.MSG_TITLE_DOES_NOT_EXIST, transaction);

		MasterDVD dvd = inv.get(transaction.getTitle());

		if ( dvd.getStatus().equals(DVD.Status.SALE) )
		{
			int quantity = dvd.getQuantity();
			int totalQuantity = dvd.getTotalQuantity();
			int saleQuantity = transaction.getQuantity();

			if ( saleQuantity <= quantity )
			{
				dvd.setQuantity(quantity-saleQuantity);
				dvd.setTotalQuantity(totalQuantity-saleQuantity);
			}
			else
			{
				throw new ConstraintFailureException(ConstraintFailureException.MSG_QUANTITY_BELOW_ZERO, transaction);
			}
		}
		else
		{
			throw new ConstraintWarningException(ConstraintWarningException.MSG_INCORRECT_STATUS, transaction);
		}
	}

	/**
	 * sell method
	 *
	 * this method effectively carries out the sell transaction for a particular transaction object 't'. The method first creates a MasterDVD object
	 * using the dvd title from the transaction. the method checks if the dvd status is set to SALE in which case the status is set to RENTAL and
	 * the price is set to the INITIAL_PRICE variable. if the status is not SALE, the status is then set to SALE and the price is set to the
	 * transaction value of price.
	 *
	 * @param transaction represents the transaction in question
	 * @throws TitleConflictException used if there is a problem with the given dvd title
	 * @throws ConstraintFailureException
	 */
	private void processSell(Transaction transaction) throws ConstraintWarningException
	{
		if ( !inv.containsKey(transaction.getTitle()) )
			throw new ConstraintWarningException(ConstraintWarningException.MSG_TITLE_DOES_NOT_EXIST, transaction);

		MasterDVD dvd = inv.get(transaction.getTitle());

		DVD.Status status = dvd.getStatus();

		if ( status.equals(DVD.Status.SALE))
		{
			dvd.setStatus(DVD.Status.RENTAL);
			dvd.setPrice(INITIAL_PRICE);
		}
		else
		{
			dvd.setStatus(DVD.Status.SALE);
			dvd.setPrice(transaction.getPrice());
		}
	}

	/**
	 * createMasterDVDFile
	 *
	 * this method is what creates the MasterDVDFile. The method accepts the collection vector and then using the for loop and bufferedWriter object
	 * writes out each collection object using the toString() specified in the MasterDVD class.
	 *
	 * @param collection
	 * @throws IOException
	 */
	public void createMasterDVDFile (String path) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(path));

		Vector<MasterDVD> collection = getVector();

		if ( inv.size() > 0 )
			out.write(collection.get(0).toString());

		for ( int i = 1; i < collection.size(); i++ )
		{
			out.newLine();
			out.write(collection.get(i).toString());
		}

		out.close();
	}

	/**
	 *
	 * CurrentDVDFile method
	 *
	 *  this method is what creates the CurrentDVDFile. The method accepts the collection vector and then using the for loop and bufferedWriter object
	 * writes out each collection object using the toString() specified in the MasterDVD class.
	 *
	 * @param collection
	 * @throws IOException
	 */
	public void createCurrentDVDFile (String path) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(path));

		for ( MasterDVD md : getVector() )
		{
			out.write(md.dvdToString());
			out.newLine();
		}

		//out.write(END_DVD);

		out.close();
	}


	/**
	 *
	 * getDVD method
	 *
	 * This method is used to return a MasterDVD with a given title
	 *
	 * @param title
	 * @throws TitleConflictException
	 */
	public MasterDVD getDVD(String title) throws TitleConflictException
	{
		if ( inv.containsKey(title) )
			return inv.get(title);

		throw new TitleConflictException(TitleConflictException.MSG_TITLE_DOES_NOT_EXIST);
	}

	/**
	 *
	 * getVector method
	 *
	 * This method is used to return a sorted Vector of MaterDVDs
	 *
	 * @param collection
	 * @throws IOException
	 */
	public Vector<MasterDVD> getVector()
	{
		Vector<MasterDVD> collection = new Vector<MasterDVD>();

		for ( String s : inv.keySet() )
		{
			collection.add(inv.get(s));
		}

		Collections.sort(collection);

		return collection;
	}
}
