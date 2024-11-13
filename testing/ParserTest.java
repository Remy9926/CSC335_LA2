
// File: ParserTest.java
// Author(s): Mandy Jiang (mandyjiang), Ethan Huang (ehuang68)
// Purpose: parser.java junit tests

import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    Parser parser;
    private final ByteArrayOutputStream outContent =   new ByteArrayOutputStream();
    private final PrintStream originalOut =System.out;

    @BeforeEach
    public void start() {
        parser = new Parser();
        
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void closeStreams() {
        System.setOut(originalOut);
    }

    // Helper method to set up input for tests
    private void setupInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    public void testGetBooksByTitle() throws Exception {
        parser.setCommand("getBooks");
        setupInput("title\n");

        parser.executeCommand(new Scanner(System.in));
        assertTrue(outContent.toString().contains("No books found or sort option invalid."));
    }

    @Test
    public void testSuggestUnreadBook() throws Exception {
        parser.setCommand("suggestRead");
        setupInput("");

        int result = parser.executeCommand(new Scanner(System.in));
        assertEquals(0, result);
       
    }

    @Test
    public void testSearchCommandByTitle() throws NullCommandException {
        parser.setCommand("search");
        setupInput("title\nGreen Eggs and Ham\n");

        int result = parser.executeCommand(new Scanner(System.in));
        assertEquals(0, result);
    }

    @Test
    public void testAddBook() throws NullCommandException {
        parser.setCommand("addBook");
        setupInput("The Cat in the Hat\nDr Seuss\n");

        int result = parser.executeCommand(new Scanner(System.in));
        assertEquals(0, result);
    }

    @Test
    public void testSetToRead() throws NullCommandException {
        parser.setCommand("setToRead");
        setupInput("The Cat in the Hat\nDr Seuss\n");

        int result = parser.executeCommand(new Scanner(System.in)) ;
        assertEquals(0, result) ;
    }

    @Test
    public void testExit() throws NullCommandException {
        parser.setCommand("exit");
        setupInput("");

        int result = parser.executeCommand(new Scanner(System.in));
        assertEquals(1, result);
    }

    @Test
    public void testAddBooksFromInvalidFile() throws Exception {
        parser.setCommand("addBooks");
        setupInput("nonexistentfile.txt\n");

        int result = parser.executeCommand(new Scanner(System.in));
        assertEquals(0, result);
    }

    @Test
    public void testAddBooksFromFile() throws Exception {
        parser.setCommand("addBooks");
        setupInput("books.txt\n");

        int result = parser.executeCommand(new Scanner(System.in));
        assertEquals(0, result);
    }

    @Test
    public void testRateBook() throws Exception {
        parser.setCommand("addBook");
        setupInput("The Cat in the Hat\nDr Seuss\n");
        parser.executeCommand(new Scanner(System.in));

        parser.setCommand("rate");
        setupInput("The Cat in the Hat\nDr Seuss\n4\n");
        int result = parser.executeCommand(new Scanner(System.in));

        assertEquals(0, result);
    }

    @Test
    public void testSearchByAuthor() throws Exception {
        parser.setCommand("addBook");
        setupInput("The Cat in the Hat\nDr Seuss\n");
        parser.executeCommand(new Scanner(System.in));
        
        setupInput("Green Eggs and Ham\nDr Seuss\n");
        parser.executeCommand(new Scanner(System.in));

        parser.setCommand("search");
        setupInput("author\nDr Seuss\n");
        int result = parser.executeCommand(new Scanner(System.in));

        assertEquals(0, result);
       
        outContent.reset();
    }

    @Test
    public void testSearchByTitle() throws Exception {
        parser.setCommand("addBook");
        setupInput("The Cat in the Hat\nDr Seuss\n");
        parser.executeCommand(new Scanner(System.in));

        parser.setCommand("search");
        setupInput("title\nThe Cat in the Hat\n");
        int result = parser.executeCommand(new Scanner(System.in));

        assertEquals(0, result);
    }

    @Test
    public void testSearchByRating() throws Exception {
        parser.setCommand("search");
        setupInput("rating\n6\n");

        int result = parser.executeCommand(new Scanner(System.in));
        assertEquals(0, result, "Expected 0 for continue execution.");
    }

    @Test
    public void testRateBookNotFoundOrInvalidRating() throws Exception {
        parser.setCommand("rate") ;
        outContent.reset();
        setupInput("no Book Title\nUnknown Author\n5\n" );

        parser.executeCommand(new Scanner(System.in));
        String output = outContent.toString();

        assertTrue(output.contains("Book not found or invalid rating."));
    }

    @Test
    public void testAddDuplicateBookReturnsErrorMessage() throws Exception {
        parser.setCommand("addBook");
        setupInput("Duplicate Book\nAuthor Name\n");
        parser.executeCommand(new Scanner(System.in)) ;

        parser.setCommand("addBook");
        setupInput("Duplicate Book\nAuthor Name\n");
        parser.executeCommand(new Scanner(System.in)) ;

        assertTrue(outContent.toString().contains("Error, cannot be added. Book is already in the library."));
    }

    @Test
    public void testDisplayHelpMessage() throws NullCommandException {
        parser.setCommand("help");
        parser.executeCommand(new Scanner(System.in)) ;
        String expectedOutput= """
            All commands are case sensitive!
            
            search- specify whether to search by title, author, or rating and the results will be returned to you
            
            addBook- specify the title, author, rating, and whether or not you have read the book and adds it to your library
            
            setToRead- specify the title and author of the book you want to set to read
            
            rate- specify the title and author of the book you want to rate from 1-5 with default ratings set to 0
            
            getBooks- retrieves all books sorting by title or author, or only read/unread books
            
            suggestRead- picks a random unread book from the library
            
            addBooks- specify a file name, and all books from that file will be read into the library
            """;

        String actualOutput= outContent.toString().replaceAll("\\s+", " ").trim();
        String expectedNormalizedOutput = expectedOutput.replaceAll("\\s+", " ").trim();

        assertEquals(expectedNormalizedOutput, actualOutput, "Help message should match expected output.");
    }
}