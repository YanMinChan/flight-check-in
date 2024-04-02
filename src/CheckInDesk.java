public class CheckInDesk implements Runnable{
	// this class gets passenger from queue and let them check in
	
	private SharedQueue queue;
	private int deskNum;
	
	public CheckInDesk(SharedQueue queue, int deskNum) {
		this.queue = queue;
		this.deskNum = deskNum;
	}
	
	public void run() {
		// while there are still passenger in queue, continue check in
		while(!queue.getDone()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Booking b = queue.get(deskNum);
			b.setCheckIn(true);
			// connect to the rest of the code?
		}
	}
}
