import java.util.*;

public class SharedQueue {
	// This is the queue shared between passenger simulator and the check in desk.
	// A passenger simulator that reads bookings.txt and simulate passenger arrival is needed
	// Methods that put and get passengers are needed
	// The queue will be recorded in a LinkedList and it will be FIFO
	
	// instance variables
	private LinkedList<Booking> queue;
	private boolean done;
	
	public SharedQueue() {
		queue = new LinkedList<Booking>();
		done = false;
	}
	
	// get passenger in check in desk
	// will wait if queue is empty
	public synchronized Booking get() {
		while (queue.isEmpty()){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Booking b = queue.pollFirst();
		System.out.println("Got: " + b.getPassengerName());
		return b;
	}
	
	// put passenger into queue
	// will not wait
	public synchronized void put(Booking b) {
//		try {
//			System.out.println("I'm stuck here");
//			wait();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("Put: " + b.getPassengerName());
		queue.add(b);
	}; 
	
	public LinkedList<Booking> getQueue() {return queue;}
	
	public void setDone() {
		done = true;
	}

	public boolean getDone() {
		if (done && queue.isEmpty()) {
			return true;
		}
		return false;
	}
}
