import javax.swing.*;

import java.util.LinkedList;
import java.util.List;

public class DeskDisplay extends JPanel implements Observer, Subject {

    private CheckInDesk desk;
    private int deskNumber;
    CheckInSystem cs;

    public DeskDisplay(CheckInDesk desk, int deskNumber) {
        this.desk = desk;
        this.deskNumber = deskNumber;
        desk.registerObserver(this);
        this.setBorder(BorderFactory.createTitledBorder("Desk " +  deskNumber));
    }

    @Override
    public void update() {
		// remove existing JLabels
		removeAll();
		
		// Print desk action
		Booking b = desk.getCurrentPassenger();
		String details = b.getCheckInDetails(b);
        Log logger = Log.getInstance("log.txt");
        logger.write(details);
		JTextArea textArea = new JTextArea(2, 20);
		textArea.setText(details);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFocusable(false);
	    textArea.setBackground(UIManager.getColor("Label.background"));
	    textArea.setFont(UIManager.getFont("Label.font"));
	    textArea.setBorder(UIManager.getBorder("Label.border"));
		add(textArea);
		
        revalidate();
        repaint();
        notifyObservers();
        
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
