package rotN;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class rotN {
	public static void main(String[] args) throws FileNotFoundException {
		int n = 5;
		int i = 0;
		int originalChar = 0;
		char encodedChar = 'A';
		String message = "";
		String encoded = "";
		
		// Reads file input
		File file = new File("data/inputFile.txt");
		Scanner scnr = new Scanner(file);
		
		message = scnr.nextLine();
		
		// Encoding begins
		for (; i < message.length(); i++) {
			originalChar = (int) message.charAt(i);
			// If the original character wouldn't cause any problems
			if (originalChar < (90-n) && originalChar < 97 && originalChar != 32) {
				encodedChar = (char) (originalChar+n);
				encoded += encodedChar;
			}
			// If the original character needs to wrap around
			else if (originalChar > (90-n) && originalChar < 97 && originalChar != 32) {
				encodedChar = (char) ((originalChar+n)-90+64);
				encoded += encodedChar;
			}
			// If the original character wouldn't cause any problems
			else if (originalChar < (122-n) && originalChar != 32) {
				encodedChar = (char) (originalChar+n);
				encoded += encodedChar;
			}
			// If the original character needs to wrap around
			else if (originalChar > (122-n) && originalChar != 32) {
				encodedChar = (char) ((originalChar+n)-122+96);
				encoded += encodedChar;
			}
			// Space characters
			else {
				encodedChar = (char) 32;
				encoded += encodedChar;
			}
		}
		// Prints out encoded message
		System.out.println(encoded);
	}
}
