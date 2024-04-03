import java.util.*;

import javax.swing.*;

public class PassengerSimulator implements Runnable{
	// This class reads bookings.txt and simulate passenger
	
	// instance variable
	private static SharedQueue queue;
	private CheckInSystem sys;
	private HashMap<String, Booking> bookingMap;
	private HashMap<String, Booking> newMap;
	private List<String> newMapKey;
	private int passengerCounter = 0;
	private static PassengerSimulator simulatorInstance;
	
	// constructor
	public PassengerSimulator(SharedQueue queue) {
		this.queue = queue;
	};
	
	// set up all hashmap and list needed
	private void initialise() {
		// Read bookings.txt
		sys = new CheckInSystem();
		sys.readFile("bookings.txt", "Booking");
		bookingMap = sys.getBookingMap();
		this.notCheckedInMap(bookingMap);
	}
	
	// create a new hashmap that contains passenger that had not check in and its key list
	private void notCheckedInMap(HashMap<String, Booking> oriMap){
		newMap = new HashMap<String, Booking>();
		for (Map.Entry<String, Booking> b: oriMap.entrySet()) {
			if (!b.getValue().getCheckIn()) {
				newMap.put(b.getKey(), b.getValue());
			}
		}
		newMapKey = new ArrayList<String> (newMap.keySet());
	}
	
	public void run() {
		this.initialise();	
		// continue when there are still passenger that haven't check in
		while(!newMap.isEmpty()) {
			try {
				// there will be one passenger simulated per second
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			generatePassenger();
		}
		queue.setDone();
	}
	
	public void generatePassenger() {
		// get a random booking
		Random r = new Random();
		int index = r.nextInt(newMapKey.size());
		String key = newMapKey.get(index);
		Booking b = newMap.get(key);
		
		// generate random baggage
		double weight = Math.round(r.nextDouble()*80*100)/100; //max weight 80
		double dim = Math.round(r.nextDouble()*100*100)/100; //max dim 100
		Baggage ba = new Baggage(dim, weight);
		b.setBaggage(ba);
		
		// put booking into queue
		queue.put(b);
		newMap.remove(key);
		newMapKey.remove(index);
	}
	public Booking getCurrentPassenger() {
		return queue.getQueue().getLast();
	}


		

	
	public static void main(String[] args) {
		SharedQueue sq = new SharedQueue();
		Thread simulator = new Thread(new PassengerSimulator(sq));
		simulator.start();
		Thread desk1 = new Thread(new CheckInDesk(sq, 1));
		Thread desk2 = new Thread(new CheckInDesk(sq, 2));
		Thread desk3 = new Thread(new CheckInDesk(sq, 3));
		//Thread desk4 = new Thread(new CheckInDesk(sq, 4));
		desk1.start();
		desk2.start();
		desk3.start();
//		desk4.start();
	}
}
