import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckInGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField lastNameField, bookingReferenceField, weightField, dimensionField;
    private JButton checkInButton, submitBaggageButton;
    private CheckInSystem checkInSystem;
    private String details;
    private JPanel panel;
    private JLabel weightLabel,dimensionLabel;
    private double fees,dispFees;
    private JFrame detailsFrame;
    private JTextArea detailsArea;
	
    

    public CheckInGUI(CheckInSystem checkInSystem) {
    	    this.checkInSystem = checkInSystem;
    	    setTitle("Check-In");
    	    setSize(300, 200);
    	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	    panel = new JPanel();
    	    panel.setLayout(new GridLayout(5, 2)); // Changed the grid layout to accommodate the new components
    	
    	    JLabel lastNameLabel = new JLabel("Last Name:");
    	    lastNameField = new JTextField();
    	    JLabel bookingReferenceLabel = new JLabel("Booking Reference:");
    	    bookingReferenceField = new JTextField();
    	    
    	
    	    // Add labels and text fields to the panel
    	    panel.add(lastNameLabel);
    	    panel.add(lastNameField);
    	    panel.add(bookingReferenceLabel);
    	    panel.add(bookingReferenceField);
    	    panel.add(new JLabel());
    	
    	    checkInButton = new JButton("Check In"); // Initialize checkInButton here
    	    checkInButton.addActionListener(this);
    	    panel.add(checkInButton);
    	
            add(panel, BorderLayout.CENTER);
              
    	
    	    setVisible(true);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {

                    CheckInManager checkInManager = new CheckInManager();
                    try {
                        checkInManager.getReport("report.txt");
                    } catch (IllegalBookingReference e1) {
                        
                        e1.printStackTrace();
                    }
                }
            });
    	}

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkInButton) {
            String BookRef = bookingReferenceField.getText();
            String LastName = lastNameField.getText();
            if (!BookRef.isEmpty()) {
                try {
                    details = checkInSystem.DetailsByRefID(BookRef,LastName);
                    displayDetails(details);

                    // Enable the weight and dimension fields
                    
                    weightField.setEnabled(true);
                    dimensionField.setEnabled(true);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error retrieving booking details: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a reference ID.");
            }
           
        } 
        else if (e.getSource() == submitBaggageButton) {
            String weight = weightField.getText();
            String dimension = dimensionField.getText();
            if (!weight.isEmpty() && !dimension.isEmpty()) {
                try {
                    int weightValue = Integer.parseInt(weight);
                    int dimensionValue = Integer.parseInt(dimension);
                    dispFees = baggageFees(weightValue,dimensionValue);
                    JOptionPane.showMessageDialog(this, "Baggage successfully submitted with weight " + weightValue + " and dimension " + dimensionValue + "\n" + "Extra fees to be paid : "+ dispFees);
                    detailsArea.append("Extra fees : "+ dispFees + "\n");
                    
                    // Clear the weight and dimension fields
                    weightField.setText("");
                    dimensionField.setText("");
                    weightField.setEnabled(false);
                    dimensionField.setEnabled(false);
                    submitBaggageButton.setEnabled(false);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid weight or dimension value.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter both weight and dimension.");
            }
        }
    }
    
    private double baggageFees(int weightValue,int dimensionValue)
    {
    	Baggage bag = new Baggage(0,0,0);
        bag.setDim(dimensionValue);
        try {
			bag.setWeight(weightValue);
		} catch (IllegalBaggageWeight e) {
			JOptionPane.showMessageDialog(this, "Invalid weight or dimension value.");
			e.printStackTrace();
		}
        fees = bag.calculateBaggageFee(dimensionValue, weightValue);
        bag.setFee(fees);
        
        return fees;
        
    }
    
    
    private void displayDetails(String details) 
    { 
    	if (details != null && !details.isEmpty()) 
    	{ 
    		String[] lines = details.split("\n"); 
    		detailsFrame = new JFrame("Booking Details"); 
    		detailsArea = new JTextArea(); 
    		detailsArea.setEditable(false); 
    		for (String line : lines) 
    		{ 
    			detailsArea.append(line); 
    			detailsArea.append("\n"); 
    		} 
    		
    		JScrollPane scrollPane = new JScrollPane(detailsArea); 
    		detailsFrame.add(scrollPane, BorderLayout.CENTER); 
    		
    		JPanel inputPanel = new JPanel(); 
    		inputPanel.setLayout(new GridLayout(3, 2)); 
    		
    		weightLabel = new JLabel("Baggage Weight:", SwingConstants.RIGHT); 
    		dimensionLabel = new JLabel("Baggage Dimension:", SwingConstants.RIGHT); 
    		weightField = new JTextField(10); 
    		dimensionField = new JTextField(10); 
    		
    		inputPanel.add(weightLabel); 
    		inputPanel.add(weightField); 
    		inputPanel.add(dimensionLabel); 
    		inputPanel.add(dimensionField); 
    		
    		submitBaggageButton = new JButton("Submit Baggage"); 
    		submitBaggageButton.addActionListener(this); 
    		inputPanel.add(submitBaggageButton); 
    		detailsFrame.add(inputPanel, BorderLayout.SOUTH); 
    		
    		detailsFrame.setSize(300, 200); 
    		detailsFrame.setVisible(true);
    		
    		// Enable the weight and dimension fields 
    		weightField.setEnabled(true); 
    		dimensionField.setEnabled(true); 
    		} 
    	else 
    	{ 
    		JOptionPane.showMessageDialog(this, "No booking details found."); 
    	} 
    } 
    public static void main(String[] args) throws IllegalBookingReference {
        CheckInSystem checkInSystem = new CheckInSystem();
        checkInSystem.readFile("bookings.txt", "Booking");
        checkInSystem.readFile("flights.txt", "Flight");
        new CheckInGUI(checkInSystem);
    }
}





