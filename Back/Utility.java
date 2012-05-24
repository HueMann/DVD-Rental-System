/**
 * The Utility class is used as an external class that holds any functions
 * necessary, but not directly related to the intent of another class.
 * Currently it only holds three methods, all of which create a String padded
 * with either zeros or spaces, depending on the data type involved.
 *
 *
 */

import java.text.DecimalFormat;


public class Utility
{

	/**
	 * this method left justifies a String by right padding it with spaces.
	 *
	 * The method receives a String and an int representing the title of an item and the
	 * length of the String that is returned, respectively. It then creates a String by adding
	 * spaces to the end of the given title and returns the created String.
	 *
	 * @param title is a String that represents the title of an item
	 * @param length is the length we want the returning string to be after being padded
	 * @return a formatted String of that has a length equal to length
	 */
	public static String pad(String title, int length)
	{
		String format = "";

		for (int i = 0; i < (length - title.length ()); i++)
		{
			format += " ";
		}

		format = title+format;

		return format;
	}

	/**
	 * this method right justifies a String by left padding it with zeros.
	 *
	 * The method receives two integers representing the quantity of an item and the
	 * length of the String that is returned. It then creates a String by adding
	 * spaces to the beginning of the given value and returns the created String.
	 *
	 * @param value is a int that represents the quantity of an item
	 * @param length is the length we want the returning string to be after being padded
	 * @return a formatted String of that has a length equal to length
	 */
	public static String pad(int value, int length)
	{
		String format = "";
		String number = Integer.toString(value);

		for (int i = 0; i < (length - number.length ()); i++)
		{
			format += "0";
		}

		format += number;
		return format;
	}

	/**
	 * this method right justifies a double by left padding it with zeros.
	 *
	 * The method receives a double and an int representing the price of an item and the
	 * length of the String that is returned, respectively. It then creates a String by adding
	 * zeros to the beginning of the given value and returns the created String.
	 *
	 * @param value is a double that represents the price of an item
	 * @param length is the length we want the returning string to be after being padded
	 * @return a formatted String of that has a length equal to length
	 */
	public static String pad(double value, int length)
	{

		String format = "";

		if ( length < 3 )
			throw new NumberFormatException("Length provided must be greater than 3");

		for ( int i = 0; i < length -3; i++ )
		{
			format += '0';
		}

		DecimalFormat df = new DecimalFormat(format + ".00");

		return df.format(value);

	}
}
