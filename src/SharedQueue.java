import java.util.*;

public class SharedQueue implements Subject {
    // This is the queue shared between passenger simulator and the check in desk.
    // A passenger simulator that reads bookings.txt and simulate passenger arrival is needed
    // Methods that put and get passengers are needed
    // The queue will be recorded in a LinkedList and it will be FIFO
    
    // instance variables
    private LinkedList<Booking> queue;
    private boolean done;
    private final long timeLimitMinutes = 10; // 30 minutes time limit
    private long startTime;
    
    public SharedQueue() {
        queue = new LinkedList<Booking>();
        done = false;
        startTime = System.currentTimeMillis();
        startTimer();
    }
    
    private void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!done) {
                    System.out.println("Time limit exceeded. Stopping simulation.");
                    Thread.currentThread().interrupt(); // Interrupt the passenger simulator thread
                }
            }
        }, timeLimitMinutes * 1000); // Convert minutes to milliseconds
    }
    
    // get passenger in check in desk
    // will wait if queue is empty
    public synchronized Booking get(int deskNum) {
        while (queue.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } 

        Booking b = queue.pollFirst();
        notifyObservers();
        System.out.println("        Desk " + deskNum + " got: " + b.getPassengerName());
        return b;
    }
    
    // put passenger into queue
    // will not wait
    public synchronized void put(Booking b) {
        System.out.println("Put: " + b.getPassengerName());
        queue.add(b);
        notifyObservers();
        notifyAll();
    }; 
    
    public LinkedList<Booking> getQueue() {return queue;}
    
    public void setDone() {done = true;}

    public boolean getDone() {
        if (done && queue.isEmpty()) {
            return true;
        }
        return false;
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
