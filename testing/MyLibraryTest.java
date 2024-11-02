import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

public class MyLibraryTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent)); 
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out); 
    }

 

    @Test
    public void testPromptUser() throws Exception {
        Method promptUser = MyLibrary.class.getDeclaredMethod("promptUser");
        promptUser.setAccessible(true);
        promptUser.invoke(null);

        String expectedOutput = "Please input a command to continue: ";
        assertTrue(outContent.toString().contains(expectedOutput), "Expected prompt message to be displayed.");
    }
    
    
}
