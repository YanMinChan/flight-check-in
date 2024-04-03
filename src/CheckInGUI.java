import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;



public class CheckInGUI extends JFrame implements ActionListener, Observer 
{
    private static final long serialVersionUID = 1L;
    private CheckInSystem checkInSystem;
    private JPanel mainPanel;
    private WaitingQueueDisplay queueDis;
    private CheckInDesk desk1, desk2, desk3, desk4;
    private SharedQueue sq;
    
    public CheckInGUI() {
        this(new CheckInSystem());
    }

    public CheckInGUI(CheckInSystem checkInSystem) {
        this.checkInSystem = checkInSystem;
        initialise();
        setVisible(true);
    }
   
   
    private void initialise() {
	    // Setting up threads
	    sq = new SharedQueue();
	    PassengerSimulator sim = new PassengerSimulator(sq, checkInSystem);
	    Thread simulator = new Thread(sim);
	    simulator.start();
	    desk1 = new CheckInDesk(sq, 1);
	    desk2 = new CheckInDesk(sq, 2);
	    desk3 = new CheckInDesk(sq, 3);
	    desk4 = new CheckInDesk(sq, 4);
	    
	    Thread tdesk1 = new Thread(desk1);
	    Thread tdesk2 = new Thread(desk2);
	    Thread tdesk3 = new Thread(desk3);
	    Thread tdesk4 = new Thread(desk4);
	    tdesk1.start();
	    tdesk2.start();
	    tdesk3.start();
	    tdesk4.start();

    	GUI();

    }

    private void GUI() {
    	setTitle("Check-In");
        setSize(1000, 600);
        
        // write all flights details to log when close
        addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		// write log of flight details
        		Log logger = Log.getInstance("log.txt");
        		for(Map.Entry<String, Flight> f: checkInSystem.getFlightMap().entrySet()) {
        			String fcode = f.getValue().getFlightCode();
        			String log = "\n" + checkInSystem.getFlightReport(fcode);
        			logger.write(log);
        		}
        		logger.writeLogToFile();
        	}
        });
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        // Create waiting queue panel
        queueDis = new WaitingQueueDisplay(sq);
        JScrollPane waitingqscrollpanel = new JScrollPane(queueDis);
        waitingqscrollpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(waitingqscrollpanel);
      
        // Create desks panel
        JPanel desksPanel = new JPanel();
        desksPanel.setLayout(new GridLayout(2, 2));
        desksPanel.setBorder(BorderFactory.createTitledBorder("Check-In Desks"));

        // Create Desks 1, 2, 3, 4
        DeskDisplay dd1 = new DeskDisplay(desk1, 1);
        DeskDisplay dd2 = new DeskDisplay(desk2, 2);
        DeskDisplay dd3 = new DeskDisplay(desk3, 3);
        DeskDisplay dd4 = new DeskDisplay(desk4, 4);
        desksPanel.add(dd1);
        desksPanel.add(dd2);
        desksPanel.add(dd3);
        desksPanel.add(dd4);
        mainPanel.add(desksPanel);
        
        // Create flights panel
        JPanel flightsPanel = new JPanel();
        flightsPanel.setLayout(new GridLayout(1, 3));
        flightsPanel.setBorder(BorderFactory.createTitledBorder("Flights"));

        // Create Flight 1, 2, 3
        DeskDisplay[] displays = new DeskDisplay[4];
        displays[0] = dd1;
        displays[1] = dd2;
        displays[2] = dd3;
        displays[3] = dd4;
        flightsPanel.add(new FlightDisplay(displays, checkInSystem.getFlightMap().get("NYDA001"), checkInSystem));
        flightsPanel.add(new FlightDisplay(displays, checkInSystem.getFlightMap().get("LAAA002"), checkInSystem));
        flightsPanel.add(new FlightDisplay(displays, checkInSystem.getFlightMap().get("LBA003"), checkInSystem));

        mainPanel.add(flightsPanel);

         add(mainPanel, BorderLayout.CENTER);
    }
    
    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == checkInButton) {
//            String BookRef = bookingReferenceField.getText();
//            String LastName = lastNameField.getText();
//            if (!BookRef.isEmpty()) {
//                try {
//                    details = checkInSystem.DetailsByRefID(BookRef,LastName);
//                    displayDetails(details);
//
//                    // Enable the weight and dimension fields
//                    
//                    weightField.setEnabled(true);
//                    dimensionField.setEnabled(true);
//                    
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(this, "Error retrieving booking details: " + ex.getMessage());
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Please enter a reference ID.");
//            }
//           
//        } 
//        else if (e.getSource() == submitBaggageButton) {
//            String weight = weightField.getText();
//            String dimension = dimensionField.getText();
//            String bookRef = bookingReferenceField.getText();
//            if (!weight.isEmpty() && !dimension.isEmpty()) {
//                try {
//                    int weightValue = Integer.parseInt(weight);
//                    int dimensionValue = Integer.parseInt(dimension);
//                    dispFees = baggageFees(weightValue,dimensionValue,bookRef);
//                    JOptionPane.showMessageDialog(this, "Baggage successfully submitted with weight " + weightValue + " and dimension " + dimensionValue + "\n" + "Extra fees to be paid : "+ dispFees);
//                    detailsArea.append("Extra fees : "+ dispFees + "\n");
//                    
//                    // Clear the weight and dimension fields
//                    weightField.setText("");
//                    dimensionField.setText("");
//                    weightField.setEnabled(false);
//                    dimensionField.setEnabled(false);
//                    submitBaggageButton.setEnabled(false);
//                } catch(IllegalBaggageWeightException ibr) {
//                    JOptionPane.showMessageDialog(this, "Baggage weight is more than 200!");
//                } catch (NumberFormatException nfe) {
//                    JOptionPane.showMessageDialog(this, "Invalid weight or dimension value.");
//                } 
//            }
//                else 
//                {
//                    JOptionPane.showMessageDialog(this, "Please enter both weight and dimension.");
//                }
//            
//        }
    }
    
