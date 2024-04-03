import java.util.*;

public class TimedPassengerSimulatorTrial implements Runnable {
    private SharedQueue queue;
    private CheckInSystem sys;
    private HashMap<String, Booking> bookingMap;
    private HashMap<String, Booking> newMap;
    private List<String> newMapKey;
    private long startTime;
    private final long timeLimitMinutes = 1; // 30 minutes time limit
    
    public TimedPassengerSimulatorTrial(SharedQueue queue) {
        this.queue = queue;
    }
    
    private void initialise() {
        sys = new CheckInSystem();
        sys.readFile("bookings.txt", "Booking");
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
    
   /*  public static void main(String[] args) {
        SharedQueue sq = new SharedQueue();
        Thread simulator = new Thread(new TimedPassengerSimulatorTrial(sq));
        simulator.start();
        Thread desk1 = new Thread(new CheckInDesk(sq, 1));
        Thread desk2 = new Thread(new CheckInDesk(sq, 2));
        Thread desk3 = new Thread(new CheckInDesk(sq, 3));
        Thread desk4 = new Thread(new CheckInDesk(sq, 4));
        desk1.start();
        desk2.start();
        desk3.start();
        desk4.start(); // Start desk4
    }
    */
}
