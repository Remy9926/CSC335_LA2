package src;

// File: Parser.java
// Author(s): Mandy Jiang (mandyjiang), Ethan Huang (ehuang68)
// Purpose: Parses the user input to validate and execute commands as necessary

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

/*
 * The Parser Class acts as the intermediary between the MyLibrary and LibraryModel Classes.
 * Parser will take in the user's input and interpret what command the user is trying to perform
 * and ask for more information from the user depending on the command, or let the user know that
 * an error occurred because of some kind of invalid input. Encapsulation is achieved by not
 * keeping the LibraryModel object in a private variable as well as the command that is to be
 * executed in a private variable so that no other classes have access to this information.
 */

public class Parser {
	
	private LibraryModel model;
	private Command command;
	private GUI gui;
	
	public Parser() {
		this.model = new LibraryModel();
		this.gui = new GUI(model, this);
	}
	
	/*
	 * Reads in the input from the user to set the command variable of the parser class
	 * to be executed in a subsequent step.
	 * 
	 * @throws NullCommandException if the user inputs null as the command
	 * 
	 * @param command the command that the user inputs to be interpreted by the Parser
	 * 
	 */
	
	public void setCommand(String command) throws NullCommandException {
		if (command == null) {
			throw new NullCommandException(command);
		}
		
		String cleanedCommand = cleanInput(command);

		switch (cleanedCommand) {
			// map input to correspond enum command
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
	
	/*
	 * Executes a command based on what the command instance variable is set to
	 * from the user's previous command input. If the command variable is set to
	 * Command.EXIT, then the program will return with 1 to indicate that the user
	 * wants to exit, which will be interpreted by the MyLibrary Class.
	 * 
	 * @param scanner the scanner object to read in user input from stdin
	 * 
	 * @return 0 if the user wants to exit, 1 if the user does not want to exit
	 * 
	 */
	
	public int executeCommand(Scanner scanner) {
		
		ArrayList<Book> books = new ArrayList<Book>();

		switch (this.command) {
			case SEARCH:
				System.out.print("Please enter your search type (title/author/rating): ");
				String searchType = cleanInput(scanner.nextLine().toLowerCase());
				//search by title
				if (searchType.equals("title")) {
					System.out.println("Please enter the title of the book: ");
					String title = cleanInput(scanner.nextLine());
					books = model.searchBooksByTitle(title.toLowerCase());
				}
				//search by author
				if (searchType.equals("author")) {
					System.out.println("Please enter the author of the book: ");
					String author = cleanInput(scanner.nextLine());
					books = model.searchBooksByAuthor(author.toLowerCase());
				}
				//search by rating
				if (searchType.equals("rating")) {
					System.out.println("Please enter the rating of the book (1-5): ");
					int rating = Integer.parseInt(cleanInput(scanner.nextLine()));
					books = model.searchBooksByRating(rating);
					
					if (books == null) {
						System.out.println("Invalid rating given");
						break;
					}
				}
				
				if (books.isEmpty()) {
					System.out.println("No books found that match your criteria");
				} else {
					System.out.println("Here are the books that match your criteria: ");
					for (Book b: books) {
						System.out.println(b);
					}
				}
				
				break;
			
			case ADD_BOOK:
				System.out.print("Please enter the book's title: ");
				String title = cleanInput(scanner.nextLine());
				
				System.out.print("Please enter the book's author: ");
				String author = cleanInput(scanner.nextLine());

				Book newBook = new Book(title, author);
				boolean added= model.addBookToLibrary(newBook);
				if (added){
					System.out.println("Book added successfully: " + newBook);

				}else{
					System.out.println("Error, cannot be added. Book is already in the library.");
				}
				
				break;
			
			case SET_TO_READ:
				System.out.print("Please enter the title of the book to mark as read: ");
				String bookTitle = cleanInput(scanner.nextLine());  
				
				System.out.println("Please enter the book author: ");
				String bookAuthor= cleanInput(scanner.nextLine());

				boolean success = model.setToRead(bookTitle,bookAuthor);  
				//validation 
				if (success) {
					System.out.println("Book marked as read.");
				} else {
					System.out.println("Book not found or already read.");
				}
				break;
				
				
			case RATE:
				System.out.print("Please enter the title of the book you want to rate: ");
				String booksTitle =  cleanInput(scanner.nextLine());  
				
				System.out.print("Please enter the book author: ");
				String booksAuthor  = cleanInput(scanner.nextLine());
				
				System.out.print("Please enter a rating (1-5): ");
				int rating = Integer.parseInt(cleanInput(scanner.nextLine())); 
				boolean successful = model.rateBook(booksTitle, rating,booksAuthor);  
				// validation
				if (successful) {
					System.out.println("Book successfully rated.");
				} else {
					System.out.println("Book not found or invalid rating.");
				}
				break;
				
			case GET_BOOKS:
				System.out.print("How would you like to sort your books by? (title/author/read/unread): ");
				String option = cleanInput(scanner.nextLine().toLowerCase()); 
				
				books = model.getBooks(option);
				
				if (books != null && !books.isEmpty()) {
					for (Book book : books) {
						System.out.println(book); 
					}
				} else {
					System.out.println("No books found or sort option invalid.");
				}
				break;

				
			case SUGGEST_READ:
				Book suggestedBook = model.suggestBook();
				
				if (suggestedBook == null) {
					System.out.println("You have already read all books in your library!");
				} else {
					 System.out.println(suggestedBook);
				}
				break;
				
			case ADD_BOOKS:
				System.out.println("What is the name of the file that you want to read from?");

				String fileName = cleanInput(scanner.nextLine()); 
				File file = new File(fileName);

				if (!file.exists() ) {
					System.out.println("The file you input does not exist");
					break;
				}

				try {
					Scanner readFile = new Scanner(file);
					while (readFile.hasNextLine()) {
						String line = cleanInput(readFile.nextLine());
						String[] splitLine = line.split(";");
						String title1= splitLine[0];
						String author1 = splitLine[1];
						Book newBook1 = new Book(title1, author1);
						model.addBookToLibrary(newBook1);
					}
					readFile.close();
					System.out.println("All books from " + fileName + " have been added to your library!");
				} catch (Exception e) {
					System.out.println("Error reading the file: " + e.getMessage());
				}
				break;
				
			case HELP:
				displayHelpMessage();
				break;
				
			case EXIT:
				System.out.println("See you soon!");
				System.out.println("Exiting...");
				return 1;
			
			default:
				System.out.println("Sorry, I do not understand your command. Type help for the list of commands.");
				break;
		}
		return 0;
	}
	
	public String executeCommandGUI(String[] args) {
		ArrayList<Book> books = new ArrayList<Book>();

		switch (this.command) {
			case ADD_BOOK:
				Book newBook = new Book(args[0], args[1]);
				boolean bookIsAdded = model.addBookToLibrary(newBook);
				
				if (bookIsAdded){
					return "Book added successfully: " + newBook;
				}
				
				return "Error, cannot be added. Book is already in the library.";
			
			case SET_TO_READ:
				boolean bookIsSetToRead = model.setToRead(args[0], args[1]);  

				if (bookIsSetToRead) {
					return "Book marked as read.";
				}
				
				return "Book not found or already read.";
				
			case RATE:
				boolean bookIsRated = model.rateBook(args[0], Integer.parseInt(args[2]), args[1]);
				
				if (bookIsRated) {
					return "Book has been rated.";
				}
				
				return "Invalid rating given or book does not exist.";

			case SUGGEST_READ:
				Book suggestedBook = model.suggestBook();
				
				if (suggestedBook == null) {
					return "You have already read all books in your library!";
				} else {
					return "Suggested: " + suggestedBook;
				}
				
			case HELP:
				return helpMessage();
			
			default:
				return "Sorry, I do not understand your command. Type help for the list of commands.";
		}
	}
	
	public ArrayList<Book> searchGUI(String[] args) {
		if (args[0].equals("title")) {
			return model.searchBooksByTitle(args[1]);
		}
		
		else if (args[0].equals("author")) {
			return model.searchBooksByAuthor(args[1]);
		}
		
		return model.searchBooksByRating(Integer.parseInt(args[1]));
	}
	
	public ArrayList<Book> getBooksGUI(String[] args) {
		String option = args[0].toLowerCase();
		
		return model.getBooks(option);
	}
	
	/*
	 * Cleans the user's input so that it doesn't have any leading or trailing whitspace
	 * 
	 * @param input the input that is read in from the user
	 * 
	 * @return the cleaned user input
	 * 
	 */
	
	private String cleanInput(String input) {
		return input.trim();
	}

	/*
	 * Displays the help message when the user inputs help to let the user know the list
	 * of available commands
	 * 
	 */
	
	private void displayHelpMessage() {
		System.out.println("All commands are case sensitive!\n");
		System.out.println("search- specify whether to search by title, author, or rating and the results will be returned to you\n");
		System.out.println("addBook- specify the title, author, rating, and whether or not you have read the book and adds it to your library\n");
		System.out.println("setToRead- specify the title and author of the book you want to set to read\n");
		System.out.println("rate- specify the title and author of the book you want to rate from 1-5 with default ratings set to 0\n");
		System.out.println("getBooks- retrieves all books sorting by title or author, or only read/unread books\n");
		System.out.println("suggestRead- picks a random unread book from the library\n");
		System.out.println("addBooks- specify a file name, and all books from that file will be read into the library\n");
	}
	
	private String helpMessage() {
		return "All commands are case sensitive!\n" + 
	"Search- specify whether to search by title, author, or rating and the results will be returned to you\n" +
	"Sort- retrieves all books sorting by title or author, or only read/unread books\n" +
	"Suggest Book- picks a random unread book from the library\n" +
	"Add Book- specify the title and author and add it to your library\n" +
	"Rate Book- specify the title and author of the book you want to rate from 1-5\n" +
	"Set To Read- specify the title and author of the book you want to set to read\n" +
	"Import Books- select a file and all books from that file will be read into the library\n";
	}
}
