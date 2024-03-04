import static org.junit.Assert.*;
import org.junit.*;
public class BaggageTest {
    private Baggage baggage;

    @Before
    public void setUp() {
        baggage = new Baggage(10, 25, 60); // Sample values for dimension, weight, and fee
    }

    @Test
    public void testgetDim() {
        assertEquals(10, baggage.getDim(),0.001);
    }

    @Test
    public void testgetWeight() {
        assertEquals(25, baggage.getWeight(),0.0001);
    }

    @Test
    public void testgetFee() {
        assertEquals(60, baggage.getFee(), 0.001);
    }

    @Test
    public void testsetDim() {
        baggage.setDim(15);
        assertEquals(15, baggage.getDim(), 0.005);
    }


   
    @Test
    public void testSetFee() {
        baggage.setFee(60);
        assertEquals(60, baggage.getFee(), 0.01);
    }

    @Test
    public void testCalculateBaggageFee() {
        assertEquals(140, baggage.calculateBaggageFee(12, 35), 0.01);
    }
}