//    private double baggageFees(int weightValue,int dimensionValue,String BookRef) throws IllegalBaggageWeightException
//    {
//        Baggage bag = new Baggage(dimensionValue, weightValue);
//        fees = bag.calculateBaggageFee(dimensionValue, weightValue);
//        checkInSystem.addBaggageDetails(BookRef, dimensionValue, weightValue,fees);
//        bag.setFee(fees);
//        
//        return fees;
//        
//    }
    
    
//    private void displayDetails(String details) 
//    { 
//        if (details != null && !details.isEmpty()) 
//        { 
//            String[] lines = details.split("\n"); 
//            detailsFrame = new JFrame("Booking Details"); 
//            detailsArea = new JTextArea(); 
//            detailsArea.setEditable(false); 
//            for (String line : lines) 
//            { 
//                detailsArea.append(line); 
//                detailsArea.append("\n"); 
//            } 
//            
//            JScrollPane scrollPane = new JScrollPane(detailsArea); 
//            detailsFrame.add(scrollPane, BorderLayout.CENTER); 
//            
//            JPanel inputPanel = new JPanel(); 
//            inputPanel.setLayout(new GridLayout(3, 2)); 
//            
//            weightLabel = new JLabel("Baggage Weight:", SwingConstants.RIGHT); 
//            dimensionLabel = new JLabel("Baggage Dimension:", SwingConstants.RIGHT); 
//            weightField = new JTextField(10); 
//            dimensionField = new JTextField(10); 
//            
//            inputPanel.add(weightLabel); 
//            inputPanel.add(weightField); 
//            inputPanel.add(dimensionLabel); 
//            inputPanel.add(dimensionField); 
//            
//            submitBaggageButton = new JButton("Submit Baggage"); 
//            submitBaggageButton.addActionListener(this); 
//            inputPanel.add(submitBaggageButton); 
//            detailsFrame.add(inputPanel, BorderLayout.SOUTH); 
//            
//            detailsFrame.setSize(300, 200); 
//            detailsFrame.setVisible(true);
//            
//            // Enable the weight and dimension fields 
//            weightField.setEnabled(true); 
//            dimensionField.setEnabled(true); 
//            } 
//        else 
//        { 
//            JOptionPane.showMessageDialog(this, "No booking details found."); 
//        } 
//    }
    
//    public void displayQueue(LinkedList<Booking> queue) {
//        StringBuilder queueDetails = new StringBuilder();
//        while (!queue.isEmpty()) {
//            Booking ref = queue.poll();
//            String Name = ref.getPassengerName();
//            queueDetails.append(Name).append("\n");
//        }
//        if (queueDetails.length() > 0) {
//            waitingQueuePanel.add(new JLabel(queueDetails.toString()));
//            validate();
//        }
//    }

    public void update() {
    	
    }
    
    public static void main(String[] args){
         //CheckInSystem checkInSystem = new CheckInSystem();
         //checkInSystem.readFile("Bookings.txt", "Booking");
         //checkInSystem.readFile("flights.txt", "Flight");
         //new CheckInGUI(checkInSystem);

//        SharedQueue sq = new SharedQueue();
////        Thread simulator = new Thread(new PassengerSimulator(sq));
////        simulator.start();
//        Thread desk1 = new Thread(new CheckInDesk(sq, 1));
//        Thread desk2 = new Thread(new CheckInDesk(sq, 2));
//        Thread desk3 = new Thread(new CheckInDesk(sq, 3));
//        //Thread desk4 = new Thread(new CheckInDesk(sq, 4));
//        desk1.start();
//        desk2.start();
//        desk3.start();
    	
    	CheckInSystem CIsys = new CheckInSystem();
    	CIsys.readFile("bookings.txt", "Booking");
    	CIsys.readFile("flights.txt", "Flight");
    	CIsys.readFile("baggage.txt", "Baggage");
    	//System.out.println(CIsys.getFlightMap().get("NYDA001").getDest());
    	
    	new CheckInGUI(CIsys);
     }

    
}
