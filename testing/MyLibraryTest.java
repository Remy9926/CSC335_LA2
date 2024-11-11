package testing;

import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

import src.MyLibrary;

public class MyLibraryTest {

    private final ByteArrayOutputStream outContent= new ByteArrayOutputStream();
    private final PrintStream silentOut = new PrintStream(outContent);
    private final PrintStream originalOut =System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void start() {
        System.setOut(silentOut);
        System.setErr(silentOut);
    }
    
    @AfterEach
    public void closeStream() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testShowWelcomeMessage() throws Exception {
        Method showWelcomeMessage = MyLibrary.class.getDeclaredMethod("showWelcomeMessage");
        showWelcomeMessage.setAccessible(true);
        showWelcomeMessage.invoke(null);

        assertTrue(outContent.toString().contains("Welcome to your library! Type help for a list of comamnds and exit to quit.") );
    }

    @Test
    public void testPromptUser() throws Exception {
        Method promptUser = MyLibrary.class.getDeclaredMethod("promptUser");
        promptUser.setAccessible(true);
        promptUser.invoke(null);

        assertTrue(outContent.toString().contains("Please input a command to continue:") );
    }

    @Test
    public void testMainMethodWithExitCommand() {
        String simulatedUserInput = "exit\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        MyLibrary.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to your library! Type help for a list of comamnds and exit to quit.") );
        assertTrue(output.contains("Please input a command to continue:"));
        assertFalse(output.contains("Exception")
                 );
    }

  
}

