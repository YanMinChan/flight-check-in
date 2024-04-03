import java.util.*;

public class PassengerSimulatorBaggageDraft implements Runnable {
    private SharedQueue queue;
    private CheckInSystem sys;
    private HashMap<String, Booking> bookingMap;
    private HashMap<String, Booking> newMap;
    private List<String> newMapKey;
    private long startTime;
    private final long timeLimitMinutes = 1; // 30 minutes time limit
    
    public PassengerSimulatorBaggageDraft(SharedQueue queue) {
        this.queue = queue;
    }
    
    private void initialise() {
        sys = new CheckInSystem();
        sys.readFile("bookings.txt", "Booking");
        sys.readFile("flights.txt", "Flight");
        sys.readFile("baggage.txt", "Baggage");
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
        
        while (!newMap.isEmpty() && !isTimeLimitExceeded()) {
            try {
            	// Randomized check-in time between 1 to 10 minutes
            	int checkInTime = r.nextInt(10) + 1;
            	Thread.sleep(checkInTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            int index = r.nextInt(newMapKey.size());
            String key = newMapKey.get(index);
            Booking b = newMap.get(key);
            
            queue.put(b);
            newMap.remove(key);
            newMapKey.remove(index);
        }
        queue.setDone();
    }
    
}
