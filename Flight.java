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
    public String getflightCode(){
        return this.flightCode;
    }
    public String getdest(){
        return this.destinationAirport;
    }
    public String getcarrier(){
        return this.carrier;
    }
    public int[] getcapacity(){
        return this.capacity;
    }
    public void setflightCode(String f){
        flightCode=f;
    }
    public void setdest(String d){
        destinationAirport=d;
    }
    public void setcarrier(String c){
        carrier=c;
    }
    public void setcapacity(int[] ca){
        capacity=ca;
    }
    public String getShortFlightDetails(){
        return "The flight code is "+flightCode+" and the destination is "+destinationAirport;

    }
    public String getFullFlightDetails(){
        return "The flight code is "+flightCode+" ,the destination is "+destinationAirport+" ,the capacity is "+capacity[0]+" ,the carrier is "+carrier;
    }



    


}
