// File: Command.java
// Author(s): Mandy Jiang (user:mandyjiang), Ethan Huang
// Purpose: If a user specifies null as the command, this error will be thrown to notify the user

public class NullCommandException extends Exception {
	public NullCommandException(String input) {
		super("You cannot input null as the command!");
	}
}
