import java.util.Calendar;
import java.util.Date;

public class MovieTicket implements Product{

	private String code;
	private String type;
	private String productName;
	private String movieDateTime;

	private Address address;
	private String scrnNum;
	private double cost;
	private int quantity;
	private double discountAmount;
	
	public MovieTicket(String code, String type, String movieDateTime, String productName, Address address, String scrnNum, double cost) {
		this.setCode(code);
		this.setType(type);
		this.productName = productName;
		this.movieDateTime = movieDateTime;
		this.address = address;
		this.setScreenNum(scrnNum);
		this.cost = cost;
	}
	
	public MovieTicket(MovieTicket product) {
		this.setCode(product.getCode());
		this.setType(product.getType());
		this.productName = product.getName();
		this.movieDateTime = product.getMovieDateTime();
		this.address = product.getAddress();
		this.setScreenNum(product.getScreenNum());
		this.cost = product.getCost();
	}

	public String getName() {
		return this.productName;
	}
	
	public String getMovieDateTime() {
		return this.movieDateTime;
	}
	
	public void setMovieDateTime(String movieDateTime) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String[] dateTime = movieDateTime.split(" ");
		String[] date = dateTime[0].split("-");
		@SuppressWarnings("deprecation")
		Date dateObj = new Date(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateObj);
		boolean isValid = true;
		boolean isTueThur = false;
		
		// If year is less than 1900, or ten years later than current year,
		// it is invalid
		if (Integer.parseInt(date[0]) < 1900 || 
			Integer.parseInt(date[0]) > (currentYear + 10)) {
			isValid = false;
		}
		// If month value is under 1 or more than 12, it is invalid
		else if (Integer.parseInt(date[1]) < 1 || 
				 Integer.parseInt(date[1]) > 12) {
			isValid = false;
		}
		// If date value is under 1 or more than 31, it is invalid
		else if (Integer.parseInt(date[1]) < 1 || 
				 Integer.parseInt(date[1]) > 31) {
			isValid = false;
		}
		
		// Checks for Tue/Thur discount
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ||
			calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			isTueThur = true;
		}
		
		// If date is valid, the discount is calculated
		if (isValid && isTueThur) {
			double discountAmount = this.cost * 0.07;
			this.discountAmount = discountAmount;
			this.cost -= discountAmount;
			this.movieDateTime = movieDateTime;
		}
		// If date is invalid, an indicator is stored
		else if (!isValid) {
			this.movieDateTime = "(Invalid date)";
		}
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
	
	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public int getQuantity() {
		return this.quantity;
	}

	public double getDiscount() {
		return discountAmount;
	}
}
