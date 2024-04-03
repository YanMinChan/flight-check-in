import java.util.LinkedList;
import java.util.List;

public class CheckInDesk implements Runnable, Subject{
	// this class gets passenger from queue and let them check in
	
	private SharedQueue queue;
	private int deskNum;
	private Booking b;
	
	public CheckInDesk(SharedQueue queue, int deskNum) {
		this.queue = queue;
		this.deskNum = deskNum;
	}
	
	public void run() {
		// while there are still passenger in queue, continue check in
		while(!queue.getDone()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			b = queue.get(deskNum);
			b.setCheckIn(true);
			notifyObservers();
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
}
