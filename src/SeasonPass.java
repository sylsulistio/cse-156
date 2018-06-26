import java.time.LocalDate;
import java.util.Calendar;

public class SeasonPass implements Product{
	
	private String code;
	private String type;
	private String invoiceDate;
	private String productName;
	private String startDate;
	private String endDate;
	private double cost;
	private int quantity;
	private double prorated;
	private boolean isProrated;
	private int activeTime;
	private int timeLeft;
	
	public SeasonPass(String salesCode, String salesType, String salesName, String salesStartDate, String salesEndDate,
			double salesCost) {
		this.setCode(salesCode);
		this.setType(salesType);
		this.productName = salesName;
		this.setStartDate(salesStartDate);
		this.setEndDate(salesEndDate);
		this.setCost(salesCost);
	}
	
	public SeasonPass(SeasonPass product, String invoiceDate) {
		this.setCode(product.getCode());
		this.setType(product.getType());
		this.productName = product.getName();
		this.setStartDate(product.getStartDate());
		this.setEndDate(product.getEndDate());
		this.cost = product.getCost();
		this.invoiceDate = invoiceDate;
		this.setProrated();
	}

	public String getName() {
		return this.productName;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String[] date = startDate.split("-");
		boolean isValid = true;
		
		// If year is less than 1900, or ten years later than current year,
		// it is invalid
		if (Integer.parseInt(date[0]) < 1900 || 
			Integer.parseInt(date[0]) >	(currentYear + 10)) {
			isValid = false;
		}
		// If month value is under 1 or more than 12, it is invalid
		else if (Integer.parseInt(date[1]) < 1 || 
				 Integer.parseInt(date[1]) > 12) {
			isValid = false;
		}
		// If date value is under 1 or more than 31, it is invalid
		else if (Integer.parseInt(date[2]) < 1 || 
				 Integer.parseInt(date[2]) > 31) {
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
		String[] date = endDate.split("-");
		boolean isValid = true;
		
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
		
		if (isValid) {
			this.endDate = endDate;
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

	public double getProrated() {
		return prorated;
	}

	public void setProrated() {
		String[] startDate = this.startDate.split("-");
		String[] endDate = this.endDate.split("-");
		String[] invoiceDate = this.invoiceDate.split("-");
		
		LocalDate startCal = LocalDate.of(Integer.parseInt(startDate[0]), Integer.parseInt(startDate[1]), 
				Integer.parseInt(startDate[2]));
		LocalDate endCal = LocalDate.of(Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]), 
				Integer.parseInt(endDate[2]));
		LocalDate invoiceCal = LocalDate.of(Integer.parseInt(invoiceDate[0]), Integer.parseInt(invoiceDate[1]), 
				Integer.parseInt(invoiceDate[2]));
		
		if (invoiceCal.compareTo(startCal) < 0) {
			this.prorated = 0;
		}
		else if (invoiceCal.compareTo(startCal) > 0) {
			if (invoiceCal.compareTo(endCal) >= 0) {
				this.prorated = (this.cost+8)*this.quantity;
			}
			else {
				// Length of time the pass is active
				int timeUntilExpiration = startCal.until(endCal).getDays();
				this.activeTime  = timeUntilExpiration;
				
				// Length of time from invoice date that the pass is active
				int timeLeft = invoiceCal.until(endCal).getDays();
				this.timeLeft = timeLeft;
				
				// Length of time that needs to be accounted for
				int timeProrated = timeUntilExpiration - timeLeft;
				
				// If there is time that needs to be accounted for
				if (timeProrated > 0) {
					this.prorated = (this.cost/timeUntilExpiration)*timeProrated;
					this.isProrated = true;
				}
			}
		}
	}

	public boolean isProrated() {
		return isProrated;
	}

	public void setProrated(boolean isProrated) {
		this.isProrated = isProrated;
	}

	public int getActiveTime() {
		return activeTime;
	}

	public int getTimeLeft() {
		return timeLeft;
	}
}
