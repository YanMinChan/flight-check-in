public class CheckInManager {

	public CheckInManager(){}

	public void run(){
		//Initialize check in system and read files
		CheckInSystem sys = new CheckInSystem();
		sys.readFile("bookings.txt", "Booking");
		sys.readFile("flights.txt", "Flight");

		//Run GUI
		new CheckInGUI(sys);
	}
	
	//  public static void main(String[] args){
	//  	new CheckInManager().run();
	//  }
}
