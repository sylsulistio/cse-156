/* The purpose of this class is to break down the various 
 * addresses contained within a data file, using the following order:
 */
public class Address {
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;
	
	// Constructor uses string array for easier implementation elsewhere
	public Address(String[] array) {
		String street = array[0];
		String city = array[1];
		String state = array[2];
		String zip = array[3];
		String country  = array[4];
		this.street = street;
		this.setCity(city);
		this.setState(state);
		this.setZip(zip);
		this.setCountry(country);
	}

	// Street of address
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street.trim();
	}

	//city of address
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		city = city.trim();
		boolean validCity = true;
		if (city == null) {
			this.city = "(City unknown)";
		}
		else {
			//city cannot haver numbers or other values besides typical language characters.
			for (int i = 0; i < city.length(); i++) {
				// If statements allow for hyphens and spaces in names
				if ((city.toLowerCase()).charAt(i) < 97 &&
					(city.toLowerCase()).charAt(i) != 45 &&
					(city.toLowerCase()).charAt(i) != 32 ||
					(city.toLowerCase()).charAt(i) > 122) {
					validCity = false;
					this.city = "(Invalid city)";	 
					break;
				}
			}
		}
		if (validCity) {
			this.city = city;
		}
	}

	//Get the state, same rules apply
	public String getState() {
		return state;
	}
	public void setState(String state) {
		state = state.trim();
		boolean isState = true;
		if (state.length() != 2) {
			this.state = "(Invalid state)";
		}
		else {
			for (int i = 0; i < state.length(); i++) {
				if ((state.toLowerCase()).charAt(i) < 97 ||
					(state.toLowerCase()).charAt(i) > 122) {
					isState = false;
					this.state = "(Invalid state)";	 
					break;
				}
			}
		}
		if (isState)
		this.state = (state).toUpperCase();
	}

	//zip code, only valid numbers
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		/* Validation is currently limited to zipcode length,
		 * any ideas on how to further validate?
		 */
		zip = zip.trim();
		if (zip.length() != 5 && zip.length() != 10 && zip.length() != 7) {
			this.zip = "(Invalid zip code)";
		}
		else {
			this.zip = zip;
		}
	}

	//Get country, same rules as states and cities
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		country = country.trim();
		boolean validCountry = true;
		if (country == null) {
			this.country = "(Country unknown)";
		}
		else {
			for (int i = 0; i < country.length(); i++) {
				// If statements allow for hyphens and spaces in names
				if ((country.toLowerCase()).charAt(i) < 97 &&
					(country.toLowerCase()).charAt(i) != 45 &&
					(country.toLowerCase()).charAt(i) != 32 ||
					(country.toLowerCase()).charAt(i) > 122) {
					validCountry = false;
					this.country = "(Invalid country)";	 
					break;
				}
			}
		}
		if (validCountry) {
			this.country = country;
		}
	}
	
}
