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
    public void testsetFee() {
        baggage.setFee(60);
        assertEquals(60, baggage.getFee(), 0.01);
    }
    @Test
    public void testSetWeight() throws IllegalBaggageWeightException {
        

        // Test setting a valid weight
        baggage.setWeight(100);
        assertEquals(100, baggage.getWeight(),0.001);

        // Test setting a weight that is too high
        assertThrows(IllegalBaggageWeightException.class, () -> {
            baggage.setWeight(201);
        });

        // Test setting a weight that is too low
        assertThrows(IllegalBaggageWeightException.class, () -> {
            baggage.setWeight(-1);
        });
    }

    @Test
    public void testcalculateBaggageFee() {
        assertEquals(140, baggage.calculateBaggageFee(12, 35), 0.01);
    }
}
