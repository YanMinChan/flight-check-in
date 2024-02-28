public class Flight {
    private String flightCode;
    private String destinationAirport;
    private String carrier;
    private int[] capacity;
    public Flight(String flightC, String destinationAirport, String carrier, int[] capacity) {
		flightCode = flightC;
		this.destinationAirport = destinationAirport;
		this.carrier = carrier;
		this.capacity = capacity;
	}
    public String getFlightCode(){
        return this.flightCode;
    }
    public String getDest(){
        return this.destinationAirport;
    }
    public String getCarrier(){
        return this.carrier;
    }
    public int[] getCapacity(){
        return this.capacity;
    }
    public void setFlightCode(String f){
        flightCode=f;
    }
    public void setDest(String d){
        destinationAirport=d;
    }
    public void setCarrier(String c){
        carrier=c;
    }
    public void setCapacity(int[] ca){
        capacity=ca;
    }
    public String getShortFlightDetails(){
        return "The flight code is "+flightCode+" and the destination is "+destinationAirport;

    }
    public String getFullFlightDetails(){
        return "The flight code is "+flightCode+" ,the destination is "+destinationAirport+" ,the capacity is "+capacity[0]+" ,the carrier is "+carrier;
    }



    


}
