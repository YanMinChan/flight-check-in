import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class CheckInGUI implements ActionListener
{
	//GUI for the flight booking system
	
	JFrame frame;
	JButton CheckInButton;
	JButton filterButton;
	JPanel panel;
	private JTextArea HeadingArea;
	
	
	public CheckInGUI() {
		// Initialising JFrame and JPanel
        frame = new JFrame();
        panel = new JPanel();
        
        HeadingArea = new JTextArea("Check - In"); //Heading text
        HeadingArea.setEditable(false);
        
        // Check in action
        CheckInButton = new JButton("Check In ");  //button for checking in
        CheckInButton.addActionListener(this);
        
        //placement of heading
        panel.setLayout(new BorderLayout());
        panel.add(HeadingArea, BorderLayout.NORTH); 
        panel.add(new JScrollPane(HeadingArea), BorderLayout.CENTER); // Add a JScrollPane for the HeadingArea
        
        //placement for check in Button
        panel.add(CheckInButton, BorderLayout.SOUTH); 
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.pack();
        frame.setVisible(true);
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// tbd
		
	}
	
	public static void main(String[] args)
	{
		CheckInGUI gui = new CheckInGUI();
	}

}
