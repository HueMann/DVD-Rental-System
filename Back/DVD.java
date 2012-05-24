/**
 * The DVD class holds all pertinent data corresponding to a unique DVD. This
 * class holds all the needed methods to access the DVDs data and set that data.
 * The four base elements for a DVD are the status, title, quantity and price.
 * The status is an enumeration of type Status which has three values: RENTAL,
 * SALE and EMPTY. The title is a String representing the name of the DVD. The
 * quantity is an int which holds the quantity of DVDs available. The price
 * variable is a double, which holds the price that the DVD should be sold at.
 *
 *
 * @version 0.8 02/24/12
 *
 */

public class DVD {

	private static final int QUANTITY_LENGTH = 3;
	private static final int PRICE_LENGTH = 6;
	private static final int TITLE_LENGTH = 25;

	//enumeration representing the status for a dvd (rental, sale, empty)
	public enum Status
	{
		RENTAL('R'), SALE('S'), EMPTY(' ');

		private char code;

		private Status(char c)
		{
			code = c;
		}

		public char getCode()
		{
			return code;
		}
	}
	public static final double MAX_PRICE = 150;
	public static final int MAX_TITLE_LENGTH = 25;

	private Status status;
	private String title;
	private int quantity;
	private double price;


	/**
	 * this method is the constructor for this DVD class.
	 *
	 * The method receives multiple parameters which are then allocated to local variables to represent the DVD credentials within the system.
	 *
	 * @param status represents the status rent or sale for the dvd
	 * @param title represents the title for the dvd
	 * @param quantity represents the number of copies for the dvd
	 * @param price represents the price for the dvd
	 */
	public DVD (Status status, String title, int quantity, double price)
	{
		this.status = status;
		this.title = title;
		this.quantity = quantity;
		this.price = price;
	}
	/**
	 * DVD constructor
	 *
	 * and empty DVD constructor
	 *
	 */
	public DVD()
	{

	}

	/**
	 * Method to receive the status for this instance of DVD
	 *
	 * The method is used from other classes to retrieve the dvd status
	 *
	 */
	public Status getStatus()
	{
		return status;
	}

	/**
	 * Method to receive the title for this instance of DVD
	 *
	 * The method is used from other classes to retrieve the dvd title
	 **/
	public String getTitle() {
		return title;
	}
	/**
	 * Method to receive the price for this instance of DVD
	 *
	 * The method is used from other classes to retrieve the dvd price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Method to receive the quantity for this instance of DVD
	 *
	 * The method is used from other classes to retrieve the dvd quantity
	 */
	public int getQuantity() {
		return quantity;
	}



	/**
	 * Method to set the quantity of a DVD
	 *
	 * The method receives the integer quantity and simply sets the local variable quantity to the parameter value quantity.
	 *
	 * @param quantity represents the desired integer value which will be used to set the local variable quantity.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	/**
	 * Method to set the status of a DVD
	 *
	 * The method receives the DVD.Status status and simply sets the local variable status to the parameter value status.
	 *
	 * @param status represents the desired DVD.Status value which will be used to set the local variable status.
	 * @throws InvalidDVDStatusException
	 */
	public void setStatus(DVD.Status status) {

		this.status = status;
	}

	/**
	 * Method to set the price of a DVD
	 *
	 * The method receives the price per unit and simply sets the local variable price to the parameter value price.
	 *
	 * @param price represents the desired price per unit value which will be used.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * setTitle method
	 *
	 * this method simply recieves the parameter title of type String, and initialized the local variable title to the parameter value
	 *
	 * @param title String value for the specified dvd title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	/**
	 * toString() override method
	 *
	 * Returns a String containing all of this DVD's information in the proper format to write to a currentDVD file
	 *
	 */
	@Override
	public String toString()
	{
		int qty = quantity;

		if  ( qty > 999 )
			qty = 999;

		return Utility.pad(title, TITLE_LENGTH) + ' ' + Utility.pad(qty, QUANTITY_LENGTH) + ' ' + status.getCode() + ' ' + Utility.pad(price, PRICE_LENGTH);
	}

	/**
	 * equals method
	 *
	 * this method is used to compare another Object to this DVD by
	 * comparing all of its values and returning true if they are all equal
	 * to this DVDs values, false otherwise
	 *
	 * @return boolean value telling if the given Object equals this Transaction or not
	 * @param Object o
	 */
	@Override
	public boolean equals(Object o)
	{
		if ( o instanceof DVD )
		{
			DVD dvd = (DVD)o;

			if ( dvd.getPrice() == this.getPrice()
					&& dvd.getQuantity() == this.getQuantity()
					&& dvd.getStatus().getCode() == this.getStatus().getCode()
					&& dvd.getTitle().equals(this.getTitle()) )
				return true;
		}

		return false;
	}
}
