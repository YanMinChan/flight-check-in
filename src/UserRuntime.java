import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class UserRuntime implements ActionListener{
    private JLabel runtime;
    private JDialog dialog;
    private JTextField[] inputs;
    private JButton button;
    private int[] times;
    private CheckInSystem sys;
    private JFrame owner;

    public UserRuntime(JFrame owner, CheckInSystem sys) {
        this.owner = owner;
        this.sys = sys;

        inputs = new JTextField[4];
        times = new int[4];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i=0; i<4; i++) {
            times[i] = Integer.parseInt(inputs[i].getText());
        }
        dialog.dispose();
    }

    public void initialise(){
        dialog = new JDialog(owner,"Run Time",true);
        runtime = new JLabel("Enter runtime in seconds");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 4));

        for (int i=0; i<4; i++) {
            inputPanel.add(new JLabel("Desk " + (i+1)));
            inputs[i] = new JTextField(5);
            inputPanel.add(inputs[i]);
        }

        button = new JButton("Set Run Time");

        dialog.add(runtime, BorderLayout.NORTH);
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(button, BorderLayout.SOUTH);

        runtime.setSize(200,100);
        button.addActionListener(this);

        dialog.pack();
        dialog.setVisible(true);
    }

//    public void run(){
//        dialog = new JDialog(owner, "Run Time");
//
//        // write all flights details to log when close
////        newFrame.addWindowListener(new WindowAdapter() {
////        	@Override
////        	public void windowClosing(WindowEvent e) {
////        		// write log of flight details
////        		Log logger = Log.getInstance("log.txt");
////        		for(Map.Entry<String, Flight> f: sys.getFlightMap().entrySet()) {
////      		String fcode = f.getValue().getFlightCode();
////        			String log = "\n" + sys.getFlightReport(fcode);
////        			logger.write(log);
////        		}
////        		logger.writeLogToFile();
////        	}
////        });
//
//        dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        runtime = new JLabel("Enter runtime in seconds:");
//        runtime.setSize(200, 100);
//
//
//        input = new JTextField(5);
//        button = new JButton("Set Run Time");
//
//        button.addActionListener(this);
//
//        dialog.add(runtime, BorderLayout.NORTH);
//        dialog.add(input, BorderLayout.CENTER);
//        dialog.add(button, BorderLayout.SOUTH);
//
//        button.addActionListener(this);
//        dialog.pack();
//        dialog.setVisible(true);
//
//        getTime();
//    }

    public int[] getTime() {
        return times;
    }
}
