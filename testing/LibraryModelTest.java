import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryModelTest {

    private LibraryModel library;
    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    public void setUp() {
        library = new LibraryModel();
        book1 = new Book("The Cat in the Hat", "Dr Seuss");
        book2 = new Book("Green Eggs and Ham", "Dr Seuss");
        book3 = new Book("Oh, the Places You'll Go!", "Dr Seuss");
    }

    @Test
    public void testAddBook() {
        assertTrue(library.addBookToLibrary(book1));
        assertFalse(library.addBookToLibrary( new Book("The Cat in the Hat", "Dr Seuss")));
    }

    @Test
    public void testGetBooksTitle() {
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        
        ArrayList<Book> sortedBooks = library.getBooks("title");
        assertEquals(2, sortedBooks.size()) ;
        assertTrue(sortedBooks.get(0).getTitle().equalsIgnoreCase("Green Eggs and Ham"));
        assertTrue(sortedBooks.get(1).getTitle().equalsIgnoreCase("The Cat in the Hat"));
    }

    @Test
    public void testGetBooksAuthor() {
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        
        ArrayList<Book> sortedBooks = library.getBooks("author");
        assertEquals(2, sortedBooks.size());
        assertTrue(sortedBooks.get(0).getAuthor().equalsIgnoreCase("Dr Seuss"));
    }

    @Test
    public void testGetReadBooks() {
        book1.setToRead();
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        
        ArrayList<Book> readBooks = library.getBooks("read");
        assertEquals(1, readBooks.size());
        assertTrue(readBooks.get(0).getTitle().equalsIgnoreCase("The Cat in the Hat"));
    }

    @Test
    public void testSetBookToRead() {
        library.addBookToLibrary(book1);
        assertTrue(library.setToRead("the cat in the hat", "dr seuss"));
        assertFalse(library.setToRead("Nonexistent Book", "Unknown"));
    }

    @Test
    public void testRateBook() {
        library.addBookToLibrary(book1);
        assertTrue(library.rateBook("The Cat in the Hat", 4, "Dr Seuss"));
        assertEquals(4, book1.getRating());
        assertFalse(library.rateBook("The Cat in the Hat", 6, "Dr Seuss"));
        assertFalse(library.rateBook("Nonexistent Book", 4, "Unknown"));
    }

    @Test
    public void testSuggestUnreadBook() {
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        book1.setToRead();
        
        Book suggestion = library.suggestBook();
        assertNotNull(suggestion);
        assertTrue(suggestion.getTitle().equalsIgnoreCase("Green Eggs and Ham"));
    }

    @Test
    public void testSearchBooksByTitle() {
        library.addBookToLibrary(book2);
       
        ArrayList<Book> bookList = library.searchBooksByTitle("green eggs and ham");
        assertFalse(bookList.isEmpty());
        
        assertEquals(1, bookList.size());
        
        assertTrue(bookList.get(0).getTitle().equalsIgnoreCase("Green Eggs and Ham"));
    }
    
    @Test
    public void testSearchBooksAuthor() {
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        
        ArrayList<Book> bookList = library.searchBooksByAuthor("dr seuss");
        assertFalse(bookList.isEmpty());
        assertEquals(2, bookList.size());
        assertTrue(bookList.get(0).getAuthor().equalsIgnoreCase("Dr Seuss"));
    }

    @Test
    public void testSearchBooksRating() {
        book1.setRating(5);
        library.addBookToLibrary(book1);
        
        ArrayList<Book> books = library.searchBooksByRating(5);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        
        assertEquals(5, books.get(0).getRating());
    }

    @Test
    public void testSearchBooksInvalidRating() {
        library.addBookToLibrary(book1);
        
        ArrayList<Book> books = library.searchBooksByRating(6);
        assertNull(books);
    }

    @Test
    public void testInvalidGetBooksOption() {
        library.addBookToLibrary(book1);
        
        ArrayList<Book> result = library.getBooks("unknown");
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testGetUnreadBooks() {
        book1.setToRead();
        
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        library.addBookToLibrary(book3);
        
        ArrayList<Book> unreadBooks = library.getBooks("unread");
        assertEquals(2, unreadBooks.size());
        assertTrue(unreadBooks.get(0).getTitle().equalsIgnoreCase("Green Eggs and Ham"));
        assertTrue(unreadBooks.get(1).getTitle().equalsIgnoreCase("Oh, the Places You'll Go!"));
        
        unreadBooks.forEach(book -> assertFalse(book.haveRead()));
    }
    
    @Test
	public void testParserHelpCommand() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("help");
			parser.executeCommand(scanner);
			
		} catch (NullCommandException e) {

		}
	}
	
	@Test
	public void testInvalidCommand() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("nothing");
			parser.executeCommand(scanner);
			
		} catch (NullCommandException e) {

		}
		
	}
	
	@Test
	public void testParserThrowException() {
		Parser parser = new Parser();
		assertThrows(NullCommandException.class, () -> parser.setCommand(null));
	}
	
	@Test
	public void emptyLibraryTestParserSearch() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("search");
			System.out.println("(Search by title)");
			parser.executeCommand(scanner);
			
			System.out.println("(Search by author)");
			parser.executeCommand(scanner);
			
			System.out.println("(Search by rating)");
			parser.executeCommand(scanner);
		
		} catch (NullCommandException e) {

		}
	}
	
	@Test
	public void emptyLibraryTestParserSearchInvalidRating() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("search");
			System.out.println("(Search by rating with an invalid number)");
			parser.executeCommand(scanner);

		} catch (NullCommandException e) {

		}
		
	}
	
	@Test
	public void testSuggestRead() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("suggestRead");
			parser.executeCommand(scanner);
			
			System.out.println("(addBook with any details you want, and it should be returned below)");
			parser.setCommand("addBook");
			parser.executeCommand(scanner);
			
			parser.setCommand("suggestRead");
			parser.executeCommand(scanner);
			
		} catch (NullCommandException e) {

		}
	}
	
	@Test
	public void testParserNonEmptyLibrary() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("setToRead");
			System.out.println("(Book does not exist so you can add anything)");
			parser.executeCommand(scanner);
			
			parser.setCommand("rate");
			System.out.println("(Book does not exist so you can add anything)");
			parser.executeCommand(scanner);
			
			parser.setCommand("addBook");
			System.out.println("(addBook)");
			parser.executeCommand(scanner);
			
			parser.setCommand("setToRead");
			System.out.println("(Use the details you added above)");
			parser.executeCommand(scanner);

			parser.setCommand("rate");
			System.out.println("(Use the details you added above)");
			parser.executeCommand(scanner);
			
			parser.setCommand("rate");
			System.out.println("(Do the same as above, but input an invalid rating)");
			parser.executeCommand(scanner);

		
		} catch (NullCommandException e) {

		}
	}
	
	@Test
	public void testParserExit() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("exit");
			assertEquals(1, parser.executeCommand(scanner));
		
		} catch (NullCommandException e) {

		}		
	}
	
	@Test
	public void testNonEmptyLibrarySearch() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("addBook");
			System.out.println("(addBook then search for it given any of the criteria)");
			parser.executeCommand(scanner);
			
			parser.setCommand("search");
			System.out.println("(Search for the book you just added by title)");
			parser.executeCommand(scanner);
			
			System.out.println("(Search for the book you just added by author)");
			parser.executeCommand(scanner);
			
			parser.setCommand("rate");
			System.out.println("(Give the book a rating from 1-5)");
			parser.executeCommand(scanner);
			
			parser.setCommand("search");
			System.out.println("(Search for the book you just added by the rating you gave it)");
			parser.executeCommand(scanner);

			
		} catch (NullCommandException e) {

		}
	}
	
	@Test
	public void getBooksTest() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("getBooks");
			System.out.println("(Library is empty so nothing should be returned)");
			parser.executeCommand(scanner);
			
			System.out.println("(Choose an invalid search option)");
			parser.executeCommand(scanner);
			
			parser.setCommand("addBook");
			System.out.println("(addBook and search for it with its details below)");
			parser.executeCommand(scanner);
			
			parser.setCommand("getBooks");
			System.out.println("(Choose title)");
			parser.executeCommand(scanner);
			
			System.out.println("(Choose author)");
			parser.executeCommand(scanner);
			
			System.out.println("(Choose unread)");
			parser.executeCommand(scanner);
			
			parser.setCommand("setToRead");
			System.out.println("(Set the book you added to read)");
			parser.executeCommand(scanner);
			
			parser.setCommand("getBooks");
			System.out.println("(Choose read)");
			parser.executeCommand(scanner);
			
		} catch (NullCommandException e) {

		}
	}
	
	@Test
	public void addBooksTest() {
		Parser parser = new Parser();
		Scanner scanner = new Scanner(System.in);
		
		try {
			parser.setCommand("addBooks");
			
			System.out.println("Input an invalid file name");
			parser.executeCommand(scanner);
			
			System.out.println("Input a valid file name to read from");
			parser.executeCommand(scanner);
			
		} catch (NullCommandException e) {

		}
	}
}
