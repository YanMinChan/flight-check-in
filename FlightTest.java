import static org.junit.Assert.*;
import org.junit.*;
public class FlightTest {
    private Flight flight;
    @Before
    public void setUp(){
        double[] arr = {1, 2, 3};
        flight = new Flight("Mumbai", "Vistara",arr,"MV001");
    }
    @Test
    public void testgetFlightCode() {
        assertEquals("MV001", flight.getFlightCode());
    }
    @Test
    public void testgetDest(){
        assertEquals("Mumbai",flight.getDest());
    }
    @Test
    public void testgetCarrier(){
        assertEquals("Vistara",flight.getCarrier());
    }
    @Test
    public void testsetFlightCode(){
        flight.setFlightCode("MV002");
        assertEquals("MV002",flight.getFlightCode());


    }
    @Test
    public void testsetDest(){
        flight.setDest("Muscat");
        assertEquals("Muscat",flight.getDest());

    }
    


    
}
