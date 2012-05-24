/**
 * The ConstraintFailureException class is used to throw exceptions based on the quantity of a DVD
 *
 *
 * @version 0.8 02/24/12
 *
 */

@SuppressWarnings("serial")
/**
 * A Class to represent the ConstraintFailureException
 */
public class ConstraintFailureException extends Exception {

	public static final String MSG_QUANTITY_BELOW_ZERO = "The quantity is below zero so this transaction is invalid";
	public static final String MSG_TITLE_ALREADY_EXISTS = "The title already exists so this transaction will not occur";
	private Transaction current;

	/**
	 * Constructor for the class
	 *
	 * the constructor simply passes the parameter msg to its super class.
	 *
	 * @param msg a String which is passed to the super class
	 */
	public ConstraintFailureException(String msg, Transaction t)
	{
		super(msg);
		current = t;
	}
	/**
	 * Second Constructor for the class
	 *
	 * the constructor simply passes the parameter msg and ex (Exception) to its super class.
	 *
	 * @param msg a String which is passed to the super class
	 * @param ex represents the Exception type
	 */
	public ConstraintFailureException(String msg, Exception ex, Transaction t)
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
