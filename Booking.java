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
	public String getbookingref(){
		return bookingReference;
	}
	public String getpassengerName(){
		return passengerName;
	} 
	public String getflightCode(){
		return flightCode;
	}
	public boolean getcheckIn(){
		return checkIn;
	}
	public Baggage getBaggage(){
		return baggage;
	}
	public void setbookingref(String b){
		bookingReference=b;
	}
	public void setpassengerName(String p){
		passengerName=p;
	}
	public void setflightCode(String f){
		flightCode=f;
	}
	public void setcheckIn(boolean c){
		checkIn=c;
	}
	public void setbaggage(Baggage ba){
		baggage = ba;
	}
	public String getPassengerLastName(){
		String[] Name = passengerName.split("\\s+");
		return Name[Name.length-1];
	}

	
	

}
