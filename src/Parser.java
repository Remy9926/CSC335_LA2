import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// File: Parser.java
// Author(s): Mandy Jiang (user:mandyjiang), Ethan Huang
// Purpose: Parses the user input to validate and execute commands as necessary

public class Parser {
	
	private LibraryModel model;
	private Command command;
	private SearchType searchType;
	private String[] params;
	
	public Parser() {
		this.model = new LibraryModel();
	}
	
	void setCommand(String command) throws NullCommandException {
		if (command == null) {
			throw new NullCommandException(command);
		}
		
		String cleanedCommand = cleanInput(command);

		switch (cleanedCommand) {
			case "search":
				this.command = Command.SEARCH;
				break;
			
			case "addBook":
				this.command = Command.ADD_BOOK;
				break;
			
			case "setToRead":
				this.command = Command.SET_TO_READ;
				break;
				
			case "rate":
				this.command = Command.RATE;
				break;
				
			case "getBooks":
				this.command = Command.GET_BOOKS;
				break;
				
			case "suggestRead":
				this.command = Command.SUGGEST_READ;
				break;
				
			case "addBooks":
				this.command = Command.ADD_BOOKS;
				break;
				
			case "help":
				this.command = Command.HELP;
				break;
				
			case "exit":
				this.command = Command.EXIT;
				break;
			
			default:
				this.command = Command.INVALID;
				break;
		}
	}
	
	int executeCommand(Scanner scanner) {

		switch (this.command) {
			case SEARCH:
				System.out.print("Please input your search type (title, author, rating): ");
				String search = scanner.nextLine();
				SearchType searchType = getSearchType(search);
				
				if (searchType == SearchType.NULL) {
					System.out.println("Invalid search type specified!");
					break;
				}
				
				break;
			
			case ADD_BOOK:
				this.command = Command.ADD_BOOK;
				System.out.print("Please input your search type (title, author, rating): ");
				
				System.out.print("Please input your search type: ");
				break;
			
			case SET_TO_READ:
				this.command = Command.SET_TO_READ;
				break;
				
			case RATE:
				this.command = Command.RATE;
				break;
				
			case GET_BOOKS:
				this.command = Command.GET_BOOKS;
				break;
				
			case SUGGEST_READ:
				this.command = Command.SUGGEST_READ;
				break;
				
			case ADD_BOOKS:
				this.command = Command.ADD_BOOKS;
				System.out.println("What is the name of the file that you want to read from?");
				String fileName = scanner.nextLine();
				File file = new File(fileName);
				
				if (!file.exists()) {
					System.out.println("Sorry, the file you specified does not exist!");
				}
				try {
					Scanner readFile = new Scanner(file);
					
					while (readFile.hasNextLine()) {
						String line = readFile.nextLine();
						String[] splitLine = line.split(";");
						String title = splitLine[0];
						String author = splitLine[1];
						Book newBook = new Book(title, author);
						model.addBookToLibrary(newBook);
					}
					
					readFile.close();
					System.out.println("All books from " + fileName + " have been added to your library!");
				}
				catch (FileNotFoundException e) {
					System.out.println("Sorry, the file could not be found");
				}
				break;
				
			case HELP:
				this.command = Command.HELP;
				displayHelpMessage();
				break;
				
			case EXIT:
				System.out.println("See you soon!");
				System.out.println("Exiting...");
				return 1;
			
			default:
				this.command = Command.INVALID;
				System.out.println("Sorry, I do not understand your command. Press help for the list of commands.");
				break;
		}
		return 0;
	}
	
	private String cleanInput(String input) {
		return input.trim();
	}
	
	private SearchType getSearchType(String searchType) {
		switch (searchType) {
			case "title":
				return SearchType.TITLE;
				
			case "author":
				return SearchType.AUTHOR;
				
			case "rating":
				return SearchType.RATING;
				
			default:
				return SearchType.NULL;
		}
	}

	private void displayHelpMessage() {
		System.out.println("All commands are case sensitive!\n");
		System.out.println("search- specify whether to search by title, author, or rating and the results will be returned to you");
		System.out.println("addBook- specify the title, author, rating, and whether or not you have read the book and adds it to your library");
		System.out.println("setToRead- specify the title and author of the book you want to set to read");
		System.out.println("rate- specify the title and author of the book you want to rate from 1-5 with default ratings set to 0");
		System.out.println("getBooks- retrieves all books sorting by title or author, or only read/unread books");
		System.out.println("suggestRead- picks a random unread book from the library");
		System.out.println("addBooks- specify a file name, and all books from that file will be read into the libray\n");
	}
}
