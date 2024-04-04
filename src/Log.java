//import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Log {
	
	private final String logFile;
	private static Log instance;
	private String log;
	
	/**
	 * writes supplied text to file
	 * @param filename the name of the file to be written to
	 * @param report the text to be written to the file
	 */
	public void writeLogToFile() {
	
		 
		 try (FileWriter fw = new FileWriter("log.txt")){
		    fw.write("The log:\n");
		    fw.write(log);
		 	fw.close();
		 }
		 //message and stop if file not found
		 catch (FileNotFoundException fnf) {
			 System.out.println(logFile + " not found ");
			 System.exit(0);
		 }
		 //stack trace here because we don't expect to come here
		 catch (IOException ioe) {
		    ioe.printStackTrace();
		    System.exit(1);
		 }
	}

    private Log(String logFile) {
        this.logFile = logFile;
        this.log = "";
    }

	public static Log getInstance(String logFile) {
        synchronized(Log.class) {
            if(instance==null) {
                instance = new Log(logFile);
            }
        }
        return instance;
    }

	public void write(String data) {
        log += data + "\n";
    }

}
