/**
 * The Transaction class holds all pertinent data corresponding to a unique
 * transaction that occurred during the current session. It contains all of
 * the necessary methods for accessing and changing the data being held for
 * this Transaction. The required data for for a complete Transaction is its
 * type, quantity, title, price and status. The type is an enumeration of type
 * Code which can be any of: RENT, RETURN, CREATE, ADD, REMOVE, BUY, SELL and
 * END. The quantity is an in representing the quantity dealt with in this
 * Transaction. The title is a String holding the title of the DVD involved in
 * this Transaction. The price represents the price of the DVD involved in this
 * Transaction. The status is of type DVD.Status which represents the status of
 * the DVD involve din this transaction. A function of not is the toString method
 * which allows the InventoryController to convert a Transaction into a String when
 * writing all Transactions to file.
 *
 *
 * @version 0.8 02/24/12
 *
 */

public class Transaction
{
	//enumeration for the "code" representing what the purpose of a particular transaction is (rent, return, create, add, remove, buy, sell, end)
	public enum Code
	{
		RENT(1), RETURN(2), CREATE(3), ADD(4), REMOVE(5), BUY(6), SELL(7), END(0);

		private int code;

		private Code(int c)
		{
			code = c;
		}

		public int getCode(){
			return code;
		}
	}

	// boundary variables for toString
	private static final int TRANSACTION_CODE_LENGTH = 2;
	private static final int DVD_TITLE_LENGTH = 25;
	private static final int QUANTITY_LENGTH = 3;
	private static final int PRICE_LENGTH = 6;

	// boundary variables for fromString-like constructor
	private static final int CODE_START = 0;
	private static final int CODE_FINISH = 2;
	private static final int TITLE_START = 3;
	private static final int TITLE_FINISH = 28;
	private static final int COPIES_START = 29;
	private static final int COPIES_FINISH = 32;
	private static final int STATUS_POSITION = 33;
	private static final int PRICE_START = 35;
	private static final int PRICE_FINISH = 41;

	private Code type;
	private String title = "";
	private int quantity = 0;
	private double price = 0;
	private DVD.Status status = DVD.Status.EMPTY;

	/**
	 * this first constructor for the transaction class accepts 5 main parameters, code, title, status, quantity, price. it simply initialises
	 * the local variables of the same name to the parameter values.
	 *
	 * @param code represents the dvd code RENT, RETURN, CREATE, ADD, REMOVE, BUY, SELL, END
	 * @param title represents the title of the dvd
	 * @param status represents if the dvd is for sale, rent
	 * @param quantity represents the number of dvds in the transaction
	 * @param price represents the price, if one, for the transaction
	 */
	public Transaction (Code code, String title, DVD.Status status, int quantity, double price)
	{
		type = code;
		this.title = title;
		this.status = status;
		this.quantity = quantity;
		this.price = price;
	}
	/**
	 * The second constructor for the transaction class accepts 4 main parameters, code, title, status, quantity. it simply initialises the local variables of the same name
	 * to the parameter values.
	 *
	 * @param code represents the dvd code RENT, RETURN, CREATE, ADD, REMOVE, BUY, SELL, END
	 * @param title represents the title of the dvd
	 * @param status represents if the dvd is for sale, rent
	 * @param quantity represents the number of dvds in the transaction
	 */
	public Transaction (Code code, String title, DVD.Status status, int quantity)
	{
		type = code;
		this.title = title;
		this.status = status;
		this.quantity = quantity;
	}
	/**
	 * The third constructor for the transaction class accepts 3 main parameters, code, title, status. it simply initialises the local variables of the same name
	 * to the parameter values.
	 *
	 * @param code represents the dvd code RENT, RETURN, CREATE, ADD, REMOVE, BUY, SELL, END
	 * @param title represents the title of the dvd
	 * @param status represents if the dvd is for sale, rent
	 */
	public Transaction (Code code, String title, DVD.Status status)
	{
		type = code;
		this.title = title;
		this.status = status;
	}
	/**
	 * The last constructor for the transaction class accepts 1 main parameter code. it simply initialises the local variable of the same name
	 * to the parameter value.
	 *
	 * @param code represents the dvd code RENT, RETURN, CREATE, ADD, REMOVE, BUY, SELL, END
	 * @param title represents the title of the dvd
	 * @param status represents if the dvd is for sale, rent
	 */
	public Transaction (Code code)
	{
		type = code;
	}

