import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BookTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Book sampleBook; 

    @BeforeEach
    public void setUpStreams(){
        System.setOut(new PrintStream(outContent));
        sampleBook = new Book("Sample Title", "Sample Author");
    }

    @AfterEach
    public void restoreStreams(){
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    public void testConstructor(){
        Book book = new Book("Sample Book", "Author");
        
        assertEquals("Sample Book", book.getTitle()) ;
        assertEquals("Author" , book.getAuthor());
        assertEquals(0, book.getRating());
        assertFalse(book.haveRead()) ;
    }

    @Test
    public void testCopyConstruct() {
        sampleBook.setRating(4);
        sampleBook.setToRead();

        Book copy = new Book(sampleBook);
        
        assertEquals(sampleBook.getTitle(), copy.getTitle());
        assertEquals(sampleBook.getAuthor(), copy.getAuthor());
        assertEquals(sampleBook.getRating(), copy.getRating());
        assertEquals(sampleBook.haveRead(), copy.haveRead());
    }

    @Test
    public void testGetters(){
        assertEquals("Sample Title", sampleBook.getTitle());
        assertEquals("Sample Author", sampleBook.getAuthor());
        assertEquals(0, sampleBook.getRating());
        assertFalse(sampleBook.haveRead());
    }

    @Test
    public void testSetToRead(){
        sampleBook.setToRead();
        assertTrue(sampleBook.haveRead()) ;
    }

    @Test
    public void testSetRatings(){
    	
        sampleBook.setRating(4);
        assertEquals(4, sampleBook.getRating());
        
        
        sampleBook.setRating(10); 
        assertEquals(4, sampleBook.getRating());
    }


    @Test
    public void testToString(){
        String expected = "Sample Title by Sample Author\n - Rating: 0 |Unread|" ;
        assertEquals(expected,sampleBook.toString());

        sampleBook.setToRead();
        sampleBook.setRating(5 );
        expected = "Sample Title by Sample Author\n - Rating: 5 |Read|";
        assertEquals(expected, sampleBook.toString());
    }

}

