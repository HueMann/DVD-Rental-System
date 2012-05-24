/**	MohaWK DVD Rental Systems
 *	messages.java
 *	@author Kelley, Christopher
 *	@author McAlear, Cassandra
 *	@author Watson, Ryan
 *	@version 1.04 Mar. 04, 2012
 */

/**	Messages Class houses all the various prompts and messages displayed to the 
 *	user in one easy to modify locale.
 */
class Messages{
	
	//Prompt messages
	public final String WELCOME = "Welcome to MohaWK DVD rental. Please type"
						+ " \"login\".";
	public final String USER_TYPE = "Enter user type \"standard\"/\"admin\": ";
	public final String USER_COMMANDS = "Commands: \"rent\", \"return\","
						+ " \"buy\", \"logout\"";
	public final String ADMIN_COMMANDS = "Commands: \"create\", \"add\","
						+ " \"remove\", \"rent\", \"return\", \"sell\", \"buy\""
						+ ", \"logout\"";
	public final String ENTER_TITLE = "Enter DVD title: ";
	public final String ENTER_COPIES = "Enter number of copies: ";
	public final String SALE_PRICE = "Enter sale price without '$': ";
	public final String UNIT_COST = /*DVDNAME*/ " costs " /*$X*/;
	public final String UNIT_COST2 = " each, for a total of " /*$X*n*/;
	public final String CONFIRM_PURCH = "Confirm purchase: \"yes\"/\"no\"";
	public final String CONFIRM_STAT = "Confirm status change: \"yes\"/\"no\"";

	//Explanation messages
	public final String WILL_ADD = "This transaction will add copies to an "
						+ "existing DVD title.";
	public final String WILL_BUY_ADM = "This transaction will purchase copies "
						+ "of a DVD title.";
	public final String WILL_BUY_STD = "This transaction will purchase up to 5 "
						+ "copies of a DVD title.";
	public final String WILL_CREATE = "This transaction will create a new DVD "
						+ "title in the system.";
	public final String WILL_REMOVE = "This transaction will remove a DVD title"
						+ " from the system.";
	public final String WILL_RENT = "This transaction will rent up to 3 copies "
						+ "of a DVD title.";
	public final String WILL_RETURN = "This transaction will return up to 3 "
						+ "copies of a DVD title.";	
	public final String WILL_RENTAL = "This transaction will change the status "
						+ "of a DVD from 'sale' to 'rental'.";
	public final String WILL_SALE = "This transaction will change the status of"
						+ " a DVD from 'rental' to 'sale'.";
	public final String WILL_SELL = "This transaction will change the status of"
						+ " a DVD.";
	
	//Error messages
	public final String INV_COMMAND = "ERROR invalid command";
	public final String INV_USER = "ERROR invalid user type";
	public final String TOO_MANY_CHAR = "ERROR too many characters";
	public final String INV_NAME = "ERROR \" \" is invalid as a name";
	public final String NAME_TAKEN = "ERROR name already taken";
	public final String INV_COPIES = "ERROR invalid number of copies";
	public final String TOTAL_999 = "ERROR total exceeds 999";
	public final String NAME_NOT_FOUND = "ERROR name not found";
	public final String PRIV_TRANS = "ERROR privileged transaction";
	public final String NO_COPIES = "ERROR no copies available";
	public final String NOT_ENOUGH = "ERROR requested amount exceeds copies "
						+ "available";
	public final String PRICE_EXCEED = "ERROR sales price exceeds $150.00";
	public final String INV_PRICE = "ERROR sales price invalid";
	public final String DVD_UNAVAILABLE = "ERROR DVD unavailable until next "
						+ "session";
	public final String INV_CONFIRM = "ERROR invalid confirmation";
	public final String TIME_OUT = "ERROR session has timed out";
	public final String INV_RENTAL = "ERROR DVD only available through "
						+ "purchase";
	public final String INV_SALE = "ERROR DVD only available through rental";
	public final String IO_ERROR = "ERROR Bad IO";
	
	//Success messages
	public final String CREATE_SUCCESS = "DVD creation successful";
	public final String ADD_SUCCESS = "DVD addition successful";
	public final String REMOVE_SUCCESS = "DVD removal successful";
	public final String RENT_SUCCESS = "DVD rental successful";
	public final String RETURN_SUCCESS = "DVD return successful";
	public final String STAT_CHANGE_SUCCESS = "DVD status change successful";
	public final String PURCH_SUCCESS = "DVD purchase successful";

	public final String CONFIRM_FAIL = "Transaction cancelled";
	public final String THANK_YOU = "Thank you for using MohaWK DVD rental. "
						+ "Goodbye.";
	public final String GOOD_NIGHT = "Goodnight.";
	
}
