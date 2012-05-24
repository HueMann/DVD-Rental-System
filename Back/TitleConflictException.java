/**
 * The TitleConflictException class is used to throw exceptions based on the quantity of a DVD
 *
 *
 * @version 0.8 02/24/12
 *
 */

@SuppressWarnings("serial")
/**
 * A Class to represent the TitleConflictException
 */
public class TitleConflictException extends Exception
{

	// error messages
	public static final String MSG_TITLE_DOES_NOT_EXIST = "The specified title does not exist";

	/**
	 * Constructor for the class
	 *
	 * the constructor simply passes the parameter msg to its super class.
	 *
	 * @param msg a String which is passed to the super class
	 */
	public TitleConflictException(String msg)
	{
		super(msg);
	}
}
