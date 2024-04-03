import java.util.*;

public class PassengerSimulator implements Runnable {
    private SharedQueue queue;
    private CheckInSystem sys;
    private HashMap<String, Booking> bookingMap;
    private HashMap<String, Booking> newMap;
    private List<String> newMapKey;
    private long startTime;
    private final long timeLimitMinutes = 30; // 30 minutes time limit
    
    public PassengerSimulator(SharedQueue queue, CheckInSystem sys) {
        this.queue = queue;
        this.sys = sys;
    }
    
    private void initialise() {
        bookingMap = sys.getBookingMap();
        this.notCheckedInMap(bookingMap);
    }
    
    private void notCheckedInMap(HashMap<String, Booking> oriMap){
        newMap = new HashMap<String, Booking>();
        for (Map.Entry<String, Booking> b: oriMap.entrySet()) {
            if (!b.getValue().getCheckIn()) {
                newMap.put(b.getKey(), b.getValue());
            }
        }
        newMapKey = new ArrayList<String>(newMap.keySet());
    }
    
    private boolean isTimeLimitExceeded() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedTimeMinutes = elapsedTime / (1000 * 60); // Convert milliseconds to minutes
        return elapsedTimeMinutes >= timeLimitMinutes;
    }
    
    public void run() {
        this.initialise();
        Random r = new Random();
        startTime = System.currentTimeMillis(); // Start time
        
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
        if (isTimeLimitExceeded()) {
            System.out.println("Time limit reached. Stopping simulation.");
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

    
//    public static void main(String[] args) {
//        SharedQueue sq = new SharedQueue();
//        Thread simulator = new Thread(new PassengerSimulator(sq));
//        simulator.start();
//        Thread desk1 = new Thread(new CheckInDesk(sq, 1));
//        Thread desk2 = new Thread(new CheckInDesk(sq, 2));
//        Thread desk3 = new Thread(new CheckInDesk(sq, 3));
//        Thread desk4 = new Thread(new CheckInDesk(sq, 4));
//        desk1.start();
//        desk2.start();
//        desk3.start();
//        desk4.start(); // Start desk4
//    }
}
