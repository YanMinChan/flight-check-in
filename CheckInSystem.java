import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CheckInSystem {

	// instance variables
	HashMap<String, Flight> flightMap;
	HashMap<String, Booking> bookingMap;

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
	
	/** reads file with given name, extracting student data, creating student objects
	 * and adding them to the list of students
	 * Blank lines are skipped
	 * Validation for integer year, missing items
	 * @param filename the name of the input file
	 */
	public void readFile(String filename) {
		try {
			File f = new File(filename);
			Scanner scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				//read first line and process it
				String inputLine = scanner.nextLine(); 
				if (inputLine.length() != 0) {//ignored if blank line
					processLine(inputLine);
				}
			}
			scanner.close();
		}
		//if the file is not found, stop with system exit
		catch (FileNotFoundException fnf){
			 System.out.println( filename + " not found ");
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

	public void addBooking(Booking b){
		bookingMap.put(b.getBookingReference(), b);
	}

	public void addFlight(Flight f){
		flightMap.put(f.getFlightCode(), f);
	}

	public Booking findByLastName(String ln) {
		for (Booking b : bookingMap.values()) {
			if (b.getPassengerLastName().equals(ln)) {
				return b;
			}
		}
		return null;
	}

	public Booking findByBookingRef(String bref) {
		return bookingMap.get(bref);
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
				totalWeight += b.getBaggageWeight();
			}
		}
		return totalWeight;
	}
	
	public int totalVolume(String fcode) {
		int totalVolume = 0;
		for (Booking b : bookingMap.values()) {
			if (b.getFlightCode().equals(fcode)) {
				totalVolume += b.getBaggageVolume();
			}
		}
		return totalVolume;
	}
	
	public int totalBaggageFees(String fcode) {
		int totalBaggageFees = 0;
		for (Booking b : bookingMap.values()) {
			if (b.getFlightCode().equals(fcode)) {
				totalBaggageFees += b.getExcessBaggageFee();
			}
		}
		return totalBaggageFees;
	}
	
	public boolean isCapacityExceeded(String fcode) {
		if (!flightMap.containsKey(fcode)) {
			return false;
		}
		Flight f = flightMap.get(fcode);
		int totalPassengers = totalPassenger(fcode);
		return totalPassengers > f.getCapacity();
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

	public static void main(String[] args){
		
	}
}
