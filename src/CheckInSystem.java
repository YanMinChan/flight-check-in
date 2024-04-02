import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class CheckInSystem {

	// instance variables
	HashMap<String, Flight> flightMap;
	HashMap<String, Booking> bookingMap;

	public CheckInSystem(){
		flightMap = new HashMap<String, Flight>();
		bookingMap = new HashMap<String, Booking>();
	}

	/**
	 * writes supplied text to file
	 * @param filename the name of the file to be written to
	 * @param report the text to be written to the file
	 */
	public void writeToFile(String filename, String report) {
	
		 FileWriter fw;
		 try {
		    fw = new FileWriter(filename);
		    fw.write("The report\n");
		    fw.write(report);
		 	fw.close();
		 }
		 //message and stop if file not found
		 catch (FileNotFoundException fnf){
			 System.out.println(filename + " not found ");
			 System.exit(0);
		 }
		 //stack trace here because we don't expect to come here
		 catch (IOException ioe){
		    ioe.printStackTrace();
		    System.exit(1);
		 }
	}
	
	/** reads file with given name
	 * and adding them to the list of students
	 * Blank lines are skipped
	 * Validation for integer year, missing items
	 * @param filename the name of the input file
	 */
	public void readFile(String fileName, String fileType) {
		try {
			File f = new File(fileName);
			Scanner scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				//read first line and process it
				String inputLine = scanner.nextLine(); 
				if (inputLine.length() != 0) {//ignored if blank line
					if (fileType.equals("Booking")){
						processBooking(inputLine);
					} else if (fileType.equals("Flight")){
						processFlight(inputLine);
					} else if (fileType.equals("Baggage")) {
						processBaggage(inputLine);
					}
				}
			}
			scanner.close();
		}
		//if the file is not found, stop with system exit
		catch (FileNotFoundException fnf){
			 System.out.println( fileName + " not found ");
			 System.exit(0);
		 }
	}
	
	/**
	 * Processes line, extracts data, creates Booking object
	 * and adds to list
	 * Checks for non-numeric check in and missing items
	 * @param line the line to be processed
	 */
	private void processBooking(String line) {
		try {
			// split parts by ","
			String parts [] = line.split(",");
			// store booking ref, passenger name, flight code and check in
			String bookingRef = parts[0];
			String passengerName = parts[1];
			String flightCode = parts[2];
			boolean checkIn = Boolean.parseBoolean(parts[3]);

			// create booking object and add to the map
			Booking b = new Booking(bookingRef, passengerName, flightCode, checkIn);
			this.addBooking(b);
		}

		//for these two formatting errors, ignore lines in error and try and carry on
		
		//this catches trying to convert a String to an integer
		catch (NumberFormatException nfe) {
			String error = "Number conversion error in '" + line + "'  - " 
			                  + nfe.getMessage();
			System.out.println(error);
		}
		//this catches missing items if only one or two items
		//other omissions will result in other errors
		catch (ArrayIndexOutOfBoundsException air) {
			String error = "Not enough items in  : '" + line
			                        + "' index position : " + air.getMessage();
			System.out.println(error);
		}
		//this catches illegal booking reference
		catch (IllegalBookingReferenceException ibr) {
			String error = "Booking reference length not 8 in: '" + line 
					+ "' index position: " + ibr.getMessage(); 
			System.out.println(error);
		}
	}

	/**
	 * Processes line, extracts data, creates Flight object
	 * and adds to list
	 * Checks for non-numeric capacity and missing items
	 * @param line the line to be processed
	 */
	private void processFlight(String line) {
		try {
			// split parts by ","
			String parts [] = line.split(",");
			// store destination, carrier and capacity
			String destination = parts[0];
			String carrier = parts[1];
			
			//the capacity are at the end of the line
			int capLength = parts.length - 3;
			double flightCapacity[] = new double[capLength];
			for (int i = 0; i < capLength; i++) {
				flightCapacity[i] = Double.parseDouble(parts[i + 2].trim());
			}
			String flightCode = parts[parts.length-1];

			// create flight object and add to the map
			Flight f = new Flight(destination, carrier, flightCapacity,flightCode);
			this.addFlight(f);
		}

		//for these two formatting errors, ignore lines in error and try and carry on
		
		//this catches situation where the capacity of the Flight object is illegal
		catch (IllegalCapacityException ice){
			    System.out.println("Error creating flight: " + ice.getMessage());
		}
		//this catches trying to convert a String to an integer
		catch (NumberFormatException nfe) {
			String error = "Number conversion error in '" + line + "'  - " 
			                  + nfe.getMessage();
			System.out.println(error);
		}
		//this catches missing items if only one or two items
		//other omissions will result in other errors
		catch (ArrayIndexOutOfBoundsException air) {
			String error = "Not enough items in  : '" + line
			                        + "' index position : " + air.getMessage();
			System.out.println(error);
		}
	}

	/**
	 * Processes line, extracts data, creates Baggage object
	 * and adds to list
	 * Checks for non-numeric capacity and missing items
	 * @param line the line to be processed
	 */
	private void processBaggage(String line) {
		try {
			// split parts by ","
			String parts [] = line.split(",");
			// store booking ref, passenger name, flight code and check in
			String bookingRef = parts[0];
			double dim = Double.parseDouble(parts[1]);
			double weight = Double.parseDouble(parts[2]);

			// create baggage object and add to the corresponding booking
			Baggage ba = new Baggage(dim, weight);
			ba.calculateBaggageFee(dim, weight);
			
			Booking b = this.findByBookingRef(bookingRef);
			b.setBaggage(ba);
		}

		//for these two formatting errors, ignore lines in error and try and carry on
		//this catches trying to convert a String to an integer
		catch (NumberFormatException nfe) {
			String error = "Number conversion error in '" + line + "'  - " 
			                  + nfe.getMessage();
			System.out.println(error);
		}
		//this catches missing items if only one or two items
		//other omissions will result in other errors
		catch (ArrayIndexOutOfBoundsException air) {
			String error = "Not enough items in  : '" + line
			                        + "' index position : " + air.getMessage();
			System.out.println(error);
		}
	}
	
	public void addBooking(Booking b) {
		bookingMap.put(b.getBookingRef(), b);
	}

	public void addFlight(Flight f){
		flightMap.put(f.getFlightCode(), f);
	}

	public Booking findByLastName(String ln) {
		for (Booking b : bookingMap.values()) {
			if (b.getPassengerLastName().equals(ln)) {
				System.out.println(b);
				return b;
			}
		}
		return null;
	}

	public Booking findByBookingRef(String bref) {
		return bookingMap.getOrDefault(bref,null);
	}

	public int totalPassenger(String fcode) {
		int totalPassengers = 0;
		for (Booking b : bookingMap.values()) {
			if (b.getFlightCode().equals(fcode)) {
				if(b.getCheckIn()){
				totalPassengers ++;}
			}
		}
		return totalPassengers;
	}
	
	public int totalWeight(String fcode) {
		int totalWeight = 0;
		for (Map.Entry<String, Booking> entry : bookingMap.entrySet()) {
			Booking booking = entry.getValue();
			String fc = booking.getFlightCode();
			Baggage baggage = booking.getBaggage();
			double baggageWeight = baggage.getWeight();
			if (fc.equals(fcode)) {
            double weight = baggage != null ? baggageWeight : 0;
            totalWeight += weight;
        }
    }
		return totalWeight;
	}
	
	public int totalVolume(String fcode) {
		int totalVolume = 0;
		for (Booking b : bookingMap.values()) {
			if (b.getFlightCode().equals(fcode)) {
				Baggage baggage = b.getBaggage();
            	double Vol = baggage != null ? baggage.getDim() : 0;
				totalVolume += Vol;
			}
		}
		return totalVolume;
	}
	
	public int totalBaggageFees(String fcode) {
		int totalBaggageFees = 0;
		for (Booking b : bookingMap.values()) {
			if (b.getFlightCode().equals(fcode)) {
				Baggage baggage = b.getBaggage();
            	double fees = baggage != null ? baggage.getFee() : 0;
				totalBaggageFees += fees;
			}
		}
		return totalBaggageFees;
	}
	
	// need to check for total Passenger, volume and weight
	public boolean isCapacityExceeded(String fcode) {
		if (!flightMap.containsKey(fcode)) {
	        return false;
	    }
	    Flight flight = flightMap.get(fcode);
	    int totalPassengers = totalPassenger(fcode);
	    int totalWeight = totalWeight(fcode);
	    int totalVolume = totalVolume(fcode);
	    
	    boolean isPassengerExceeded = totalPassengers > flight.getCapacity()[0];
	    boolean isWeightExceeded = totalWeight > flight.getCapacity()[1];
	    boolean isVolumeExceeded = totalVolume > flight.getCapacity()[2];
	    
	    return isPassengerExceeded || isWeightExceeded || isVolumeExceeded;
	}
	
	public String getFlightReport(String fcode) {
		StringBuilder report = new StringBuilder();
		
		report.append("Flight Report for ").append(fcode).append(":\n");
        report.append("Total Passengers: ").append(totalPassenger(fcode)).append("\n");
        report.append("Total Weight: ").append(totalWeight(fcode)).append("\n");
        report.append("Total Volume: ").append(totalVolume(fcode)).append("\n");
        report.append("Total Baggage Fees: ").append(totalBaggageFees(fcode)).append("\n");
        report.append("Is Capacity Exceeded: ").append(isCapacityExceeded(fcode)).append("\n");
       return report.toString();
	}
	//details to display in GUI based on ref id
	public String DetailsByRefID(String bookRefField,String ln) throws Exception {
	    Booking details = findByBookingRef(bookRefField);
	    if (details == null) throw new IllegalBookingReferenceException();
	    if (details.getCheckIn() == true) {
        	throw new Exception("User had already checked-in!");
        } 
    	if (details.getPassengerLastName().equals(ln))
    	{
			details.setCheckIn(true);
        return "Booking Reference ID : " + details.getBookingRef() + "\n" +
               "Passenger Name : " + details.getPassengerName() + "\n" +
               "Flight Code : " + details.getFlightCode() + "\n" +
               "Check In Status : TRUE" +  "\n";
    	}
    	else throw new Exception("Passenger last name does not match");
//    	{
//    		return "Passenger last name does not match" ; 
//    	}
	}
	
	public void addBaggageDetails(String bookRefField, int baggagedim, int baggageWeight,double fees) throws IllegalBaggageWeightException {
		Booking booking = findByBookingRef(bookRefField);
		if (booking != null) {
			Baggage baggage = booking.getBaggage();
			baggage.setDim(baggagedim);
			baggage.setWeight(baggageWeight);
			baggage.setFee(fees);
			booking.setBaggage(baggage);	       
		} 
	}

	public HashMap<String, Booking> getBookingMap(){
		return bookingMap;
	}

	public HashMap<String, Flight> getFlightMap(){
		return flightMap;
	}

	public String CheckInDetails(Booking b) {
		String ret = "";
		String name = b.getPassengerName();
		double weight = b.getBaggage().getWeight();
		double fees = b.getBaggage().getFee();
		
		ret = name + "is dropping off 1 bag of " + Double.toString(weight) + "kg. A baggage fee of £" + Double.toString(fees) + " is due.";
		
		return ret;
	}
	
	public String FlightDetails(Flight f) {
		String ret = "";
		String flightCode = f.getFlightCode();
		String dest = f.getDest();
		int pasCount = totalPassenger(flightCode);
		double flightCap = f.getCapacity()[0];
		double percentageFull = pasCount/flightCap;
		
		ret = flightCode + " " + dest + "\n" + Integer.toString(pasCount) + " checked in of " + String.format("%.0f", flightCap) + "\n" + "Hold is " + Double.toString(percentageFull) + "% full";
		
		return ret;
		
	}
//	public static void main(String[] args){

	 	//Initialize check in system and read booking.txt
//	 	CheckInSystem sys = new CheckInSystem();
//	 	sys.readFile("bookings.txt", "Booking");

//	 	//Check if the readfile works for booking.txt
//	 	String name = sys.getBookingMap().get("BR777888").getPassengerName();
//	 	System.out.println(name);


	 	//read flights.txt
//	 	sys.readFile("flights.txt", "Flight");

//	 	//Check if readfile works for flight.txt
//	 	String carrier = sys.getFlightMap().get("LAAA002").getCarrier();
//		System.out.println(carrier);
	 	
//	 	sys.readFile("baggage.txt", "Baggage");
//	 	double weight = sys.getBookingMap().get("BR246810").getBaggage().getWeight();
//	 	System.out.println(weight);
		
//		String RefId = sys.DetailsByRefID("BR111222", "Brown");
//		System.out.println(RefId);	
//	}
}

