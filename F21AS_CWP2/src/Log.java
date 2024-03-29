//import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class Log {
	
	private static Log instance;
//	private List<String> logBuffer;
	private FileWriter writer;
	
	/**
	 * writes supplied text to file
	 * @param filename the name of the file to be written to
	 * @param report the text to be written to the file
	 */
	public void writeToFile(String filename, String report) {
	
		 FileWriter fw;
		 try {
		    fw = new FileWriter(filename);
		    fw.write("The report\n");
		    fw.write(report);
		 	fw.close();
		 }
		 //message and stop if file not found
		 catch (FileNotFoundException fnf){
			 System.out.println(filename + " not found ");
			 System.exit(0);
		 }
		 //stack trace here because we don't expect to come here
		 catch (IOException ioe){
		    ioe.printStackTrace();
		    System.exit(1);
		 }
	}
}
