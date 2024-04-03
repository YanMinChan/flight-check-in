import java.io.InputStream;

public class CheckInManager {

	public CheckInManager(){}

	public void run(){
        //Initialize check in system and read files
        CheckInSystem CIsys = new CheckInSystem();
        InputStream bookingsInputStream = CheckInGUI.class.getResourceAsStream("/bookings.txt");
        InputStream flightsInputStream = CheckInGUI.class.getResourceAsStream("/flights.txt");
        InputStream baggageInputStream = CheckInGUI.class.getResourceAsStream("/baggage.txt");
    
        // Call the readFile method for each file type with the corresponding InputStream
        CIsys.readFile(bookingsInputStream, "Booking");
        CIsys.readFile(flightsInputStream, "Flight");
        CIsys.readFile(baggageInputStream, "Baggage");
        //System.out.println(CIsys.getFlightMap().get("NYDA001").getDest());
        
        
        //Run GUI
        new CheckInGUI(CIsys);
    }

	
	//  public static void main(String[] args){
	//  	new CheckInManager().run();
	//  }
}
