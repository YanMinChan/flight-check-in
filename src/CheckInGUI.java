import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

public class CheckInGUI extends JFrame implements ActionListener 
{
    private static final long serialVersionUID = 1L;
    private JTextField lastNameField, bookingReferenceField, weightField, dimensionField;
    private JButton checkInButton, submitBaggageButton;
    private CheckInSystem checkInSystem;
    private String details;
    private JPanel mainPanel,waitingQueuePanel,desk1Panel,desk2Panel,desk3Panel,desk4Panel,flight1Panel,flight2Panel,flight3Panel,panel,waitingqscrollpanel;
    private JLabel weightLabel,dimensionLabel;
    private double fees,dispFees;
    private JFrame detailsFrame;
    private JTextArea detailsArea;
    
    public CheckInGUI() {
        this(new CheckInSystem());
    }

    public CheckInGUI(CheckInSystem checkInSystem) {
        this.checkInSystem = checkInSystem;
        initialise();
        setVisible(true);
    }

   
   
    private void initialise() {
        //this.checkInSystem = checkInSystem;
        setTitle("Check-In");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        // Create waiting queue panel
        waitingQueuePanel = new JPanel();
        waitingQueuePanel.setLayout(new BoxLayout(waitingQueuePanel, BoxLayout.Y_AXIS));
        waitingQueuePanel.setBorder(BorderFactory.createTitledBorder("Waiting Queue"));
      

        JScrollPane waitingqscrollpanel = new JScrollPane(waitingQueuePanel);
        waitingqscrollpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(waitingqscrollpanel);


        // Create desks panel
        JPanel desksPanel = new JPanel();
        desksPanel.setLayout(new GridLayout(2, 2));
        desksPanel.setBorder(BorderFactory.createTitledBorder("Check-In Desks"));

        // Create Desks 1 and 2
        desk1Panel = new JPanel();
        desk1Panel.setBorder(BorderFactory.createTitledBorder("Desk 1"));
        desksPanel.add(desk1Panel);

        desk2Panel = new JPanel();
        desk2Panel.setBorder(BorderFactory.createTitledBorder("Desk 2"));
        desksPanel.add(desk2Panel);

        // Create Desks 3 and 4
        desk3Panel = new JPanel();
        desk3Panel.setBorder(BorderFactory.createTitledBorder("Desk 3"));
        desksPanel.add(desk3Panel);

        desk4Panel = new JPanel();
        desk4Panel.setBorder(BorderFactory.createTitledBorder("Desk 4"));
        desksPanel.add(desk4Panel);

        mainPanel.add(desksPanel);
        // Create flights panel
        JPanel flightsPanel = new JPanel();
        flightsPanel.setLayout(new GridLayout(1, 3));
        flightsPanel.setBorder(BorderFactory.createTitledBorder("Flights"));

        // Create Flight 1
        flight1Panel = new JPanel();
        flight1Panel.setBorder(BorderFactory.createTitledBorder("Flight 1"));
        flightsPanel.add(flight1Panel);

        // Create Flight 2
        flight2Panel = new JPanel();
        flight2Panel.setBorder(BorderFactory.createTitledBorder("Flight 2"));
        flightsPanel.add(flight2Panel);

        // Create Flight 3
        flight3Panel = new JPanel();
        flight3Panel.setBorder(BorderFactory.createTitledBorder("Flight 3"));
        flightsPanel.add(flight3Panel);

        mainPanel.add(flightsPanel);

         // Create check-in panel
         panel = new JPanel();
         panel.setLayout(new GridLayout(5, 2));
         panel.setBorder(BorderFactory.createTitledBorder("Check-In Details"));
 
         JLabel lastNameLabel = new JLabel("Last Name:");
         lastNameField = new JTextField();
         JLabel bookingReferenceLabel = new JLabel("Booking Reference:");
         bookingReferenceField = new JTextField();
 
         checkInButton = new JButton("Check In");
         panel.add(checkInButton); 

         panel.add(new JLabel());

         setLayout(new BorderLayout());

         add(mainPanel, BorderLayout.CENTER);
              
            setVisible(true);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {

                    StringBuilder rep = new StringBuilder();
                    for(String flightCode : checkInSystem.flightMap.keySet()){
                        rep.append(checkInSystem.getFlightReport(flightCode)).append("\n");
                    }
                    checkInSystem.writeToFile("report.txt", rep.toString());
                    
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
            String bookRef = bookingReferenceField.getText();
            if (!weight.isEmpty() && !dimension.isEmpty()) {
                try {
                    int weightValue = Integer.parseInt(weight);
                    int dimensionValue = Integer.parseInt(dimension);
                    dispFees = baggageFees(weightValue,dimensionValue,bookRef);
                    JOptionPane.showMessageDialog(this, "Baggage successfully submitted with weight " + weightValue + " and dimension " + dimensionValue + "\n" + "Extra fees to be paid : "+ dispFees);
                    detailsArea.append("Extra fees : "+ dispFees + "\n");
                    
                    // Clear the weight and dimension fields
                    weightField.setText("");
                    dimensionField.setText("");
                    weightField.setEnabled(false);
                    dimensionField.setEnabled(false);
                    submitBaggageButton.setEnabled(false);
                } catch(IllegalBaggageWeightException ibr) {
                    JOptionPane.showMessageDialog(this, "Baggage weight is more than 200!");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "Invalid weight or dimension value.");
                } 
            }
                else 
                {
                    JOptionPane.showMessageDialog(this, "Please enter both weight and dimension.");
                }
            
        }
    }
    
    private double baggageFees(int weightValue,int dimensionValue,String BookRef) throws IllegalBaggageWeightException
    {
        Baggage bag = new Baggage(dimensionValue, weightValue);
        fees = bag.calculateBaggageFee(dimensionValue, weightValue);
        checkInSystem.addBaggageDetails(BookRef, dimensionValue, weightValue,fees);
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
    public void displayQueue(LinkedList<Booking> queue) {
        StringBuilder queueDetails = new StringBuilder();
        while (!queue.isEmpty()) {
            Booking ref = queue.poll();
            String Name = ref.getPassengerName();
            queueDetails.append(Name).append("\n");
        }
        if (queueDetails.length() > 0) {
            waitingQueuePanel.add(new JLabel(queueDetails.toString()));
            validate();
        }
    }

//    public static void main(String[] args){
//         //CheckInSystem checkInSystem = new CheckInSystem();
//         //checkInSystem.readFile("Bookings.txt", "Booking");
//         //checkInSystem.readFile("flights.txt", "Flight");
//         //new CheckInGUI(checkInSystem);
//
//        SharedQueue sq = new SharedQueue();
//        Thread simulator = new Thread(new PassengerSimulator(sq));
//        simulator.start();
//        Thread desk1 = new Thread(new CheckInDesk(sq, 1));
//        Thread desk2 = new Thread(new CheckInDesk(sq, 2));
//        Thread desk3 = new Thread(new CheckInDesk(sq, 3));
//        //Thread desk4 = new Thread(new CheckInDesk(sq, 4));
//        desk1.start();
//        desk2.start();
//        desk3.start();
//     }

    
}
