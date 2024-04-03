import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserRuntime implements ActionListener {
    private JLabel runtime;
    private JFrame newFrame;
    private JTextField input;
    private JButton button;
    private long time;

    public UserRuntime() {
        newFrame = new JFrame("User Run Time");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        runtime.setSize(200, 100);

        runtime = new JLabel("Enter runtime in seconds:");
        input = new JTextField(5);
        button = new JButton("Set Sleep Time");

        button.addActionListener(this);

        newFrame.add(runtime, BorderLayout.NORTH);
        newFrame.add(input, BorderLayout.CENTER);
        newFrame.add(button, BorderLayout.SOUTH);

        button.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time = Long.parseLong(input.getText()) * 1000;
        newFrame.dispose();
        JOptionPane.showMessageDialog(null, "Thread sleep time set to: " + time + " milliseconds.");
    }

    public long getSleepTime() {
        return time;
    }
}