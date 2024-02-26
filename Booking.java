public class Booking {
    private String bookingReference;
	private String passengerName;
	private String flightCode;
	private boolean checkIn;
	private Baggage baggage;
	
	public Booking(String bookingRef, String passengerName, String flightCode, boolean checkIn) {
		bookingReference = bookingRef;
		this.passengerName = passengerName;
		this.flightCode = flightCode;
		this.checkIn = checkIn;
	}
	
	

}
