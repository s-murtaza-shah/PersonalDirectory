public class Contact {
	
	// Declare all variables needed for creating an object and initialize them to a default value
	private String firstName="";
	private String lastName="";
	private String workPhone="";
	private String streetAddress="";
	private String city="";
	private String province= "";
	private String country = "";
	private String email= "";
	private String cellNum = "";
	private String company="";
	private String jobTitle="";
	private int birthYear = 0;
	private boolean starred = false;
	private String notes="";
	private boolean inTrash = false;
	
	public Contact() {
	}
	
	public Contact(String firstName, String lastName, String workPhone, String cellNum, char yOrN) {
		this.firstName = firstName;
		this.lastName = lastName;	
		this.workPhone = workPhone;
		this.cellNum = cellNum;
		
		if (yOrN == 'n') {
			this.starred = false;
		}
		else {
			this.starred = true;
		}
	}
	
	public Contact(String firstName, String lastName, String workPhone,  String cellNum, String streetAddress, String city, String province, String country, String email, String company, String jobTitle, int birthYear, char YorN, String notes) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.workPhone = workPhone;
		this.cellNum = cellNum;
		this.streetAddress = streetAddress;
		this.city = city;
		this.province = province;
		this.country = country;
		this.email = email;
		this.company = company;
		this.jobTitle = jobTitle;
		this.birthYear = birthYear;
		
		if (YorN == 'n') {
			this.starred = false;
		}
		else {
			this.starred = true;
		}

		this.notes = notes;
	}
	
	public Contact(String CSVLine) {
		
		String[] data = CSVLine.split("\\|",20);
		
		this.firstName = data[0];
		this.lastName = data[1];
		this.workPhone = data[2];
		this.cellNum = data[3];
		this.streetAddress = data[4];
		this.city = data[5];
		this.province = data[6];
		this.country = data[7];
		this.email = data[8];
		this.company = data[9];
		this.jobTitle = data[10];
		this.birthYear = Integer.parseInt(data[11]);
		this.starred = Boolean.parseBoolean(data[12]);
		this.notes = data[13];
		this.inTrash = Boolean.parseBoolean(data[14]);
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return province;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCountry() {
		return country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setCellNum(String cellNum) {
		this.cellNum = cellNum;
	}
	
	public String getCellNum() {
		return cellNum;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompany() {
		return company;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobTitle() {
		return jobTitle;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	
	public int getBirthYear() {
		return birthYear;
	}
	
	public void setStarred(char YorN) {
		
		if (YorN == 'y') {
			this.starred = true;
		}
		else {
			this.starred = false;
		}
	}
	
	public boolean getStarred() {
		return this.starred;
	}
	
	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}
	
	public boolean getInTrash() {
		return inTrash;
	}
	
	/**
	 * Converts the integer that is the year of birth to a String
	 * 
	 * @return String - year of birth converted into a String
	 */
	public String getStringYear() {
		
		// return the String (int (year of birth) converted to String)
		return String.valueOf(birthYear);
	} // String getStringYear(void)
	
	/**
	 * Converts the boolean value of starred to String 
	 * 
	 * @return String - boolean value of starred converted to a String
	 */
	public String getStringStarred() {
		
		// return the converted String value from boolean
		return String.valueOf(starred);
	} // String getStringStarred(void)
	
	/**
	 * Displays the "minimized" version of the contact (fName, lName + cell number + email)
	 * 
	 * @param num int - the number when being displayed in a list
	 * @return String - the minimized version of contact
	 */
	public String displayContact(int num) {
		
		// Declare variable needed
		String display;
		
		// Format the information to display and store the formatted string in display
		display = String.format("%3d. %-50s %-40s %-55s", num, firstName + " " + lastName, cellNum, email);
		
		// return the String display
		return display;
	} // String displayContact(int)
	
	/**
	 * Word wraps a paragraph/ string (text) to the desired width.
	 * 
	 * @param text String - The text that needs to be word wrapped.
	 * @param width int - The width or how long each line should be until the text moves to the next line.
	 * @return String - the paragraph with all the lines concatenated so that it is word wrapped
	 */
	private String wordWrap(String text, int width) {
		
		// Word wrapping is done in a way so that the original text 
		// entered is not modified (new substrings are not created)
		
		// Declare variable needed
		String paragraph = "";
		
		// Declare and initialize the starting point to zero
	    int startPoint = 0; 
		  
		// Declare the ending point
		int endPoint; 
		  
		// Keep looping as long as the remaining portion of text or the text itself is greater than the
		// width entered for word wrapping
		while (text.substring(startPoint).length() > width) {
			
			// set endPoint to whatever the startingPoint is plus the width
			endPoint = startPoint + width;
			  
			// Keep decrementing endPoint by 1 until a space is found
			while (text.charAt(endPoint) != ' ') { 				 
				endPoint--;
			} // end while
			  
			// add the text from startingPoint to endPoint into paragraph plus a newline and 16 spaces to properly format it into a paragraph appearance
			paragraph += text.substring(startPoint, endPoint) + "\n                ";
			  
			// At the end, set the startPoint to whatever the endPoint happened to be + 1 (to avoid the space).
			startPoint = endPoint + 1;
		  
		} // end while (as long as text is greater than width)
		  
		// Add the remaining text as there may be something left since the loop exits when the
		// length of the remaining portion (or the whole thing) is less than the width to paragraph
		paragraph += (text.substring(startPoint)); 
		
		// return paragraph (the text/paragraph word wrapped)
		return paragraph;
	
	} // String wordWrap (String, int)

	/**
	 * Displays all the fields of the contact object and displays the notes field word wrapped if a line is longer than 115 characters
	 * 
	 * @return String - values of all fields concatenated ("expanded" view of a contact)
	 */
	public String toString() {
		
		// return the String in which the values of all the fields are concatenated
		return "\n    First name: " + firstName +
			   "\n     Last name: " + lastName +
			   "\n    Work phone: " + workPhone +
			   "\n   Cell number: " + cellNum + 
			   "\nStreet address: " + streetAddress +
			   "\n          City: " + city +
			   "\n      Province: " + province + 
			   "\n       Country: " + country + 
			   "\n         Email: " + email + 
			   "\n       Company: " + company +
			   "\n     Job title: " + jobTitle +
			   "\n Year of Birth: " + birthYear +
			   "\n       Starred: " + starred +
			   "\n         Notes: " + wordWrap(notes,115);
		
	} // String toString(void)
	
	/**
	 * Returns the values of all fields concatenated by a pipe "|" separating each of the values
	 * 
	 * @return String - the values of all fields separated by a pipe so that it can be written to a csv file
	 */
	public String toCSV() {
		
		// return the string of all values combined using a pipe
		return String.join("|", firstName, lastName, workPhone, cellNum, streetAddress, city, province, country, email, company, jobTitle, String.valueOf(birthYear), String.valueOf(starred), notes, String.valueOf(inTrash));
	} // String toCSV(void)
	
} // end class (Contact)
