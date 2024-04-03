import javax.swing.*;
import java.util.Observable;

public class DeskDisplay extends JLabel implements Observer {

    private PassengerSimulator sim;
    private int deskNumber;
    CheckInSystem cs;

    public DeskDisplay(PassengerSimulator sim, int deskNumber) 
    {
        this.sim = sim;
        this.deskNumber = deskNumber;
    }

    @Override
    public void update() 
    {
        Booking currentPassenger = sim.getCurrentPassenger();
        if (currentPassenger != null) {
            this.setText(cs.CheckInDetails(currentPassenger));
            this.revalidate();
            this.repaint();
    }
    }
}
