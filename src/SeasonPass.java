import java.util.Calendar;

public class SeasonPass implements Product{
	
	private String code;
	private String type;
	private String productName;
	private String startDate;
	private String endDate;
	private double cost;
	private int quantity;
	private double discountedPrice;
	
	public SeasonPass(String salesCode, String salesType, String salesName, String salesStartDate, String salesEndDate,
			double salesCost) {
		this.setCode(salesCode);
		this.setType(salesType);
		this.productName = salesName;
		this.setStartDate(salesStartDate);
		this.setEndDate(salesEndDate);
		this.setCost(salesCost);
	}
	
	public String getName() {
		return this.productName;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String[] dateTime = startDate.split("-");
		boolean isValid = true;
		
		// If year is less than 1900, or ten years later than current year,
		// it is invalid
		if (Integer.parseInt(dateTime[0]) < 1900 || 
			Integer.parseInt(dateTime[0]) >	(currentYear + 10)) {
			isValid = false;
		}
		// If month value is under 1 or more than 12, it is invalid
		else if (Integer.parseInt(dateTime[1]) < 1 || 
				 Integer.parseInt(dateTime[1]) > 12) {
			isValid = false;
		}
		// If date value is under 1 or more than 31, it is invalid
		else if (Integer.parseInt(dateTime[2]) < 1 || 
				 Integer.parseInt(dateTime[2]) > 31) {
			isValid = false;
		}
		
		if (isValid) {
			this.startDate = startDate;
		}
		else {
			this.startDate = "(Invalid date)";
		}
	}
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		int currentDate = Calendar.getInstance().get(Calendar.DATE);
		String[] dateTime = endDate.split("-");
		boolean isValid = true;
		boolean isExpired = false;
		
		// If year is less than 1900, or ten years later than current year,
		// it is invalid
		if (Integer.parseInt(dateTime[0]) < 1900 || 
			Integer.parseInt(dateTime[0]) > (currentYear + 10)) {
			isValid = false;
		}
		// If month value is under 1 or more than 12, it is invalid
		else if (Integer.parseInt(dateTime[1]) < 1 || 
				 Integer.parseInt(dateTime[1]) > 12) {
			isValid = false;
		}
		// If date value is under 1 or more than 31, it is invalid
		else if (Integer.parseInt(dateTime[1]) < 1 || 
				 Integer.parseInt(dateTime[1]) > 31) {
			isValid = false;
		}
		
		if (Integer.parseInt(dateTime[0]) < currentYear) {
			isExpired = true;
		}
		if (Integer.parseInt(dateTime[1]) < currentMonth) {
			isExpired = true;
		}
		if (Integer.parseInt(dateTime[2]) < currentDate) {
			isExpired = true;
		}
		
		if (isValid && !isExpired) {
			this.endDate = endDate;
		}
		else if (isValid && isExpired){
			this.endDate = endDate + " (Expired)";
		}
		else if (!isValid) {
			this.endDate = "(Invalid date)";
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

	@Override
	public double getDiscount() {
		return discountedPrice;
	}
}
