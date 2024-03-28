
public class CheckInDesk implements Runnable{
	// this class gets passenger from queue and let them check in
	
	private SharedQueue queue;
	
	public CheckInDesk(SharedQueue queue) {
		this.queue = queue;
	}
	
	public void run() {
		// while there are still passenger in queue, continue check in
		while(!queue.getDone()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Booking b = queue.get();
			b.setCheckIn(true);
			// connect to the rest of the code?
		}
	}
}
