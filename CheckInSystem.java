import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

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
	 * @throws IllegalBookingReference 
	 */
	public void readFile(String fileName, String fileType) throws IllegalBookingReference {
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
	 * @throws IllegalBookingReference 
	 */
	private void processBooking(String line) throws IllegalBookingReference {
		try {
			// split parts by ","
			String parts [] = line.split(",");
			// store booking ref, passenger name, flight code and check in
			String bookingRef = parts[0];
			String passengerName = parts[1];
			String flightCode = parts[2];
			boolean checkIn = Boolean.parseBoolean(parts[3]);

			// check booking reference
			//if (this.findByBookingRef(bookingRef).getBookingRef() != null) throw new IllegalBookingReference();

			// create booking object and add to the map
			Booking b = new Booking(bookingRef, passengerName, flightCode, checkIn);
			this.addBooking(b);
		}

		//for these two formatting errors, ignore lines in error and try and carry on
		
		//this catches trying to convert a String to an integer
		//YM: should we change this into an exception that catch boolean? Or it works for boolean too
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
		// catch (IllegalBookingReference ibr) {
		// 	System.out.println(ibr.getMessage());
		// }
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
			// store booking ref, passenger name, flight code and check in
			String destination = parts[0];
			String carrier = parts[1];
			
			//the capacity are at the end of the line
			int capLength = parts.length - 2;
			double flightCapacity[] = new double[capLength];
			for (int i = 0; i < capLength; i++) {
				flightCapacity[i] = Double.parseDouble(parts[i + 2].trim());
			}

			// create booking object and add to the map
			Flight f = new Flight(destination, carrier, flightCapacity);
			this.addFlight(f);
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

	public void addBooking(Booking b) throws IllegalBookingReference{
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
				totalPassengers ++;
			}
		}
		return totalPassengers;
	}
	
	public int totalWeight(String fcode) {
		int totalWeight = 0;
		for (Booking b : bookingMap.values()) {
			if (b.getFlightCode().equals(fcode)) {
				totalWeight += b.getBaggage().getWeight();
			}
		}
		return totalWeight;
	}
	
	public int totalVolume(String fcode) {
		int totalVolume = 0;
		for (Booking b : bookingMap.values()) {
			if (b.getFlightCode().equals(fcode)) {
				totalVolume += b.getBaggage().getDim();
			}
		}
		return totalVolume;
	}
	
	public int totalBaggageFees(String fcode) {
		int totalBaggageFees = 0;
		for (Booking b : bookingMap.values()) {
			if (b.getFlightCode().equals(fcode)) {
				totalBaggageFees += b.getBaggage().getFee();
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
	
	public void getFlightReport(String fileName) {
		StringBuilder report = new StringBuilder();
		for(String fcode : flightMap.keySet()){
		report.append("Flight Report for ").append(fcode).append(":\n");
        report.append("Total Passengers: ").append(totalPassenger(fcode)).append("\n");
        report.append("Total Weight: ").append(totalWeight(fcode)).append("\n");
        report.append("Total Volume: ").append(totalVolume(fcode)).append("\n");
        report.append("Total Baggage Fees: ").append(totalBaggageFees(fcode)).append("\n");
        report.append("Is Capacity Exceeded: ").append(isCapacityExceeded(fcode)).append("\n");}
        writeToFile(fileName, report.toString());
	}
	//details to display in GUI based on ref id
	public String DetailsByRefID(String bookRefField,String ln) throws IllegalBookingReference {
	    Booking details = findByBookingRef(bookRefField);
	    if (details != null) 
	    {
	    	if (details.getPassengerLastName().equals(ln))
	    	{
	        return "Booking Reference ID : " + details.getBookingRef() + "\n" +
	               "Passenger Name : " + details.getPassengerName() + "\n" +
	               "Flight Code : " + details.getFlightCode() + "\n" +
	               "Check In Status : " + details.getCheckIn() + "\n";
	    	}
	    	else
	    	{
	    		return "Passenger last name does not match" ; 
	    	}
	    } else {
	        return "Booking details not found for reference ID: " + bookRefField;
	    }
	}
	
	
	/*public String DetailsByLastName(String LastName) {
		
	    Booking details = findByLastName(LastName);
	    System.out.println("function return: "+ findByLastName(LastName));
	    System.out.println("detail: "+ details);
	    if (details != null) {
	        System.out.println("Found booking details: " + details.toString());
	        
	        return "Booking Reference ID : " + details.getBookingRef() + "\n" +
	               "Passenger Name : " + details.getPassengerName() + "\n" +
	               "Flight Code : " + details.getFlightCode() + "\n" +
	               "Check In Status : " + details.getCheckIn() + "\n";
	    } else {
	        return "Booking details not found for LastName: " + LastName;
	    }
	} 
	*/
	

	public HashMap<String, Booking> getBookingMap(){
		return bookingMap;
	}

	public HashMap<String, Flight> getFlightMap(){
		return flightMap;
	}

	public static void main(String[] args) throws IllegalBookingReference{

		//Initialize check in system and read booking.txt
		CheckInSystem sys = new CheckInSystem();
		sys.readFile("bookings.txt", "Booking");

		//Check if the readfile works for booking.txt
		String name = sys.getBookingMap().get("BR777888").getPassengerName();
		System.out.println(name);


		//read flights.txt
		sys.readFile("flights.txt", "Flight");

		//Check if readfile works for flight.txt
		String carrier = sys.getFlightMap().get("LAAA002").getCarrier();
		System.out.println(carrier);
		
		//String RefId = sys.DetailsByRefID("BR111222");
		//System.out.println(RefId);
		
		//String ln = sys.DetailsByLastName("Brown");
		//System.out.println(ln);
	

	
	}
	
}

