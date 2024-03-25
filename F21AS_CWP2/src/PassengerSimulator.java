import java.util.*;

public class PassengerSimulator {
	// This class reads bookings.txt and simulate passenger
	
	// instance variable
	private SharedQueue queue;
	private CheckInSystem sys;
	private HashMap<String, Booking> bookingMap;
	
	public PassengerSimulator(SharedQueue queue) {
		this.queue = queue;
	};
	
	private void initialise() {
		// Read bookings.txt
		sys = new CheckInSystem();
		sys.readFile("bookings.txt", "Booking");
		bookingMap = sys.getBookingMap();
	}
	
	public void run() {
		//how to run? do we initialise every time
		this.initialise();
		// get random booking id
		List<String> key = new ArrayList<String> (bookingMap.keySet());
		Random r = new Random();
		Booking b = bookingMap.get(key.get(r.nextInt(key.size())));
		//somewhere here should check if the passenger already checked in
		queue.put(b);
	}

	public static void main(String[] args) {
		PassengerSimulator sim = new PassengerSimulator(new SharedQueue());
		//sim.initialise();
		sim.run();
	}
}
