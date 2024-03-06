public class Flight {

    private String flightCode;
    private String destinationAirport;
    private String carrier;
    private double[] capacity;
	
    public Flight(String destinationAirport, String carrier, double[] capacity,String fcode) {
		if (capacity == null || capacity.length == 0) {
            throw new IllegalArgumentException("Capacity must be specified and cannot be empty");
        }
        this.destinationAirport = destinationAirport;
		this.carrier = carrier;
		this.capacity = capacity;
        flightCode = fcode;
	}

    public String getFlightCode(){
        if (flightCode == null) {
            throw new IllegalStateException("Flight code has not been set");
        }
        return flightCode;
    }

    public String getDest(){
        return this.destinationAirport;
    }

    public String getCarrier(){
        return this.carrier;
    }
    
    public double[] getCapacity(){
        return this.capacity;
    }
    
    public void setFlightCode(String f){
        if (f == null || f.isEmpty()) {
            throw new IllegalArgumentException("Flight code cannot be null or empty");
        }
        flightCode = f;
    }
    
    public void setDest(String d){
        destinationAirport=d;
    }
    
    public void setCarrier(String c){
        carrier=c;
    }
    
    public void setCapacity(double[] ca){
        capacity=ca;
    }
    
    public String getShortFlightDetails(){
        return "The flight code is "+flightCode+" and the destination is "+destinationAirport;

    }
    
    public String getFullFlightDetails(){
        return "The flight code is "+flightCode+" ,the destination is "+destinationAirport+" ,the capacity is "+capacity[0]+" ,the carrier is "+carrier;
    }

   


}
