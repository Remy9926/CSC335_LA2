// File: Book.java
// Author(s): Mandy Jiang (mandyjiang), Ethan Huang (ehuang68)
// Purpose: Models the library on the backend so that the user doesn't directly interact with it

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * The LibraryModel Class represents the Library that a user will perform CRU operations
 * on Book Objects to. It implements encapsulation by making its library a private variable, so
 * no other classes can access it without interacting with the Library Model's methods. Also,
 * to prevent any kinds of escaping references, when a user wants to search for books, a
 * Deep Copy of each book is created so that the actual library cannot be modified.
 */

public class LibraryModel {

	private ArrayList<Book> library;
	
	public LibraryModel() {
		library = new ArrayList<Book>();
	}
	
	/*
	 * Adds a book into the library
	 *
	 * @param book the book to be added into the library
	 * 
	 */
	
	public boolean addBookToLibrary(Book book) {
		for (Book otherBook : library) {
			if (otherBook.getTitle().equalsIgnoreCase(book.getTitle()) &&
				otherBook.getAuthor().equalsIgnoreCase(book.getAuthor())) {
				return false;  
			}
		}
		
		library.add(book);
		return true;
	}

	
	/*
	 * Searches for and returns a deep copy of the library based on the user's specified crtieria
	 * 
	 * @param option the method of searching specified by the user to retrieve books from the
	 * library
	 * 
	 * @return a deep copy of the library with the books that meet the user's criteria
	 * 
	 */
	
	public ArrayList<Book> getBooks(String option) {

		ArrayList<Book> sortedBooks = new ArrayList<>(library);
	
		switch (option.toLowerCase()) {
			case "title":
				sortedBooks.sort(Comparator.comparing(book->book.getTitle().toLowerCase()));
				break;
			case "author":
				sortedBooks.sort(Comparator.comparing(book->book.getAuthor().toLowerCase()));
				break;
	
			case "read":
				sortedBooks.removeIf(book -> !book.haveRead());
				sortedBooks.sort(Comparator.comparing(book->book.getTitle().toLowerCase()));
				break;
	
			case "unread":
				sortedBooks.removeIf(Book::haveRead);
				sortedBooks.sort(Comparator.comparing(book->book.getTitle().toLowerCase()));
				break;
	
			default:
				return new ArrayList<>();
		}
		// rerturns copy 
		ArrayList<Book> sortedBooksCopy = new ArrayList<>();
		for (Book book:sortedBooks) {
			sortedBooksCopy.add(new Book(book));  
		}
	
		return sortedBooksCopy;
	}


	/*
	 * Looks for the book specified by the title and author given by the user and sets it to read
	 * 
	 * @param title the title of the book to set to read
	 * @param author the author of the book to set to read
	 * 
	 * @return true if the book is found and set to read, false if the book doesn't exist or is already read
	 * 
	 */
	
	public boolean setToRead(String title, String author) {
        for (Book book:library) {
            if (((book.getTitle().equalsIgnoreCase(title)) && (book.getAuthor().equalsIgnoreCase(author))&& !book.haveRead())) {  
            	book.setToRead();
                return true;  
            }
        }
        return false;  
    }



	/*
	 * Looks for the book specified by the title and author given by the user and sets its
	 * rating to the specified rating
	 * 
	 * @param title the title of the book to set to read
	 * @param rating the rating to give the book
	 * @param author the author of the book to set to read
	 * 
	 * @return true if the book is found and its rating is set to the new rating, false if
	 * the rating is invalid or the book isn't found
	 * 
	 */
	
	public boolean rateBook(String title, int rating, String author) {
        if (rating < 1 || rating > 6) {
            return false;
        }

        for (Book book:library) {
            if ((book.getTitle().equalsIgnoreCase(title)) && book.getAuthor().equalsIgnoreCase(author)) {  
                book.setRating(rating);
                return true; 
            }
        }

        return false;  
    }
	
	/*
	 * Uses the Collections.shuffle() method to shuffle the library and return a random book
	 * from the library that has not been read yet
	 * 
	 * @return a random book that has not been marked as read yet, null is returned if all books
	 * are already read
	 * 
	 */
	
	public Book suggestBook() {
		Collections.shuffle(library);
		
		for (Book book:library) {
			if (!book.haveRead()) {
				return new Book(book);
			}
		}
		return null;
	}
	
	/*
	 * Returns a deep copy of the library whose books match the title specified
	 * 
	 * @param title the title of the books the user wants to look for
	 * 
	 * @return a deep copy of the subset of the library that contains all books
	 * of the specified title
	 * 
	 */
	
	public ArrayList<Book> searchBooksByTitle(String title) {
		ArrayList<Book> books = new ArrayList<Book>();
		
		for (Book b: library) {
			if (b.getTitle().toLowerCase().equals(title)) {
				books.add(new Book(b));
			}
		}
		return books;
	}
	
	/*
	 * Returns a deep copy of the library whose books match the author specified
	 * 
	 * @param author the author whose books the user wants to look for
	 * 
	 * @return a deep copy of the subset of the library that contains all books
	 * written by the specified author
	 * 
	 */
	
	public ArrayList<Book> searchBooksByAuthor(String author) {
		ArrayList<Book> books = new ArrayList<Book>();
		
		for (Book b: library) {
			if (b.getAuthor().toLowerCase().equals(author)) {
				books.add(new Book(b));
			}
		}
		return books;
	}
	
	/*
	 * Returns a deep copy of the library whose books match the rating specified
	 * 
	 * @param rating the rating of the books the user wants to look for
	 * 
	 * @return a deep copy of the subset of the library that contains all books
	 * of the specified rating, returns null if the rating is invalid
	 * 
	 */
	
	public ArrayList<Book> searchBooksByRating(int rating) {
		if (rating < 1 || rating > 5) {
			return null;
		}
		ArrayList<Book> books = new ArrayList<Book>();
		
		for (Book b: library) {
			if (b.getRating() == rating) {
				books.add(new Book(b));
			}
		}
		return books;
	}
}






