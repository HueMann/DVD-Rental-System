/**
 * The ConstraintWarningException class is used to throw exceptions based on the quantity of a DVD
 *
 *
 * @version 0.8 02/24/12
 *
 */

@SuppressWarnings("serial")
/**
 * A Class to represent the ConstraintWarningException
 */
public class ConstraintWarningException extends Exception
{

	public static final String MSG_TITLE_DOES_NOT_EXIST = "The DVD title does not exist so this transaction will not occur";
	public static final String MSG_INCORRECT_STATUS = "The status for this dvd is not proper for this transaction";
	public static final String MSG_TOTAL_QUANTITY_ABOVE_MAX_QUANTITY = "This transaction will force total quantity above the maximum quantity";

	Transaction current;

	/**
	 * Constructor for the class
	 *
	 * the constructor simply passes the parameter msg to its super class.
	 *
	 * @param msg a String which is passed to the super class
	 */
	public ConstraintWarningException(String msg, Transaction t)
	{
		super(msg);
		current = t;
	}
	/**
	 * Constructor for the class
	 *
	 * the constructor simply passes the parameter msg and ex (Exception) to its super class.
	 *
	 * @param msg a String which is passed to the super class
	 * @param ex represents the Exception type
	 */
	public ConstraintWarningException(String msg, Exception ex, Transaction t)
	{
		super(msg, ex);
		current = t;
	}
	/**
	 * getTransaction method
	 *
	 * the method simply returns the current transaction object in use
	 *
	 * @return object of type Transaction
	 */
	public Transaction getTransaction()
	{
		return current;
	}
}
