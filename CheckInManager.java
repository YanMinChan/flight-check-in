public class CheckInManager {

	public CheckInManager(){}

	public void run() throws IllegalBookingReference{
		//Initialize check in system and read files
		CheckInSystem sys = new CheckInSystem();
		sys.readFile("bookings.txt", "Booking");
		sys.readFile("flights.txt", "Flight");

		//Check if readfile works for flight.txt
		String carrier = sys.getFlightMap().get("LAAA002").getCarrier();
		System.out.println(carrier);

		//Run GUI
		new CheckInGUI(sys);
	}
	public String getReport(String filename) throws IllegalBookingReference {
		CheckInSystem sys = new CheckInSystem();
		sys.readFile("bookings.txt", "Booking");
		sys.readFile("flights.txt", "Flight");
        StringBuilder report = new StringBuilder();

        for (String flightCode : sys.flightMap.keySet()) {
            report.append(sys.getFlightReport(flightCode)).append("\n");
        }

        sys.writeToFile(filename, report.toString());
        return report.toString();
    }

	// public static void main(String[] args){
	// 	new CheckInManager().run();
	// }
}
