
public class Address {
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;
	
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		boolean validCity = true;
		if (city == null) {
			this.city = "(City unknown)";
		}
		else {
			for (int i = 0; i < city.length(); i++) {
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		if (zip.length() != 5 && zip.length() != 10) {
			this.zip = "(Invalid zip code)";
		}
		else {
			this.zip = zip;
		}
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		boolean validCountry = true;
		if (country == null) {
			this.city = "(Country unknown)";
		}
		else {
			for (int i = 0; i < country.length(); i++) {
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
