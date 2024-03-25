import static org.junit.Assert.*;
import org.junit.*;
public class FlightTest {
    private Flight flight;
    @Before
    public void setUp() throws IllegalCapacityException{ 
        double[] arr = {1, 2, 3}; 
        flight = new Flight("Mumbai", "Vistara",arr,"MV001"); //sample values set up
    }
    @Test
    public void testgetFlightCode() { //test that checks getFlight() method
        assertEquals("MV001", flight.getFlightCode()); 
    }
    @Test
    public void testgetDest(){ //test that checks getDest() method
        assertEquals("Mumbai",flight.getDest());
    }
    @Test
    public void testgetCarrier(){ //test that checks getCarrier() method
        assertEquals("Vistara",flight.getCarrier());
    }
    @Test
    public void testsetFlightCode(){//test that checks setFlightCode() method
        flight.setFlightCode("MV002");
        assertEquals("MV002",flight.getFlightCode());


    }
    @Test
    public void testsetDest(){ //test that checks setDest() method
        flight.setDest("Muscat");
        assertEquals("Muscat",flight.getDest());

    }
    


    
}
