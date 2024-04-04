import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class UserRuntime implements ActionListener, Runnable {
    private JLabel runtime;
    private JFrame newFrame;
    private JTextField input;
    private JButton button;
    private long time;
    private CheckInSystem sys;

    public UserRuntime(CheckInSystem sys) {
    	this.sys = sys;
    }
    public JFrame getNewFrame() {
        return newFrame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        time = Long.parseLong(input.getText()) * 1000;
        newFrame.dispose();
        JOptionPane.showMessageDialog(null, "Thread sleep time set to: " + time + " milliseconds.");
    }
    public void run(){
        newFrame = new JFrame("User Run Time");
        
        // write all flights details to log when close
        
        newFrame.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		// write log of flight details
        		Log logger = Log.getInstance("log.txt");
        		for(Map.Entry<String, Flight> f: sys.getFlightMap().entrySet()) {
        			String fcode = f.getValue().getFlightCode();
        			String log = "\n" + sys.getFlightReport(fcode);
        			logger.write(log);
        		}
        		logger.writeLogToFile();
        	}
        });
        
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        runtime = new JLabel("Enter runtime in seconds:");
        runtime.setSize(200, 100);

        
        input = new JTextField(5);
        button = new JButton("Set Sleep Time");

        button.addActionListener(this);

        newFrame.add(runtime, BorderLayout.NORTH);
        newFrame.add(input, BorderLayout.CENTER);
        newFrame.add(button, BorderLayout.SOUTH);

        button.addActionListener(this);
        newFrame.pack();
        newFrame.setVisible(true);

        getSleepTime();
    }

    public long getSleepTime() {
        return time;
    }
}