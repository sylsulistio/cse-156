package Relativity;

import java.text.DecimalFormat;

public class Relativity {
	public static void main(String[] args) {
		double t = 10;
		double l = 150;
		double m = 125000;
		DecimalFormat format = new DecimalFormat("0.00");
		
		// Output
		System.out.println("Moving measurements:");
		System.out.printf("%-15s%s years", "Elapsed Time: ", t);
		System.out.printf("\n%-15s%s meters", "Craft Length: ", l);
		System.out.printf("\n%-15s%s kilograms\n\n", "Craft Mass: ", m);
		
		// Table begins
		System.out.println("Stationary measurements:");
		System.out.printf("%-10s%-10s%-10s%-12s", "v", "T", "L", "M");
		System.out.println("\n------------------------------------------");
		// v = 0.00c
		System.out.printf("%-10s%-10s%-10s%-10s", "0.00c", 
				format.format((t/(Math.sqrt(1)))) + "y",
				format.format((l*(Math.sqrt(1)))) + "m",
				format.format((m/(Math.sqrt(1)))) + "kg");
		// v = 0.10c
		System.out.printf("\n%-10s%-10s%-10s%-10s", "0.10c", 
				format.format((t/(Math.sqrt(0.99)))) + "y",
				format.format((l*(Math.sqrt(0.99)))) + "m",
				format.format((m/(Math.sqrt(0.99)))) + "kg");
		// v = 0.50c
		System.out.printf("\n%-10s%-10s%-10s%-10s", "0.50c", 
				format.format((t/(Math.sqrt(0.75)))) + "y",
				format.format((l*(Math.sqrt(0.75)))) + "m",
				format.format((m/(Math.sqrt(0.75)))) + "kg");
		// v = 0.75c
		System.out.printf("\n%-10s%-10s%-10s%-10s", "0.75c", 
				format.format((t/(Math.sqrt(0.4375)))) + "y",
				format.format((l*(Math.sqrt(0.4375)))) + "m",
				format.format((m/(Math.sqrt(0.4375)))) + "kg");		
		// v = 0.99c
		System.out.printf("\n%-10s%-10s%-10s%-10s", "0.99c", 
				format.format((t/(Math.sqrt(0.0199)))) + "y",
				format.format((l*(Math.sqrt(0.0199)))) + "m",
				format.format((m/(Math.sqrt(0.0199)))) + "kg");
	}
}
