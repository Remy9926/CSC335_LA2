// File: Book.java
// Author(s): Mandy Jiang (user:mandyjiang), Ethan Huang
// Purpose: Models the library on the backend so that the user doesn't directly interact with it

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LibraryModel {

	private ArrayList<Book> library;
	
	public LibraryModel() {
		library = new ArrayList<Book>();
	}
	
	public void addBookToLibrary(Book book) {
		library.add(book);
	}


	public ArrayList<Book> getBooks(String option) {

		ArrayList<Book> sortedBooks = new ArrayList<>(library);
	
		switch (option.trim().toLowerCase()) {
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
				System.out.println("Invalid sort option.");
				return new ArrayList<>();
		}
		// rerturns copy 
		ArrayList<Book> sortedBooksCopy = new ArrayList<>();
		for (Book book:sortedBooks) {
			sortedBooksCopy.add(new Book(book));  
		}
	
		return sortedBooksCopy;
	}



	public boolean setToRead(String title, String author) {
        for (Book book:library) {
            if (((book.getTitle().equalsIgnoreCase(title)) && (book.getAuthor().equalsIgnoreCase(author))&& !book.haveRead())) {  
            	book.setToRead();
                return true;  
            }
        }
        return false;  
    }




	public boolean rateBook(String title, int rating, String author) {
        if (rating < 1 || rating > 6) {
            System.out.println("Rating must be between 1 and 5.");
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
	
	public Book suggestBook() {
		Collections.shuffle(library);
		
		for (Book book:library) {
			if (!book.haveRead()) {
				return new Book(book);
			}
		}
		return null;
	}
	
	public ArrayList<Book> searchBooksByTitle(String title) {
		ArrayList<Book> books = new ArrayList<Book>();
		
		for (Book b: library) {
			if (b.getTitle().toLowerCase().equals(title)) {
				books.add(new Book(b));
			}
		}
		return books;
	}
	
	public ArrayList<Book> searchBooksByAuthor(String title) {
		ArrayList<Book> books = new ArrayList<Book>();
		
		for (Book b: library) {
			if (b.getAuthor().toLowerCase().equals(title)) {
				books.add(new Book(b));
			}
		}
		return books;
	}
	
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






