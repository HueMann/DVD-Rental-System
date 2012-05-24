/**
 * The MasterDVD class extends the DVD class and implements Comparable. This class accepts a record from the Library class and parses the record into the
 * appropriate local variables. The method provides all required get/set methods used by other classes. The class also provides it unique toString method for
 * use to writing to the MasterDVD file.
 *
 *
 * @version 0.8 02/24/12
 *
 */
public class MasterDVD extends DVD implements Comparable<MasterDVD>
{

	// boundary variables for toString
	private static final int NUMBER_LENGTH = 5;
	private static final int QUANTITY_LENGTH = 4;
	private static final int TOTAL_QUANTITY_LENGTH = 4;
	private static final int PRICE_LENGTH = 6;
	private static final int TITLE_LENGTH = 25;

	// boundaries for fromString-like constructor
	private static final int NUMBER_START = 0;
	private static final int NUMBER_FINISH = 5;
	private static final int QUANTITY_START = 6;
	private static final int QUANTITY_FINISH = 10;
	private static final int TOTAL_QUANTITY_START = 11;
	private static final int TOTAL_QUANTITY_FINISH = 15;
	private static final int STATUS_POSITION = 16;
	private static final int PRICE_START = 18;
	private static final int PRICE_FINISH = 24;
	private static final int TITLE_START = 25;
	private static final int TITLE_FINISH = 50;

	private int number;
	private int totalQuantity;
	/**
	 * Constructor for MasterDVD
	 *
	 * This is the first constructor for the MasterDVD class which accepts the dvd status, title, quantity, price, number and
	 * total quantity for a particular dvd object. The constructor then initialises the local variables number and total quantity to
	 * their corresponding parameter values.
	 */
	public MasterDVD(Status status, String title, int quantity, double price, int number, int totalQuantity)
	{
		super(status, title, quantity, price);
		// TODO Auto-generated constructor stub
		this.number = number;
		this.totalQuantity = totalQuantity;
	}
	/**
	 * Represents the second constructor for MasterDVD
	 *
	 * This constructor will receive a record which represents 1 line in the master DVD file. this line is then parsed into the corresponding elements (number, quantity,
	 * total quantity, price, Status and title). If the values are invalid the method will throw the InvalidDVDException. The constructor then send the status, title,
	 * quantity and price to the corresponding "set" methods.
	 * @param record The record parameter represents 1 line in the master DVD file
	 * @throws InvalidDVDException the exception catches any false data while a record is being parsed
	 * @throws FatalErrorException
	 */
	public MasterDVD(String record) throws  NumberFormatException
	{
		int quantity;
		double price;


			number = Integer.parseInt (record.substring(NUMBER_START, NUMBER_FINISH));
			quantity = Integer.parseInt(record.substring(QUANTITY_START, QUANTITY_FINISH));
			totalQuantity = Integer.parseInt(record.substring(TOTAL_QUANTITY_START, TOTAL_QUANTITY_FINISH));
			price = Double.parseDouble(record.substring(PRICE_START, PRICE_FINISH));


		char s = (char)Character.toUpperCase(record.charAt(STATUS_POSITION));
		String title = record.substring(TITLE_START, TITLE_FINISH);
		title = title.trim();

		DVD.Status status = DVD.Status.EMPTY;

		if ( s == DVD.Status.RENTAL.getCode() )
		{
			status = DVD.Status.RENTAL;
		}
		else if ( s == DVD.Status.SALE.getCode() )
		{
			status = DVD.Status.SALE;
		}

		setStatus (status);
		setTitle (title);
		setQuantity (quantity);
		setPrice (price);
	}

	/**
	 * Overrides the java default toString method
	 *
	 * This toString() writes out to the masterDVD file
	 */
	@Override
	public String toString ()
	{
		String masterLog = "";

		masterLog += Utility.pad(number, NUMBER_LENGTH) + " ";
		masterLog += Utility.pad(getQuantity (), QUANTITY_LENGTH) + " ";
		masterLog += Utility.pad(totalQuantity, TOTAL_QUANTITY_LENGTH) + " ";
		masterLog += getStatus().getCode () + " ";
		masterLog += Utility.pad(getPrice (), PRICE_LENGTH) + " ";
		masterLog += Utility.pad(getTitle (), TITLE_LENGTH);

		return masterLog;
	}
	/**
	 * superToString method
	 *
	 * method to return the super class toString() cmd
	 * @return represents the super.toString() method return
	 */
	public String dvdToString()
	{
		return super.toString();
	}

	/**
	 * getNumber method
	 *
	 * returns the value held by the variable number
	 * @return value held by number
	 */
	public int getNumber()
	{
		return number;
	}
	/**
	 * setNumber method
	 *
	 * sets the local variable number to the value given by the parameter.
	 * @param number represents the dvd number
	 */
	public void setNumber( int number )
	{
		this.number = number;
	}

	/**
	 * getTotalQuantity method
	 *
	 * returns the value held by totalQuantity
	 * @return the value held by totalQuantity
	 */
	public int getTotalQuantity()
	{
		return totalQuantity;
	}

	/**
	 * setTotalQuantity method
	 *
	 * accepts the totalQuantity parameter and sets the local totalQuantity variable to this value.
	 * @param totalQuantity represents the total quantity of a dvd in the system
	 */
	public void setTotalQuantity( int totalQuantity )
	{
		this.totalQuantity = totalQuantity;
	}

	/**
	 * Overrides the compareTo default method
	 *
	 * recieves a dvd and returns true or false based on whether the current number and the dvd objects number are equal or not.
	 */
	@Override
	public int compareTo(MasterDVD dvd) {

		return this.number - dvd.getNumber() ;
	}

	/**
	 * equals method
	 *
	 * this method is used to compare another Object to this MasterDVD by
	 * comparing all of its values and returning true if they are all equal
	 * to this MasterDVDs values, false otherwise
	 *
	 * @return boolean value telling if the given Object equals this Transaction or not
	 * @param Object o
	 */
	@Override
	public boolean equals(Object o)
	{
		if ( o instanceof MasterDVD )
		{
			MasterDVD dvd = (MasterDVD)o;

			if ( dvd.getNumber() == this.getNumber()
					&& dvd.getPrice() == this.getPrice()
					&& dvd.getQuantity() == this.getQuantity()
					&& dvd.getStatus().getCode() == this.getStatus().getCode()
					&& dvd.getTitle().equals(this.getTitle())
					&& dvd.getTotalQuantity() == this.getTotalQuantity() )
				return true;
		}

		return false;
	}
}
