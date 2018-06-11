
//this file reads product sales information based on the given parameters of a data file
public interface Product {
	
	final String[] TYPE_ARRAY = {"M", "S", "R", "P"};
	void setCode(String code);
	String getCode();
	void setType(String type);
	String getType();
	void setCost(double cost);
	double getCost();
	void setQuantity(int quantity);
	int getQuantity();
}
