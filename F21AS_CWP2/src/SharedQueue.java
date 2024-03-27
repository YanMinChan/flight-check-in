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
		done = false;
	}
	
	public synchronized void put(Booking b) {}; 
	
	public LinkedList<Booking> getQueue() {return queue;}
	
	public void setDone() {
		done = true;
	}

	public boolean getDone() {
		return done;
	}
}
