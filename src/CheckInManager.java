public class CheckInManager {

	public CheckInManager(){}

	public void run(){
		//Initialize check in system and read files
		CheckInSystem CIsys = new CheckInSystem();
    	CIsys.readFile("bookings.txt", "Booking");
    	CIsys.readFile("flights.txt", "Flight");
    	CIsys.readFile("baggage.txt", "Baggage");
    	//System.out.println(CIsys.getFlightMap().get("NYDA001").getDest());
    	
    	
		//Run GUI
		new CheckInGUI(CIsys);
	}
	
	//  public static void main(String[] args){
	//  	new CheckInManager().run();
	//  }
}
