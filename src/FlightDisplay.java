import javax.swing.JLabel;
import javax.swing.JPanel;

public class FlightDisplay extends JPanel{
    private FlightSharedQueue s;
    public void update() {

		removeAll();
		
	
		String details = " ";
		JLabel label = new JLabel(details);
		add(label);
		
		
		for (Flight f: s.getQueue()) {
			details = String.format("%-10s %-30s", f.getFlightCode(), f.getDest());
			label = new JLabel(details);
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
