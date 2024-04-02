import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class WaitingQueueDisplay extends JPanel implements Observer{
	
	// instance variable
	private PassengerSimulator sim;
	private JScrollPane waitingqscrollpanel;
	
	// constructor
	public WaitingQueueDisplay(PassengerSimulator sim) {
		this.sim = sim;
		sim.registerObserver(this);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Waiting Queue"));
		waitingqscrollpanel = new JScrollPane();
		waitingqscrollpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	public void update() {
        Booking b = sim.getCurrentPassenger();
        if (b != null) {
        	String details = b.getBookingRef() + " " + b.getPassengerName();

            // New JLabel for new passenger
            JLabel newPassengerLabel = new JLabel(details);
            this.add(newPassengerLabel, 0);
            
            revalidate();
            repaint();
            
//            waitingqscrollpanel = (JScrollPane) getParent();
//            waitingqscrollpanel.getVerticalScrollBar().setValue(waitingqscrollpanel.getVerticalScrollBar().getMaximum());
        }
        
	}
}
