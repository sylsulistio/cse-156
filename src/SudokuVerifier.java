import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class SudokuVerifier {
	private static Boolean validity; // Used for final input validity message
	
	// Method to check groupings of numbers
	public static Boolean arrayCheck(int[] array) {
		int i = 0;
		Arrays.sort(array); // Sorted to iterate through elements
		for (i = 0; i < array.length; i++) {
			if (array[i] > 9 || array[i] < 1) {	// Checks for invalid number
				System.out.print("Invalid number found! ");
				return false;
			}
			else if(array[i] != (i+1)) { // Checks for duplicate number
				System.out.printf("Invalid! ", i+1);
				validity = false; // Used for final input validity message
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// Reads file
		File file = new File("data/inputFileNumRep.txt");
		int[][] sudoku = new int[9][9];
		Scanner scnr = new Scanner(file);
		int i = 0;
		int j = 0;
		int k = 0;
		validity = true; // Innocent until proven guilty
		
		// Putting numbers into sudoku array
		try {
			String[] numberArray = scnr.nextLine().split(" ");
			for (i = 0; i < 9; i++) {
				for (j = 0; j < 9; j++) { 
					sudoku[i][j] = Integer.parseInt(numberArray[k]);
					k++;
				}
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Number missing!");
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// Checking rows
		int[] array = new int[9];
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) { 
				array[j] = sudoku[i][j];
			}
			System.out.printf("Checking row %d... ", i+1);
			arrayCheck(array);
			System.out.println();
		}
		
		// Checking columns
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) { 
				array[j] = sudoku[j][i];
			}
			System.out.printf("Checking column %d... ", i+1);
			arrayCheck(array);
			System.out.println();
		}
		
		// Checking subgrids
		/*  Each i loop checks a subgrid, and when each k if-statement is 
		 *  passed, the value of k used in the inner loop is altered so as
		 *  to not go over the array indices. The same principle is used 
		 *  with the i if-statements.
		 */ 
		i = 0;
		for (k = 0; k < 9; k++) {
			if (k < 3) {
				if (i < 3)
					for (; i < 3; i++) {
						for (j = 0; j < 3; j++) { 
							array[j+(i*3)] = sudoku[i+(k*3)][j];
						}
					}
				else if (i < 6)
					for (; i < 6; i++) {
						for (j = 0; j < 3; j++) { 
							array[j+((i-3)*3)] = sudoku[(i-3)+(k*3)][j];
						}
					}
				else if (i < 9) {
					for (; i < 9; i++) {
						for (j = 0; j < 3; j++) { 
							array[j+((i-6)*3)] = sudoku[(i-6)+(k*3)][j];
						}
					}
					i = 0;
				}
				System.out.printf("Checking subgrid %d... ", k+1);
				arrayCheck(array);
				System.out.println();
			}
			else if (k < 6) {
				if (i < 3)
					for (; i < 3; i++) {
						for (j = 0; j < 3; j++) { 
							array[j+(i*3)] = sudoku[i+((k-3)*3)][j+3];
						}
					}
				else if (i < 6)
					for (; i < 6; i++) {
						for (j = 0; j < 3; j++) { 
							array[j+((i-3)*3)] = sudoku[(i-3)+((k-3)*3)][j+3];
						}
					}
				else if (i < 9) {
					for (; i < 9; i++) {
						for (j = 0; j < 3; j++) { 
							array[j+((i-6)*3)] = sudoku[(i-6)+((k-3)*3)][j+3];
						}
					}
					i = 0;
				}
				System.out.printf("Checking subgrid %d... ", k+1);
				arrayCheck(array);
				System.out.println();
			}
			else if (k < 9) {
				if (i < 3)
					for (; i < 3; i++) {
						for (j = 0; j < 3; j++) { 
							array[j+(i*3)] = sudoku[i+((k-6)*3)][j+6];
						}
					}
				else if (i < 6)
					for (; i < 6; i++) {
						for (j = 0; j < 3; j++) { 
							array[j+((i-3)*3)] = sudoku[(i-3)+((k-6)*3)][j+6];
						}
					}
				else if (i < 9) {
					for (; i < 9; i++) {
						for (j = 0; j < 3; j++) { 
							array[j+((i-6)*3)] = sudoku[(i-6)+((k-6)*3)][j+6];
						}
					}
					i = 0;
				}
				System.out.printf("Checking subgrid %d... ", k+1);
				arrayCheck(array);
				System.out.println();
			}
		}
		System.out.println();
		
		// Input validity message
		if (validity) {
			System.out.println("Valid solution!");
		}
		else {
			System.out.println("Invalid solution! Please see above for details.");
		}
	}
	
}