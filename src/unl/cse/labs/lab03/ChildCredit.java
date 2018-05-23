package unl.cse.labs.lab03;

public class ChildCredit {

	public static void main(String args[]) {
		Child tom = new Child("Tommy", 2);
		Child dick = new Child("Richard", 18);
		Child harry = new Child("Harold", 19);
		Child x = new Child("s", -7);
		
		Child arr[] = new Child[4];
		arr[0] = tom;
		arr[1] = dick;
		arr[2] = harry;
		arr[3] = x;

		//TODO: write a loop to iterate over the elements in the child array 
		//      and output a table as specified
		
		String output = String.format("%-15s%-10s", "Child", "Amount");
		String childInfo = "";
		int totalCredit = 0;
		
		Boolean first = true;
		System.out.println(output);
		for (Child c : arr) {
			int credit = 500;
			if (c.getAge() < 18 && first && c.getAge() >= 0) {
				credit = 1000;
				first = false;
			}
			else if (c.getAge() >= 18 || c.getAge() < 0) {
				credit = 0;
			}
			totalCredit += credit;
			childInfo = String.format("%1s %1s", c.getName(), ("("+ c.getAge() + ")"));
			output = String.format("%-15s%-10s", childInfo, ("$" + credit + ".00"));
			System.out.println(output);
		}
		output = String.format("%-15s%-10s", "Total Credit:", ("$" + totalCredit + ".00"));
		System.out.println(output);
	}
}
