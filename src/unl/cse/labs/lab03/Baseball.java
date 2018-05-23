package unl.cse.labs.lab03;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Baseball {

	public static void main(String args[]) throws FileNotFoundException {
		
		String fileName = "data/mlb_nl_2011.txt";
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Team teams[] = new Team[16];

		//TODO: read in and process the data file, create teams and add them to the array
		for(int i = 0; i < teams.length; i++) {
			String infoStr = (s.nextLine().trim());
			String[] info = infoStr.split(" ");
			Team t = new Team(info[0], (Integer.parseInt(info[1])), (Integer.parseInt(info[2])));
			teams[i] = t;
		}
		
		System.out.println("Teams: ");
		for(Team t : teams) {
			System.out.println(t);
		}

		Arrays.sort(teams, new Comparator<Team>() {
			@Override
			public int compare(Team a, Team b) {
				return b.getWinPercentage().compareTo(a.getWinPercentage());
			}
			
		});
		
		System.out.println("\n\nSorted Teams: ");
		for(Team t : teams) {
			System.out.println(t);
		}
		
		//TODO: output the team array to a file as specified
		PrintWriter print = new PrintWriter("data/mlb_nl_2011_results.txt");
		for (Team t: teams) {
			String output = String.format("%10s %-10.2f\n", t.getName(), (t.getWinPercentage()*100));
			print.write(output);
		}
		print.close();
	}
	
}
