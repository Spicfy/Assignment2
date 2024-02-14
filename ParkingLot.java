import java.io.File;
import java.util.Scanner;

/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 */
public class ParkingLot {
	/**
	 * The delimiter that separates values
	 */
	private static final String SEPARATOR = ",";

	/**
	 * Instance variable for storing the number of rows in a parking lot
	 */
	private int numRows;

	/**
	 * Instance variable for storing the number of spaces per row in a parking lot
	 */
	private int numSpotsPerRow;

	/**
	 * Instance variable (two-dimensional array) for storing the lot design
	 */
	private CarType[][] lotDesign;

	/**
	 * Instance variable (two-dimensional array) for storing occupancy information
	 * for the spots in the lot
	 */
	private Spot[][] occupancy;

	/**
	 * Constructs a parking lot by loading a file
	 * 
	 * @param strFilename is the name of the file
	 */
	public ParkingLot(String strFilename) throws Exception {

		if (strFilename == null) {
			System.out.println("File name cannot be null.");
			return;
		}

		// WRITE YOUR CODE HERE!
		calculateLotDimensions(strFilename);
		
		lotDesign = new CarType[numRows][numSpotsPerRow];
		occupancy = new Spot[numRows][numSpotsPerRow];

		populateDesignFromFile(strFilename);
		
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumSpotsPerRow() {
		return numSpotsPerRow;
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i         is the parking row index
	 * @param j         is the index of the spot within row i
	 * @param c         is the car to be parked
	 * @param timestamp is the (simulated) time when the car gets parked in the lot
	 */
	public void park(int i, int j, Car c, int timestamp) {
		if (canParkAt(i,j,c)){
			occupancy[i][j]= new Spot(c, timestamp);		}
		

		// WRITE YOUR CODE HERE!		

	}

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Spot remove(int i, int j) {
		if (i>numRows||j>numSpotsPerRow){
			return null;
		}
		Spot removed = occupancy[i][j];
		occupancy[i][j]=null;
		return removed; 

		// WRITE YOUR CODE HERE!
		
	 // Remove this statement when your implementation is complete.

	}

	/**
	 * Returns the spot instance at a given position (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot instance at position (i, j)
	 */
	public Spot getSpotAt(int i, int j) {

		// WRITE YOUR CODE HERE!
		if (i>numRows||j>numSpotsPerRow){
			return null;
		}
		return occupancy[i][j]; 
		// Remove this statement when your implementation is complete.
	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 *
	 * NOTE: This method is complete; you do not need to change it.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */
	public boolean canParkAt(int i, int j, Car c) {
		

		// WRITE YOUR CODE HERE!
		if(i < numRows && j < numSpotsPerRow){
			if(lotDesign[i][j] == CarType.NA){
				return false;
			}
			if(occupancy[i][j] != null){
				return false;
			}
			else if(lotDesign[i][j] == CarType.LARGE){
				return true;			
			}
			else if(lotDesign[i][j] == CarType.REGULAR){
				if(c.getType() == CarType.LARGE){
					return false;
				}
				else{
					return true;
				}
			}
			else if(lotDesign[i][j] == CarType.SMALL){
				if(c.getType() == CarType.LARGE || c.getType() == CarType.REGULAR){
					return false;
				}
				else{
					return true;
				}
			
			}
			else{
				if(c.getType() == CarType.ELECTRIC){
					return true;
				}
				else{
					return false;
				}
			}
		}
		else{
			return false;
		}
		 // Remove this statement when your implementation is complete.
	}

	/**
	 * Attempts to park a car in the lot. Parking is successful if a suitable parking spot
	 * is available in the lot. If some suitable spot is found (anywhere in the lot), the car
	 * is parked at that spot with the indicated timestamp and the method returns "true".
	 * If no suitable spot is found, no parking action is taken and the method simply returns
	 * "false"
	 * 
	 * @param c is the car to be parked
	 * @param timestamp is the simulation time at which parking is attempted for car c 
	 * @return true if c is successfully parked somwhere in the lot, and false otherwise
	 */
	public boolean attemptParking(Car c, int timestamp) {

		// WRITE YOUR CODE HERE!
		for (int i =0;i<numRows;i++){
			for (int j=0;j<numSpotsPerRow;j++){
				if (canParkAt(i, j, c)){
					park(i, j, c, timestamp);
					return true;
				}
			}
		}
		
		return false; 
						// Remove this statement when your implementation is complete.

	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {

		// WRITE YOUR CODE HERE!
		int Ncount = 0;
		for (int i=0;i<numRows;i++){
			for (int j=0;j<numSpotsPerRow;j++){
				if(lotDesign[i][j]==CarType.NA){
					Ncount++;
				}

			}
		}
		// WRITE YOUR CODE HERE!
		int dimensions = (numRows*numSpotsPerRow) - Ncount;
		return dimensions; 
		 // Remove this statement when your implementation is complete.
	
	}

	/**
	 * @return the total occupancy of the parking lot
	 */
	public int getTotalOccupancy() {

		// WRITE YOUR CODE HERE!
		int carcount = 0;
		for(int i = 0; i<numRows; i++){
			for(int j = 0; j< numSpotsPerRow; j ++){
				if(occupancy[i][j] != null){
					carcount ++;
				}
			}
		}
		return carcount;
		 // Remove this statement when your implementation is complete.

	}

	private void calculateLotDimensions(String strFilename) throws Exception {
		
		Scanner scanner = new Scanner(new File(strFilename));

		while (scanner.hasNext()) {
			String str = scanner.nextLine().trim();

			if (str.isEmpty()) {
				// Do nothing
			} else {
				numRows++;
				String[] tokens = str.split(SEPARATOR);
				numSpotsPerRow = Integer.max(tokens.length, numSpotsPerRow);
			}
		}

		scanner.close();
		
		

		// WRITE YOUR CODE HERE!

	}

	private void populateDesignFromFile(String strFilename) throws Exception {


		// WRITE YOUR CODE HERE!
		Scanner scanner = new Scanner(new File(strFilename));


		// YOU MAY NEED TO DEFINE SOME LOCAL VARIABLES HERE!
		int counter = -1;
		
		// while loop for reading the lot design
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			str = str.strip();
			str = str.replaceAll(SEPARATOR,"");
			str = str.replaceAll(" ","");
			counter++;
			if (str.equals("")){
				counter=counter-1;
			}
			for (int i = 0;i<str.length();i++){
				char c = str.charAt(i);
				if (c == 'S'){
					lotDesign[counter][i]= CarType.SMALL;
					
				}
				else if (c == 'R'){
					lotDesign[counter][i] = CarType.REGULAR;
				}
				else if (c == 'L'){
					lotDesign[counter][i] = CarType.LARGE;
				
				}
				else if (c=='E'){
					lotDesign[counter][i] = CarType.ELECTRIC;

				}
				else if (c == 'N'){
					lotDesign[counter][i] = CarType.NA;
				}
		
				
				}
			

			// WRITE YOUR CODE HERE!
		}
		scanner.close();

	}

	/**
	 * NOTE: This method is complete; you do not need to change it.
	 * @return String containing the parking lot information
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("==== Lot Design ====").append(System.lineSeparator());

		for (int i = 0; i < lotDesign.length; i++) {
			for (int j = 0; j < lotDesign[0].length; j++) {
				buffer.append((lotDesign[i][j] != null) ? Util.getLabelByCarType(lotDesign[i][j])
						: Util.getLabelByCarType(CarType.NA));
				if (j < numSpotsPerRow - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(System.lineSeparator());
		}

		buffer.append(System.lineSeparator()).append("==== Parking Occupancy ====").append(System.lineSeparator());

		for (int i = 0; i < occupancy.length; i++) {
			for (int j = 0; j < occupancy[0].length; j++) {
				buffer.append(
						"(" + i + ", " + j + "): " + ((occupancy[i][j] != null) ? occupancy[i][j] : "Unoccupied"));
				buffer.append(System.lineSeparator());
			}

		}
		return buffer.toString();
	}
}
