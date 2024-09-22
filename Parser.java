// File: Parser.java
// Author(s): Mandy Jiang (user:mandyjiang), Ethan Huang
// Purpose: Parses the user input to validate and execute commands as necessary

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class Parser {
	
	private LibraryModel model;
	private Command command;
	
	public Parser() {
		this.model = new LibraryModel();
	}
	
	public void setCommand(String command) throws NullCommandException {
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
	
	public int executeCommand(Scanner scanner) {
		
		ArrayList<Book> books = new ArrayList<Book>();;

		switch (this.command) {
			case SEARCH:
				System.out.print("Please enter your search type (title/author/rating): ");
				String searchType = scanner.nextLine().trim().toLowerCase();
				
				if (searchType.equals("title")) {
					System.out.println("Please enter the title of the book: ");
					String title = scanner.nextLine().trim();
					books = model.searchBooksByTitle(title.toLowerCase());
				}
				
				if (searchType.equals("author")) {
					System.out.println("Please enter the author of the book: ");
					String author = scanner.nextLine().trim();
					books = model.searchBooksByAuthor(author.toLowerCase());
				}
				
				if (searchType.equals("rating")) {
					System.out.println("Please enter the rating of the book (1-5): ");
					int rating = Integer.parseInt(scanner.nextLine().trim());
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
				String title = scanner.nextLine();
				
				System.out.print("Please enter the book's author: ");
				String author = scanner.nextLine();

				Book newBook = new Book(title, author);
				model.addBookToLibrary(newBook);
				
				System.out.println("Book added successfully: " + newBook);
				break;
			
			case SET_TO_READ:
				//ask user for author of the book too?
				System.out.print("Please enter the title of the book to mark as read: ");
				String bookTitle = scanner.nextLine();  
				System.out.println("Please enter the book author: ");
				String bookAuthor= scanner.nextLine();

				boolean success = model.setToRead(bookTitle,bookAuthor);  
				if (success) {
					System.out.println("Book marked as read.");
				} else {
					System.out.println("Book not found or already read.");
				}
				break;
				
				
			case RATE:
				//ask user for author of the book too?
				System.out.print("Please enter the title of the book you want to rate: ");
				String booksTitle = scanner.nextLine();  
<<<<<<< Updated upstream:Parser.java
				System.out.print("Please enter the book author: ");
				String booksAuthor  = scanner.nextLine();

				
=======

>>>>>>> Stashed changes:src/Parser.java
				System.out.print("Please enter a rating (1-5): ");
				int rating;
				rating = Integer.parseInt(scanner.nextLine()); 
				boolean successful = model.rateBook(booksTitle, rating,booksAuthor);  
			
				if (successful) {
					System.out.println("Book successfully rated.");
				} else {
					System.out.println("Book not found or invalid rating.");
				}
				break;
				
			case GET_BOOKS:
				System.out.print("How would you like to sort your books by? (title/author/read/unread): ");
				String option = scanner.nextLine().toLowerCase(); 
				
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

				String fileName =scanner.nextLine(); 
				File file = new File(fileName);

				if (!file.exists() ) {
					System.out.println("The file you input does not exist");
					break;
				}

				try {
					Scanner readFile = new Scanner(file);
					while (readFile.hasNextLine()) {
						String line = readFile.nextLine();
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
				System.out.println("Sorry, I do not understand your command. Press help for the list of commands.");
				break;
		}
		return 0;
	}
	
	private String cleanInput(String input) {
		return input.trim();
	}

	private void displayHelpMessage() {
		System.out.println("All commands are case sensitive!\n");
		System.out.println("search- specify whether to search by title, author, or rating and the results will be returned to you\n");
		System.out.println("addBook- specify the title, author, rating, and whether or not you have read the book and adds it to your library\n");
		System.out.println("setToRead- specify the title and author of the book you want to set to read\n");
		System.out.println("rate- specify the title and author of the book you want to rate from 1-5 with default ratings set to 0\n");
		System.out.println("getBooks- retrieves all books sorting by title or author, or only read/unread books\n");
		System.out.println("suggestRead- picks a random unread book from the library\n");
		System.out.println("addBooks- specify a file name, and all books from that file will be read into the libray\n");
	}
}
