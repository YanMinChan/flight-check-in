import static org.junit.Assert.*;
import org.junit.*;
public class BaggageTest {
    private Baggage baggage;

    @Before
    public void setUp() {
        baggage = new Baggage(10, 25); // values for dimension, weight, and fee
        baggage.setFee(60);
    }

    @Test
    public void testgetDim() { //testing that checks getDim() method
        assertEquals(10, baggage.getDim(),0.001);
    }

    @Test
    public void testgetWeight() { //test that checks for getWeight method
        assertEquals(25, baggage.getWeight(),0.0001);
    }

    @Test
    public void testgetFee() {//test that checks for getFee() method
        assertEquals(60, baggage.getFee(), 0.001);//testing for getFee() method
    }

    @Test
    public void testsetDim() { //test that checks setDim() method
        baggage.setDim(15);
        assertEquals(15, baggage.getDim(), 0.005); 
    }
    @Test
    public void testsetFee() {  //test that checks for setFee() method
        baggage.setFee(40);
        assertEquals(40, baggage.getFee(), 0.01); 
    }
    @Test
    public void testSetWeight() throws IllegalBaggageWeightException {
        

        //test setting a valid weight
        baggage.setWeight(80);
        assertEquals(80, baggage.getWeight(),0.001);

        //test setting a weight that is too high
        assertThrows(IllegalBaggageWeightException.class, () -> {
            baggage.setWeight(201);
        });

        //test setting a weight that is too low
        assertThrows(IllegalBaggageWeightException.class, () -> {
            baggage.setWeight(-1);
        });
    }

    @Test
    public void testcalculateBaggageFee() {
        assertEquals(140, baggage.calculateBaggageFee(12, 35), 0.01);
    }
}
