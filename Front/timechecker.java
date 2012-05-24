/**	MohaWK DVD Rental Systems
 *	timechecker.java
 *	@author Kelley, Christopher
 *	@author McAlear, Cassandra
 *	@author Watson, Ryan
 *	@version 1.00 Mar. 4, 2012
 */

/**	Class Timechecker houses a timechecker object which is used to confirm 
 *	whether a user has gone over the session timeout limit.
 */

class Timechecker{

	long currentTime;		//The current time for the system
	long lastCommandTime;	//The time for the last command entered by the user
	Messages message;

	final long timeoutAmount = 60000; //Time to session timeout in ms (60000)

	Timechecker(){
		currentTime = System.currentTimeMillis();
		lastCommandTime = currentTime;
		message = new Messages();
	}

	/**	timeoutCheck Method used to determine if the user is absent from the 
	 *	console, and thereby timeout their session
	 */
	public boolean timeoutCheck(){

		currentTime = System.currentTimeMillis();
		boolean state = true;

		if (currentTime - lastCommandTime > timeoutAmount){

			System.out.println(message.TIME_OUT);
			lastCommandTime = currentTime;
			state = false; // changing the check state

		}else{

			lastCommandTime = currentTime;

		}

		return state;

	}

}
