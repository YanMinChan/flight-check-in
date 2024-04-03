public class Booking {
    private String bookingReference;
	private String passengerName;
	private String flightCode;
	private boolean checkIn;
	private Baggage baggage;
	
	public Booking(String bookingRef, String passengerName, String flightCode, boolean checkIn) throws IllegalBookingReferenceException {
		if (bookingRef.length() != 8) throw new IllegalBookingReferenceException();
		bookingReference = bookingRef;
		this.passengerName = passengerName;
		this.flightCode = flightCode;
		this.checkIn = checkIn;
		baggage = new Baggage(0, 0);
	}
	public String getBookingRef() {		
		return bookingReference;
	}
	public String getPassengerName(){
		return passengerName;
	} 
	public String getFlightCode(){
		return flightCode;
	}
	public boolean getCheckIn(){
		return checkIn;
	}
	public Baggage getBaggage(){
		return baggage;
	}
	public void setBookingRef(String b){
		bookingReference=b;
	}
	public void setPassengerName(String p){
		passengerName=p;
	}
	public void setFlightCode(String f){
		flightCode=f;
	}
	public void setCheckIn(boolean c){
		checkIn=c;
	}
	public void setBaggage(Baggage ba){
		baggage = ba;
	}
	public String getPassengerLastName(){
		String[] Name = passengerName.split("\\s+");
		return Name[Name.length-1];
	}

	public String getCheckInDetails(Booking b) {
		String ret = "";
		String name = b.getPassengerName();
		double weight = b.getBaggage().getWeight();
		double fees = b.getBaggage().getFee();
		
		ret = name + " is dropping off 1 bag of " + Double.toString(weight) + "kg. A baggage fee of £" + Double.toString(fees) + " is due.";
		
		return ret;
	}
	

}
