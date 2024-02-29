public class Flight {
    private String flightCode;
    private String destinationAirport;
    private String carrier;
    private double[] capacity;

    public Flight(String destinationAirport, String carrier, double[] capacity) {
		this.destinationAirport = destinationAirport;
		this.carrier = carrier;
		this.capacity = capacity;
	}

    public String getFlightCode(){

        //initialize flight code
        String flightCode = "";

        //Abbreviate the dest using the initials of each word
        String dest[] = this.destinationAirport.split(" ");
        for (int i=0; i < dest.length; i++){
            flightCode += dest[i].charAt(0);
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
        flightCode=f;
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

    // public static void main(String[] args){
        
    //     Flight flight = new Flight();
    //     flight.getFlightCode();
    // }


}
