import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

public class ParserTest {
	
	//had to prevent printing to console because it caused coverage values not to show up
 Parser parser;
    private final ByteArrayOutputStream   outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void start() {
        parser = new Parser();
        System.setOut(new PrintStream(outContent)); 
    }

    @AfterEach
    public void closeStreams() {
    	
        System.setOut(originalOut) ;
    }
    
    
    //reflection
    private Command getCommandUsingReflection(Parser parser) throws Exception {
        Field commandField = Parser.class.getDeclaredField("command");
        commandField.setAccessible(true);
        return (Command) commandField.get(parser);
    }

    @Test
    public void testSetValidCommand() throws Exception {
        parser.setCommand("search");
        assertEquals(Command.SEARCH, getCommandUsingReflection(parser));

        parser.setCommand("addBook");
        assertEquals(Command.ADD_BOOK, getCommandUsingReflection(parser)) ;

        parser.setCommand("exit");
        assertEquals(Command.EXIT, getCommandUsingReflection(parser));
    }
    
    
    
 
    @Test
    public void testGetBooksByTitle() throws Exception {
        parser.setCommand("getBooks");

        String input = "title\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        int result = parser.executeCommand(scanner);
        assertEquals(0, result);
        assertTrue(outContent.toString().contains("No books found or sort option invalid.") ); 
    }
    
    
    
    
    

    @Test
    public void testSuggestUnreadBook() throws Exception {
        parser.setCommand("suggestRead");

        ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
        Scanner scanner = new Scanner(in);

        int result = parser.executeCommand(scanner);
        assertEquals(0, result);
        assertTrue(outContent.toString().contains("You have already read all books in your library!") || 
                   outContent.toString().contains("suggested book details here"));
    }
    
    
    @Test
    public void testSearchCommandByTitle() throws NullCommandException {
        String input = "title\nGreen Eggs and Ham\n";
        parser.setCommand("search");

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        int result = parser.executeCommand(scanner);
        assertEquals(0, result);
    }

    @Test
    public void testAddBook() throws NullCommandException {
        String input = "The Cat in the Hat\nDr Seuss\n";
        parser.setCommand("addBook");

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        int result = parser.executeCommand(scanner);
        assertEquals(0, result);
    }

    @Test
    public void testSetToRead() throws NullCommandException {
        String input = "The Cat in the Hat\nDr Seuss\n";
        parser.setCommand("setToRead");

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        int result = parser.executeCommand(scanner);
        assertEquals(0, result);
    }
    
    @Test
    public void testExit() throws NullCommandException {
        parser.setCommand("exit");

        ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
        Scanner scanner = new Scanner(in);

        int result = parser.executeCommand(scanner);
        assertEquals(1, result);
    }
    
    @Test
    public void testAddBooksFromInvalidFile() throws Exception {
        parser.setCommand("addBooks");
        String input = "nonexistentfile.txt\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        int result = parser.executeCommand(scanner);
        assertEquals(0, result);
        assertTrue(outContent.toString().contains("The file you input does not exist"));
    }
    
    
    @Test
    public void testAddBooksFromFile() throws Exception {
        parser.setCommand("addBooks");

        String input = "books.txt\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        int result = parser.executeCommand(scanner);
        assertEquals(0, result);
        assertTrue(outContent.toString().contains("The file you input does not exist"));
    }
    
    
    @Test
    public void testRateBook() throws Exception {
        parser.setCommand("addBook");
        String addBookInput = "The Cat in the Hat\nDr Seuss\n" ;
        ByteArrayInputStream addBookStream = new ByteArrayInputStream(addBookInput.getBytes());
        parser.executeCommand(new Scanner(addBookStream));
        
        parser.setCommand("rate");
        String rateInput = "The Cat in the Hat\nDr Seuss\n4\n";
        ByteArrayInputStream rateStream = new ByteArrayInputStream(rateInput.getBytes());
        int result = parser.executeCommand(new Scanner(rateStream)         );

        assertEquals(0, result, "Expected 0 for continue execution.");
        assertTrue(outContent.toString().contains("Book successfully rated."));
    }
    
    
    
    @Test
    public void testSearchByAuthor() throws Exception {
        parser.setCommand("addBook");
        parser.executeCommand(new Scanner(new ByteArrayInputStream("The Cat in the Hat\nDr Seuss\n".getBytes())));
        parser.executeCommand(new Scanner(new ByteArrayInputStream("Green Eggs and Ham\nDr Seuss\n".getBytes())  ));
        
        parser.setCommand("search");
        int result = parser.executeCommand(new Scanner(new ByteArrayInputStream("author\nDr Seuss\n".getBytes())));
        
        assertEquals(0, result);
        assertTrue(outContent.toString().contains("The Cat in the Hat") && outContent.toString().contains("Green Eggs and Ham"));
        outContent.reset();
    }
    
    
    
    @Test
    public void testSearchByTitle() throws Exception {
        parser.setCommand("addBook");
        parser.executeCommand(new Scanner(new ByteArrayInputStream("The Cat in the Hat\nDr Seuss\n".getBytes())));
        
        parser.setCommand("search");
        int result = parser.executeCommand(new Scanner(new ByteArrayInputStream("title\nThe Cat in the Hat\n".getBytes())));
        
        assertEquals(0, result);
        assertTrue(outContent.toString().contains("The Cat in the Hat"));
    }
    
    
    
    
    
   
    @Test
    public void testSearchByRatingInvalid() throws Exception {
        parser.setCommand("search");
        String input = "rating\n6\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        int result = parser.executeCommand(scanner);
        assertEquals(0, result, "Expected 0 for continue execution.");
        assertTrue(outContent.toString().contains("Invalid rating given"));
    }
    
    
    
    
    @Test
    public void testDisplayHelpMessage() throws NullCommandException {
        parser.setCommand("help");
        parser.executeCommand(new Scanner(System.in));  
        String expectedOutput = """
            All commands are case sensitive!
            
            search- specify whether to search by title, author, or rating and the results will be returned to you
            
            addBook- specify the title, author, rating, and whether or not you have read the book and adds it to your library
            
            setToRead- specify the title and author of the book you want to set to read
            
            rate- specify the title and author of the book you want to rate from 1-5 with default ratings set to 0
            
            getBooks- retrieves all books sorting by title or author, or only read/unread books
            
            suggestRead- picks a random unread book from the library
            
            addBooks- specify a file name, and all books from that file will be read into the library
            """;

        String actualOutput = outContent.toString().replaceAll("\\s+", " ").trim();
        String expectedNormalizedOutput = expectedOutput.replaceAll("\\s+", " ").trim() ;

        assertEquals(expectedNormalizedOutput, actualOutput, "Help message should match expected output.");
    }
    
    

    
    
}
