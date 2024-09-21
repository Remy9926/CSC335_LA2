// File: Book.java
// Author(s): Mandy Jiang (user:mandyjiang), Ethan Huang
// Purpose: Models the library on the backend so that the user doesn't directly interact with it

import java.util.ArrayList;
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
	
		switch (option.toLowerCase()) {
			case "title":
				sortedBooks.sort(Comparator.comparing(book -> book.getTitle().toLowerCase()));
				break;
	
			case "author":
				sortedBooks.sort(Comparator.comparing(book -> book.getAuthor().toLowerCase()));
				break;
	
			case "read":
				sortedBooks.removeIf(book -> !book.haveRead());
				sortedBooks.sort(Comparator.comparing(book -> book.getTitle().toLowerCase()));
				break;
	
			case "unread":
				sortedBooks.removeIf(Book::haveRead);
				sortedBooks.sort(Comparator.comparing(book -> book.getTitle().toLowerCase()));
				break;
	
			default:
				System.out.println("Invalid sort option.");
				return new ArrayList<>();
		}
		// rerturns copy 
		ArrayList<Book> bookCopy = new ArrayList<>();
		for (Book book : sortedBooks) {
			bookCopy.add(new Book(book));  
		}
	
		return bookCopy;
	}



	public boolean setToRead(String title) {
        for (Book book : library) {
            if (book.getTitle().equalsIgnoreCase(title)) {  
                if (!book.haveRead()) { 
                    book.setToRead();
                    return true;  
                }
            }
        }
        return false;  
    }




	public boolean rateBook(String title, int rating) {
        if (rating < 0 || rating > 6) {
            System.out.println("Rating must be between 1 and 5.");
            return false;
        }

        for (Book book : library) {
            if (book.getTitle().equalsIgnoreCase(title)) {  
                book.setRating(rating); 
                return true; 
            }
        }

        return false;  
    }
}






