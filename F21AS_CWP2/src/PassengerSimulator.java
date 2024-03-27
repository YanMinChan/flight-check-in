import java.util.*;

public class PassengerSimulator implements Runnable{
	// This class reads bookings.txt and simulate passenger
	
	// instance variable
	private SharedQueue queue;
	private CheckInSystem sys;
	private HashMap<String, Booking> bookingMap;
	private HashMap<String, Booking> newMap;
	private List<String> newMapKey;
	
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
		Random r = new Random();
		
		// continue when there are still passenger that haven't check in
		while(!newMap.isEmpty()) {
			try {
				// there will be one passenger simulated per second
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// get a random passenger and put it into queue
			int index = r.nextInt(newMapKey.size());
			String key = newMapKey.get(index);
			Booking b = newMap.get(key);
			System.out.println(b.getPassengerName());
			queue.put(b);
			
			// idk should we modify the original bookingMap too so I'll leave a comment here
			newMap.remove(key);
			newMapKey.remove(index);
		}
		queue.setDone();
		System.out.println("done");
	}

//	// check if all passenger had checked in
//	public boolean finishedCheckIn(HashMap<String, Booking> map) {
//		for (Map.Entry<String, Booking> b: map.entrySet()) {
//			if (!b.getValue().getCheckIn()) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	public static void main(String[] args) {
//		PassengerSimulator sim = new PassengerSimulator(new SharedQueue());
//		//sim.initialise();
//		sim.run();
		SharedQueue sq = new SharedQueue();
		Thread simulator = new Thread(new PassengerSimulator(sq));
		simulator.start();
	}
}
