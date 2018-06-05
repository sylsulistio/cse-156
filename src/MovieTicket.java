
public class MovieTicket implements Product{

	private String code;
	private String type;
	private String productName;
	private String movieDateTime;
	private Address address;
	private String scrnNum;
	private double cost;
	
	public MovieTicket(String code, String type, String productName, String movieDateTime, Address address, String scrnNum, double cost) {
		this.setCode(code);
		this.setType(type);
		this.productName = productName;
		this.movieDateTime = movieDateTime;
		this.address = address;
		this.setScreenNum(scrnNum);
	}
	
	public String getName() {
		return this.productName;
	}
	
	public String getMovieDateTime() {
		return this.movieDateTime;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getScreenNum() {
		return scrnNum;
	}
	public void setScreenNum(String scrnNum) {
		/*
		 * Every last char is a letter, before that is numbers
		 * Check every character is num except for last one
		 */
		boolean isValid = false;
		// Set to upper case
		scrnNum = scrnNum.toUpperCase();
		
		// If the first characters are not numbers, it is invalid
		for (int i = 0; i < scrnNum.length()-1; i++) {
			if (scrnNum.charAt(i) >= 48 && scrnNum.charAt(i) <= 57) {
				isValid = true;
			}
			else {
				isValid = false;
			}
		}
		
		// If the final character is not a letter, it is invalid
		if (scrnNum.charAt(scrnNum.length()-1) < 65 ||
			scrnNum.charAt(scrnNum.length()-1) > 90) {
			isValid = false;
		}
		
		if (isValid) {
			this.scrnNum = scrnNum;
		}
		else {
			this.scrnNum = "(Invalid screen number)";
		}
	}

	@Override
	public void setType(String type) {
		// code only allows for four specific types, otherwise it is set to I
		// for "Invalid"
		for (String s: TYPE_ARRAY) {
			if (s.equals(type)) {
				this.type = s;
				return;
			}
			else {
				this.type = "I";
			}
		}
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public double getCost() {
		return cost;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}
}
