// File: Book.java
// Author(s): Mandy Jiang (mandyjiang), Ethan Huang (ehuang68)
// Purpose: Main Text UI of the library that users will interact with

import java.util.Scanner;

/*
 * The MyLibrary Class acts as the UI that a user would interact with to manage their
 * library. It does not contain any methods to directly interact with the LibraryModel
 * Class. This allows for the MyLibrary class to strictly function as the UI and any
 * user input will be read by the Parser Class, which will then interpret the command and
 * prompt the user as necessary to change the library. Encapsulation is achieved by having
 * the MyLibrary Class focus on solely the UI and include methods that display messages to
 * the user.
 */

public class MyLibrary {
	
	public static void main(String[] args) {
		showWelcomeMessage();
		Scanner scanner = new Scanner(System.in);
		String input = "";
		Parser parser = new Parser();
		promptUser();
		
		while (scanner.hasNextLine()) {
			try {
				input = scanner.nextLine();
				parser.setCommand(input);
				int exit = parser.executeCommand(scanner);
				
				if (exit == 1) {
					break;
				}
			}
			catch (Exception e) {
				System.out.println(e);
			}
			promptUser();
		}
		scanner.close();
	}
	
	/*
	 * Displays the welcome message to the user
	 * 
	 */
	
	private static String showWelcomeMessage() {
		System.out.println("Welcome to your library! Type help for a list of comamnds and exit to quit.");
		return "Welcome to your library! Type help for a list of commands and exit to quit.";

	}
	
	/*
	 * Asks the user to input a command for the Parser to read in
	 */
	
	private static void promptUser() {
		System.out.println("Please input a command to continue: ");
	}
}