import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class WaitingQueueDisplay extends JPanel implements Observer{
	
	// instance variable
	private SharedQueue sq;
	private JScrollPane waitingqscrollpanel;
	
	// constructor
	public WaitingQueueDisplay(SharedQueue sq) {
		this.sq = sq;
		sq.registerObserver(this);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Waiting Queue"));
		waitingqscrollpanel = new JScrollPane();
		waitingqscrollpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	// Updates queue when there are passenger joining or leaving queue
	public void update() {
		// remove existing JLabels
		removeAll();
		
		for (Booking b: sq.getQueue()) {
			String details = String.format("%-10s %-30s %-10s %-10s", b.getBookingRef(), b.getPassengerName(), Double.toString(b.getBaggage().getWeight()), Double.toString(b.getBaggage().getDim()));
			JLabel label = new JLabel(details);
			add(label);
		}
		
        revalidate();
        repaint();
		
//		if (queueDetails.length() > 0) {
//			this.add(new JLabel(queueDetails.toString()));
//
//      }  
	}
}
