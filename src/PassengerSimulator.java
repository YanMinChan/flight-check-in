import java.util.*;

public class PassengerSimulator implements Runnable {
	// This class reads bookings.txt and simulate passenger
	
	// instance variable
    private SharedQueue queue;
    private CheckInSystem sys;
    private HashMap<String, Booking> bookingMap;
    private HashMap<String, Booking> newMap;
    private List<String> newMapKey;
    
    
    // constructor
    public PassengerSimulator(SharedQueue queue, CheckInSystem sys) {
        this.queue = queue;
        this.sys = sys;
    }
    
    // set up all hashmap and list needed
    private void initialise() {
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
        newMapKey = new ArrayList<String>(newMap.keySet());
    }
    
    public void run() {
        this.initialise();
        Random r = new Random();
        
     // continue when there are still passenger that haven't check in
        while (!newMap.isEmpty()) {
            try {
                // Randomized passenger simulating time between 1 to 10 minutes
                int simTime = r.nextInt(10) + 1;
                Thread.sleep(simTime * 100);
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
		double dim = Math.round(r.nextDouble()*50*100)/100; //max dim 50
		Baggage ba = new Baggage(dim, weight);
		ba.setFee(ba.calculateBaggageFee(dim, weight));
		b.setBaggage(ba);
		
		// put booking into queue
		queue.put(b);
		newMap.remove(key);
		newMapKey.remove(index);
	}
	
	public Booking getCurrentPassenger() {
		return queue.getQueue().getLast();
	}

}
