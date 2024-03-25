import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;
public class CheckinSystemTest {
    CheckInSystem checkInSystem = new CheckInSystem();

    @Test
    public void testWriteToFile() throws IOException { //test that checks WriteToFile() method
        String report = "My name is Shounak Kelkar, this is a group project due on the 7th";
        checkInSystem.writeToFile("testreport.txt", report);
        File file = new File("testreport.txt");
        assertTrue(file.exists());
        String trial = new String(Files.readAllBytes(Paths.get("testreport.txt")));
        assertEquals("The report\n" + report, trial);
      
    }

}