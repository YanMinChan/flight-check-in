public class Flight {

    private String flightCode;
    private String destinationAirport;
    private String carrier;
    private double[] capacity;
	
    public Flight(String destinationAirport, String carrier, double[] capacity,String fcode) throws IllegalCapacityException {
		if (capacity[0] == 0 || capacity[1] == 0 || capacity[2] == 0) {
            throw new IllegalCapacityException();
        }
        this.destinationAirport = destinationAirport;
        this,carrier = carrier;
        this.capacity = capacity;
        flightCode = fcode;
	}
    public String getFlightCode(){
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
