/**	MohaWK DVD Rental Systems
 *	User.java
 *	@author Kelley, Christopher
 *	@author McAlear, Cassandra
 *	@author Watson, Ryan
 *	@version 1.04 Mar. 04, 2012
 */

import java.io.*;

/**	User Interface is skeleton code for the default and admin objects
 *	
 */
interface User{

	/**	rent Method for the user to rent a DVD
	 *	
	 *	@param file the parsed currentDVDFile data
	 *	@throws IOException to prevent the user from crashing the system
	 */
	public int rent(DVD_File file, long lastCommandTime) throws IOException;
	
	/**	returns Method for the user to return a DVD
	 *	
	 *	@param file the parsed currentDVDFile data
	 *	@throws IOException to prevent the user from crashing the system
	 */
	public int returns(DVD_File file, long lastCommandTime) throws IOException;
	
	/**	buy Method for the user to buy a DVD
	 *	
	 *	@param file the parsed currentDVDFile data
	 *	@throws IOException to prevent the user from crashing the system
	 */
	public int buy(DVD_File file, long lastCommandTime) throws IOException;
	
	/**	logout Method for the user to logout
	 *	
	 *	@return returns a boolean indicating the change in user state
	 */
	public void logout();
	
}
