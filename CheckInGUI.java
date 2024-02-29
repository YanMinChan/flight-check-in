import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckInGUI implements ActionListener
{
	//GUI for the flight booking system
	private JFrame frame;
    private JTextField nameField, bookingRefField;
	private JButton checkInButton;
	private JPanel centerPanel;
	private JLabel headingArea;
	
	
	public CheckInGUI() {
		
        // .. Component setup
        frame = new JFrame();
        centerPanel = new JPanel();
        nameField = new JTextField(15);
        bookingRefField = new JTextField(15);
        checkInButton = new JButton("Check In ");  //button for checking in
        headingArea = new JLabel("Check - In"); //Heading text

        // ..  Layout setup
        // Set Up JFrame
        frame.setTitle("Check-In Kiosk");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Heading
        headingArea.setFont(new Font("Arial", Font.BOLD, 24));
        headingArea.setHorizontalAlignment(JLabel.CENTER);

        // Center
        centerPanel.setLayout(new GridLayout(2, 2, 10, 10));
        centerPanel.add(new JLabel("Last name:"));
        centerPanel.add(nameField);
        centerPanel.add(new JLabel("Booking reference:"));
        centerPanel.add(bookingRefField);

        // .. Panel setup
        frame.add(headingArea, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(checkInButton, BorderLayout.SOUTH);

        // -- Action listener
        checkInButton.addActionListener(this);
        
        // //placement of heading
        // centerPanel.setLayout(new BorderLayout());
        // centerPanel.add(HeadingArea, BorderLayout.NORTH); 
        // centerPanel.add(new JScrollPane(HeadingArea), BorderLayout.CENTER); // Add a JScrollPane for the HeadingArea
        
        // //placement for check in Button
        // centerPanel.add(checkInButton, BorderLayout.SOUTH); 
        // centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
 
        frame.pack();
        frame.setVisible(true);
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// tbd
		
	}
	
	public static void main(String[] args)
	{
		new CheckInGUI();
	}

}
