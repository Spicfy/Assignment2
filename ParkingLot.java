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
	 * The delimiter that separates the parking lot design section from the parked
	 * car data section
	 */
	private static final String SECTIONER = "###";

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

		// determine numRows and numSpotsPerRow; you can do so by
		// writing your own code or alternatively completing the 
		// private calculateLotDimensions(...) that I have provided
		calculateLotDimensions(strFilename);
		

		// instantiate the lotDesign and occupancy variables!
		// WRITE YOUR CODE HERE!

		// populate lotDesign and occupancy; you can do so by
		// writing your own code or alternatively completing the 
		// private populateFromFile(...) that I have provided
		populateFromFile(strFilename);
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @param c is the car to be parked
	 */
	public void park(int i, int j, Car c) {
		// WRITE YOUR CODE HERE!
		if(canParkAt(i, j, c)){
			occupancy[i][j] = c;
			System.out.println(occupancy[i][j]);
		}
		else{
			System.out.println("Car "+ c + " cannot be parked at ("+ i +","+ j+ ")");
		}
	}

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the car removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Car remove(int i, int j) {
		// WRITE YOUR CODE HERE!
		if (i>numRows||j>numSpotsPerRow){
			return null;
		}
		Spot removed = occupancy[i][j];
		occupancy[i][j] = null;
		return removed; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD

	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */
	public boolean attemptParking(Car c, int timestamp) {
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numSpotsPerRow; j++){
				if(occupancy[i][j] == null){
					if(lotDesign[i][j]== CarType.LARGE){
						Spot newSpot = new Spot(c, timestamp);
						occupancy[i][j] = newSpot;
						return true;
					}
					else if(lotDesign[i][j] == CarType.REGULAR){
						if(c.getType() == CarType.LARGE){
							return false;
						}
						else{
							Spot newSpot = new Spot(c, timestamp);
						occupancy[i][j] = newSpot;
						return true;
						}
					}
					else if(lotDesign[i][j]==CarType.SMALL){
						if(c.getType() == CarType.LARGE || c.getType() == CarType.REGULAR){
							return false;
						}
						else{
							Spot newSpot = new Spot(c, timestamp);
						occupancy[i][j] = newSpot;
						return true;
						}
					}
					else{
						if(c.getType() == CarType.ELECTRIC){
							Spot newSpot = new Spot(c, timestamp);
						occupancy[i][j] = newSpot;
						return true;
						}
						else{
							return false;
						}
					}
				}
			
			}
		}
		return false;
		
	
	

		
		 
		
		// WRITE YOUR CODE HERE!
	 // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD
 // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD

	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {
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

	}

	/**
	 * @return the total occupancy of the parking lot (i.e., the total number of
	 *         cars parked in the lot)
	 */
	public int getTotalOccupancy() {
		int carcount = 0;
		for(int i = 0; i<numRows; i++){
			for(int j = 0; j< numSpotsPerRow; j ++){
				if(occupancy[i][j] != null){
					carcount ++;
				}
			}
		}
		return carcount; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD		
	}


	private void calculateLotDimensions(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));
		String s = scanner.nextLine();
		s = s.strip();
		int x = 0;
		int a = 0;
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			str = str.strip();
			str =str.replaceAll(SEPARATOR,"");
			str = str.replaceAll(" ","");
			if (str.equals(SECTIONER)){
				break;
			}
			else if(str.equals("")){

			}

			else{
				x++;
				
			}
		

		}
		
		
		for (int i =0;i<s.length();i++){
			char c = s.charAt(i);
			if (c==','|| c ==' '){


			}
			else{
				a++;
			}
		}
	
	
		numRows = x+1;
		numSpotsPerRow = a;
		
		scanner.close();
	}

	private void populateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));


		// YOU MAY NEED TO DEFINE SOME LOCAL VARIABLES HERE!
		int counter = -1;
		lotDesign = new CarType[numRows][numSpotsPerRow];
		occupancy = new Spot[numRows][numSpotsPerRow];		// while loop for reading the lot design
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			str = str.strip();
			str = str.replaceAll(SEPARATOR,"");
			str = str.replaceAll(" ","");
			counter++;
			if (str.equals(SECTIONER)){
				break;
			
			
			}
			else if (str.equals("")){
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

		// while loop for reading occupancy data
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			str = str.strip();
			str = str.replaceAll(" ","");
			if (str.equals("")){

			}
			else{
				String[] x = str.split(SEPARATOR);
				int i = Integer.parseInt(x[0]);
				int j = Integer.parseInt(x[1]);
				CarType type = Util.getCarTypeByLabel(x[2]);
				String platenum = x[3];
				Car c = new Car(type,platenum);
				if (canParkAt(i, j, c)){
					park(i, j, c);	;
				}
			}
			
			
				


			
			// WRITE YOUR CODE HERE!
		}

		scanner.close();
	}

	/**
	 * Produce string representation of the parking lot
	 * 
	 * @return String containing the parking lot information
	 */
	public String toString() {
		// NOTE: The implementation of this method is complete. You do NOT need to
		// change it for the assignment.
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

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the file to process. Next, it creates an instance of
	 * ParkingLot. Finally, it prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();
	
		System.out.print("Please enter the name of the file to process: ");
		
		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		ParkingLot lot = new ParkingLot(strFilename);

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		System.out.println("Number of cars currently parked in the lot: " + lot.getTotalOccupancy());

		System.out.print(lot);

	}
}
