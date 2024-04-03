import java.util.*;
import java.util.concurrent.*;

public class SharedQueueTrial implements Subject{
    private LinkedList<Booking> queue;
    private boolean done;
    private final long timeLimitMinutes = 10; // 30 minutes time limit
    private long startTime;

    public SharedQueueTrial() {
        queue = new LinkedList<Booking>();
        done = false;
        startTime = System.currentTimeMillis();
        startTimer();
    }

    private void startTimer() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            if (!done) {
                System.out.println("Time limit exceeded. Stopping simulation.");
                Thread.currentThread().interrupt(); // Interrupt the passenger simulator thread
            }
        }, timeLimitMinutes, TimeUnit.MINUTES); // Schedule after the specified time limit
    }

    public synchronized Booking get(int deskNum) {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted. Stopping check-in.");
                return null; // Return null when interrupted
            }
        }
        Booking b = queue.pollFirst();
        notifyObservers();
        System.out.println("		Desk " + deskNum + " got: " + b.getPassengerName());
        return b;
    }

    public synchronized void put(Booking b) {
        System.out.println("Put: " + b.getPassengerName());
        queue.add(b);
        notifyObservers();
        notifyAll();
    }

    public LinkedList<Booking> getQueue() {return queue;}
    
    public void setDone() {
        done = true;
    }

    public boolean getDone() {
        return done && queue.isEmpty();
    }
    
	public int getPassengerInQueueCount() {
		return queue.size();
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


