// File: Book.java
// Author(s): Mandy Jiang (user:mandyjiang), Ethan Huang
// Purpose: Main Text UI of the library that users will interact with

import java.util.Scanner;

public class MyLibrary {
	
	public static void main(String[] args) {
		showWelcomeMessage();
		Scanner scanner = new Scanner(System.in);
		String input = "";
		promptUser();
		
		while (scanner.hasNextLine()) {
			try {
				input = scanner.nextLine();
				Parser parser = new Parser();
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
	
	private static void showWelcomeMessage() {
		System.out.println("Welcome to your library! Type help for a list of comamnds and exit to quit.");
	}
	
	private static void promptUser() {
		System.out.println("Please input a command to continue.");
	}
}