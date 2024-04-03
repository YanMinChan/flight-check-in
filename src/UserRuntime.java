import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserRunTime implements ActionListener {
    private JFrame frame;
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private long sleepTime;

    public UserRunTime() {
        frame = new JFrame("User Run Time");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 100);

        label = new JLabel("Enter thread sleep time in seconds:");
        textField = new JTextField(5);
        button = new JButton("Set Sleep Time");

        button.addActionListener(this);

        frame.add(label, BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sleepTime = Long.parseLong(textField.getText()) * 1000;
        frame.dispose();
        JOptionPane.showMessageDialog(null, "Thread sleep time set to: " + sleepTime + " milliseconds.");
    }

    public long getSleepTime() {
        return sleepTime;
    }
}