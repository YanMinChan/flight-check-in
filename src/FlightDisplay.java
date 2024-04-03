import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class FlightDisplay extends JPanel implements Observer{
    
	// instance variable
	//private FlightSharedQueue s;
	private DeskDisplay[] deskDiss;
	private Flight f;
	private CheckInSystem sys;
    
	// constructor
    public FlightDisplay(DeskDisplay[] deskDiss, Flight f, CheckInSystem sys) {
    	this.deskDiss = deskDiss;
    	this.f = f; 
    	this.sys = sys;
    	for (DeskDisplay deskDis: deskDiss) {
    		deskDis.registerObserver(this);
    	}
    	this.setBorder(BorderFactory.createTitledBorder("Flight " + f.getFlightCode() + " " + f.getDest()));
    }
    
    
    public void update() {

    	
	
		// get flight details
		String details = sys.FlightDetails(f);
		JTextArea textArea = new JTextArea(2, 30);
		textArea.setText(details);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFocusable(false);
	    textArea.setBackground(UIManager.getColor("Label.background"));
	    textArea.setFont(UIManager.getFont("Label.font"));
	    textArea.setBorder(UIManager.getBorder("Label.border"));
		
	    removeAll();
	    add(textArea);
		
		
//		for (Flight f: s.getQueue()) {
//			details = String.format("%-10s %-30s", f.getFlightCode(), f.getDest());
//			label = new JLabel(details);
//			add(label);
//		}
		
        revalidate();
        repaint();
        
//		if (queueDetails.length() > 0) {
//			this.add(new JLabel(queueDetails.toString()));
//
//      }  
	}
}
