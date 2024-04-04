import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
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
    private UserRuntime u;
    private long a;
    
    public CheckInGUI() {
        this(new CheckInSystem());
    }

    public CheckInGUI(CheckInSystem checkInSystem) {
        this.checkInSystem = checkInSystem;
        initialise();
        setVisible(true);
    }
   
   
    private void initialise() {
        u = new UserRuntime(checkInSystem);
        Thread runtime = new Thread(u);
        a = u.getSleepTime();
        runtime.start();
           
	    // Setting up threads
	    sq = new SharedQueue();
	    PassengerSimulator sim = new PassengerSimulator(sq, checkInSystem);
	    Thread simulator = new Thread(sim);
	    simulator.start();
	    desk1 = new CheckInDesk(sq, 1, 10);
	    desk2 = new CheckInDesk(sq, 2, 5);
	    desk3 = new CheckInDesk(sq, 3, 6);
	    desk4 = new CheckInDesk(sq, 4, 7);
	    
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

    }
    

    public void update() {
    	
    }
    
//    public static void main(String[] args)
//    {
//         CheckInSystem CIsys = new CheckInSystem();
//        InputStream bookingsInputStream = CheckInGUI.class.getResourceAsStream("/Users/dhanushkumar/Desktop/f21as_cw/bookings.txt");
//        InputStream flightsInputStream = CheckInGUI.class.getResourceAsStream("/Users/dhanushkumar/Desktop/f21as_cw/flights.txt");
//        InputStream baggageInputStream = CheckInGUI.class.getResourceAsStream("/Users/dhanushkumar/Desktop/f21as_cw/baggage.txt");
//    
//        // Call the readFile method for each file type with the corresponding InputStream
//        CIsys.readFile(bookingsInputStream, "Booking");
//        CIsys.readFile(flightsInputStream, "Flight");
//        CIsys.readFile(baggageInputStream, "Baggage");
//    
//        new CheckInGUI(CIsys);
//    }

    
}