	/**
	 * The last constructor for the transaction class accepts 1 main parameter code. it simply initialises the local variable of the same name
	 * to the parameter value.
	 *
	 * @param code represents the dvd code RENT, RETURN, CREATE, ADD, REMOVE, BUY, SELL, END
	 * @param title represents the title of the dvd
	 * @param status represents if the dvd is for sale, rent
	 * @throws InvalidTransactionException
	 */
	public Transaction (String record) throws NumberFormatException
	{
		int t = Integer.parseInt (record.substring(CODE_START, CODE_FINISH));
		quantity = Integer.parseInt(record.substring(COPIES_START, COPIES_FINISH));
		price = Double.parseDouble(record.substring(PRICE_START, PRICE_FINISH));

		title = record.substring(TITLE_START, TITLE_FINISH);
		title = title.trim();

		char s = (char)Character.toUpperCase(record.charAt(STATUS_POSITION));

		if ( s == DVD.Status.RENTAL.getCode() )
		{
			status = DVD.Status.RENTAL;
		}
		else if ( s == DVD.Status.SALE.getCode() )
		{
			status = DVD.Status.SALE;
		}

		if ( t == Code.RENT.getCode() )
		{
			type = Code.RENT;
		}
		else if ( t == Code.RETURN.getCode() )
		{
			type = Code.RETURN;
		}
		else if ( t == Code.CREATE.getCode() )
		{
			type = Code.CREATE;
		}
		else if ( t == Code.ADD.getCode() )
		{
			type = Code.ADD;
		}
		else if ( t == Code.REMOVE.getCode() )
		{
			type = Code.REMOVE;
		}
		else if ( t == Code.BUY.getCode() )
		{
			type = Code.BUY;
		}
		else if ( t == Code.SELL.getCode() )
		{
			type = Code.SELL;
		}
		else if ( t == Code.END.getCode() )
		{
			type = Code.END;
		}
	}

	/**
	 * toString override method
	 *
	 * The toString class is made to override the java toString() so that the transactions taking place during the code execution and use can be written to an
	 * external file for later use.
	 *
	 * @return returns the transactionlog with the correct records
	 */
	@Override
	public String toString ()
	{
		String transactionLog = "";

		transactionLog += Utility.pad(type.getCode (), TRANSACTION_CODE_LENGTH) + " ";
		transactionLog += Utility.pad(title, DVD_TITLE_LENGTH) + " ";
		transactionLog += Utility.pad(quantity, QUANTITY_LENGTH) + " ";
		transactionLog += status.getCode () + " ";
		transactionLog += Utility.pad(price, PRICE_LENGTH);

		return transactionLog;
	}
	/**
	 * getTitle method
	 *
	 * The getTitle class simply returns the title of the dvd of interest.
	 *
	 * @return return value is the specified title
	 */
	public String getTitle ()
	{
		return title;
	}
	/**
	 * getCode method
	 *
	 * this method simply returns the code type for the transaction
	 *
	 * @return method returns code
	 */
	public Code getCode()
	{
		return type;
	}
	/**
	 * getQuantity method
	 *
	 * this method simply returns the given quantity for a dvd
	 *
	 * @return represents the quantity integer
	 */
	public int getQuantity()
	{
		return quantity;
	}
	/**
	 * getPrice method
	 *
	 * this method simply returns the price for a given dvd
	 *
	 * @return represents the value held by the dvd for price
	 */
	public double getPrice()
	{
		return price;
	}


	/**
	 * equals method
	 *
	 * this method is used to compare another Object to this Transaction by
	 * comparing all of its values and returning true if they are all equal
	 * to this Transactions values, false otherwise
	 *
	 * @return boolean value telling if the given Object equals this Transaction or not
	 * @param Object o
	 */
	@Override
	public boolean equals(Object o)
	{
		//public Transaction (Code code, String title, DVD.Status status, int quantity, double price)
		if ( o instanceof Transaction )
		{
			Transaction t = (Transaction)o;

			if ( t.getCode().getCode() == this.getCode().getCode()
					&& t.getTitle().equals(this.getTitle())
					&& t.getQuantity() == this.getQuantity()
					&& t.getPrice() == this.getPrice())
				return true;
		}

		return false;
	}
}
