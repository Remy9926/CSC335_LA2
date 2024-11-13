package src;

// File: Command.java
// Author(s): Mandy Jiang (mandyjiang), Ethan Huang (ehuang68)
// Purpose: Represents the different commands that a user can input

/*
 * This enum is representative of the different types of command that the Parser Class can
 * interpret from the reader. While the Enum does not have any functions itself, the Parser
 * Class will know how to prompt the user or what messages to display depending on the type
 * of Command that is currently associated with the Parser, which is achieved everytime the
 * user gives input into the UI.
 */




public enum Command {
	SEARCH,
	ADD_BOOK,
	SET_TO_READ,
	RATE,
	GET_BOOKS,
	SUGGEST_READ,
	ADD_BOOKS,
	HELP,
	EXIT,
	INVALID
}
