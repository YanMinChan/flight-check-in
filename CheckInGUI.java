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
    	    JLabel weightLabel = new JLabel("Baggage Weight:");
    	    JLabel dimensionLabel = new JLabel("Baggage Dimension:");
    	    weightField = new JTextField();    
    	    dimensionField = new JTextField();
    	
    	    // Add labels and text fields to the panel
    	    panel.add(lastNameLabel);
    	    panel.add(lastNameField);
    	    panel.add(bookingReferenceLabel);
    	    panel.add(bookingReferenceField);
    	    panel.add(weightLabel);
    	    panel.add(weightField);
    	    panel.add(dimensionLabel);
    	    panel.add(dimensionField);
    	    panel.add(new JLabel());
    	
    	    checkInButton = new JButton("Check In"); // Initialize checkInButton here
    	    checkInButton.addActionListener(this);
    	    panel.add(checkInButton);
    	
    	    submitBaggageButton = new JButton("Submit Baggage");
    	    submitBaggageButton.addActionListener(this);
    	    JPanel submitPanel = new JPanel();
    		  submitPanel.add(submitBaggageButton);
    	
              add(panel, BorderLayout.CENTER);
              add(submitPanel, BorderLayout.SOUTH);
    	
    	    setVisible(true);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    checkInSystem.getFlightReport("report.txt");
                    dispose();
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
                    JOptionPane.showMessageDialog(this, "Baggage successfully submitted with weight " + weightValue + " and dimension " + dimensionValue);

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
    private void displayDetails(String details) {
        if (details != null && !details.isEmpty()) {
            String[] lines = details.split("\n");
            JFrame detailsFrame = new JFrame("Booking Details");
            JTextArea detailsArea = new JTextArea(details);
            detailsArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(detailsArea);
            detailsFrame.add(scrollPane);
            detailsFrame.setSize(300, 200);
            detailsFrame.setVisible(true);
        } else {
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