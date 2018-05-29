import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Person {
	private String persCode;
	private String persName;
	private Address mailAddress;
	private String[] emailList;

	public Person(String persCode, String persName, Address mailAddress, String[] emailList) {
		this.persCode = persCode;
		this.setName(persName);
		this.setMail(mailAddress);
		this.setEmails(emailList);
	}
	
	//Get Person's Code
	public String getCode() {
		return persCode;
	}	
	public void setCode(String persCode) {
		this.persCode = persCode;
	}

	//Get Person's Name
	public String getName() {
		return persName;
	}
	public void setName(String persName) {
		// Setting format to first-last
		boolean validName = true;

		if (persName == null) {
			this.persName = "(First name unknown)";
		}
		else {
			String[] nameArray = persName.split(",");
			String nameString = nameArray[1].trim() + " " + nameArray[0].trim();
			persName = nameString;
			
			for (int i = 0; i < persName.length(); i++) {
				// If statements allow for hyphens, apostrophes, and spaces in names
				if ((persName.toLowerCase()).charAt(i) < 97 &&
					(persName.toLowerCase()).charAt(i) != 45 &&
					(persName.toLowerCase()).charAt(i) != 39 &&
					(persName.toLowerCase()).charAt(i) != 32||
					(persName.toLowerCase()).charAt(i) > 122) {
					this.persName = "(Invalid name)";
					validName = false;
					break;
				}
			}
		}
		if (validName) {
			this.persName = persName;
		}
	}
	
	//Mailing Address
	public Address getMail() {
		return mailAddress;
	}
	public void setMail(Address mailAddress) {
		this.mailAddress = mailAddress;
	}
	
	//List of email addresses
	public String[] getEmails() {
		return emailList;
	}
	public void setEmails(String[] emailList) {
		this.emailList = emailList;
	}
	
}