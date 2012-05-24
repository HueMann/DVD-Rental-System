/**
 * The FatalErrorException class is used to throw exceptions based on the quantity of a DVD
 *
 *
 * @version 0.8 02/24/12
 *
 */

@SuppressWarnings("serial")
/**
 * A Class to represent the FatalErrorException
 */
public class FatalErrorException extends Exception
{
	//exception messages
	public static final String MSG_UNEXPECTED_END_OF_FILE = "The file has ended unexpectedly";
	public static final String MSG_INVALID_DVD_FORMAT = "This DVD has an incorrect format";
	public static final String MSG_INVALID_TRANSACTION_FORMAT = "This Transaction has an incorrect format";


	private String path;
	private int line;

	/**
	 * Constructor for the class
	 *
	 * the constructor simply passes the parameter msg to its super class.
	 *
	 * @param msg a String which is passed to the super class
	 */
	public FatalErrorException(String msg, String path, int line)
	{
		super(msg);
		this.path = path;
		this.line = line;
	}
	/**
	 * second Constructor for the class
	 *
	 * the constructor simply passes the parameter msg, ex, path and line to its super class.
	 *
	 * @param msg a String which is passed to the super class
	 * @param ex represents the Exception type
	 * @param path represents the current file path for which an exception is thrown
	 * @param line represents the current line number within the file
	 */
	public FatalErrorException(String msg, Exception ex, String path, int line)
	{
		super(msg, ex);
		this.path = path;
		this.line = line;
	}
	/**
	 * getPath method
	 *
	 * this method simply returns the current file path
	 *
	 * @return a string path representing the file path
	 */
	public String getPath()
	{
		return path;
	}
	/**
	 * getLine method
	 *
	 * simply returns the current line number within the given file
	 *
	 * @return an integer representing the line number
	 */
	public int getLine()
	{
		return line;
	}
}
