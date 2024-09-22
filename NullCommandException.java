// File: Command.java
// Author(s): Mandy Jiang (mxndyj), Ethan Huang (remy9926)
// Purpose: If a user specifies null as the command, this error will be thrown to notify the user


/*
 * This Class is an exception that can be thrown by the Parser Class when the user input read by
 * the Parser is null. To prevent the Parser from interpreting a null input, there is a guard
 * clause within the Parser Class that detects if the user input is null, and if so it will
 * immediately throw this error and let the user know what went wrong.
 */

public class NullCommandException extends Exception {
	public NullCommandException(String input) {
		super("You cannot input null as the command!");
	}
}
