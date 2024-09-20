// File: Book.java
// Author(s): Mandy Jiang (user:mandyjiang), Ethan Huang
// Purpose: Models the library on the backend so that the user doesn't directly interact with it

import java.util.ArrayList;

public class LibraryModel {

	private ArrayList<Book> library;
	
	public LibraryModel() {
		library = new ArrayList<Book>();
	}
	
	void addBookToLibrary(Book book) {
		library.add(book);
	}
}
