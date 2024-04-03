import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CheckInDesk implements Runnable, Subject{
	// this class gets passenger from queue and let them check in
	
	private SharedQueue queue;
	private int deskNum;
	private Booking b;
	private boolean timeOut;
	private long startTime;
    private final long timeLimitMinutes = 10;
	
	public CheckInDesk(SharedQueue queue, int deskNum) {
		this.queue = queue;
		this.deskNum = deskNum;
		timeOut = false;
		startTimer();
		startTime = System.currentTimeMillis();
	}
	
    private void startTimer() {
        Thread timerThread = new Thread(() -> {
            try {
                Thread.sleep(timeLimitMinutes * 1 * 1000); // Convert minutes to milliseconds
                synchronized (this) {
                    timeOut = true; // Set the time limit flag
//                    System.out.println("Check-in time limit reached. Closing check-in counters.");
                    while(timeOut) {
                    	Thread.sleep(100);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timerThread.start();
    }
	
	public void run() {
		// while there are still passenger in queue, continue check in
		while(!queue.getDone() & !timeOut) {
			b = queue.get(deskNum);
			b.setCheckIn(true);
			notifyObservers();
			try {
				Random r = new Random();
                // Randomized passenger simulating time between 1 to 10 minutes
                int checkInTime = r.nextInt(10) + 1;
				Thread.sleep(checkInTime * 1000);
			} catch (InterruptedException e) {
				String notice = "Desk closed!";
			}

		}
		if (timeOut) {
			String notice = "Desk " + deskNum + " closed!";
			System.out.println(notice);
		}
	}
	
	public Booking getCurrentPassenger(){
		return b;
	}
	
    // implementing subject observer method with GUI
    private List<Observer> registeredObservers = new LinkedList<Observer>();
    
    public void registerObserver(Observer obs) {
    	registeredObservers.add(obs);
    }
    
	public void removeObserver(Observer obs) {
		registeredObservers.remove(obs);
	}
	
	public void notifyObservers() {
		for (Observer obs : registeredObservers)
			obs.update();
	}
	
	public boolean getTimeOut() {
		return timeOut;
	}
}
