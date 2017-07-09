package workout_tracker;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImportCSV {
	public static final int NUM_EXERCISES = 1299;
	public static final int NUM_ROWS = 4;
	
	// check for row numbers from csv
	public static boolean isNumber(String str) {
		try {
			double d = Double.parseDouble(str);
		}
		catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	// imports csv data into 2d array
	public static String[][] importFile(String fileName) throws FileNotFoundException {
		
		
		String[][] dataArrs = new String[NUM_EXERCISES][NUM_ROWS]; // return arrays
		boolean header = true; // ignore header
		int rowNum = 0; // row number
		int dataNum = 0; // data column number
		
		Scanner fileScanner = new Scanner(new File(fileName));
		
		// double scanner, one for lines, one for the data for each line
		while (fileScanner.hasNextLine()) {
			
			String lineData = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(lineData);
			lineScanner.useDelimiter("\t");
			
			dataNum = 0; // reset column

			while (lineScanner.hasNext()) {
				
				String data = lineScanner.next();
				if (!isNumber(data)) {
					
					// set data for all non header rows
					if (!header) {
						dataArrs[rowNum - 1][dataNum - 1] = data;
					}				
				}
				dataNum++;
			}
			
			rowNum++;
			lineScanner.close();
			
			if (header) {
				header = false;
			}
		}
		
		fileScanner.close();
		return dataArrs;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		// import csv database
		String[][] dataArrays = importFile("exercise_database.csv");
		
		// print out all exercises
		for (int i = 0; i < dataArrays.length; i++) {
			for (int j = 0; j < dataArrays[i].length; j++) {
				System.out.print(dataArrays[i][j] + " | ");
			}
			System.out.println();
		}
	}
	
}