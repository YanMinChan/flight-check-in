import javax.swing.*;
import java.util.Observable;

public class DeskDisplay extends JPanel implements Observer {

    private CheckInDesk desk;
    private int deskNumber;
    CheckInSystem cs;

    public DeskDisplay(CheckInDesk desk, int deskNumber) {
        this.desk = desk;
        this.deskNumber = deskNumber;
        this.setBorder(BorderFactory.createTitledBorder("Desk " +  deskNumber));
    }

    @Override
    public void update() 
    {
//        Booking currentPassenger = sim.getCurrentPassenger();
//        if (currentPassenger != null) {
//            this.setText(cs.CheckInDetails(currentPassenger));
//            this.revalidate();
//            this.repaint();
//    }
    }
}
