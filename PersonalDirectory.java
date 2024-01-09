
/**
 * GMI de Best Personal Directory
 * 
 * This program is a personal directory where the contacts of a user can be accessed from.
 * The user can choose from a variety of menu options in this program to manipulate their 
 * contacts (edit, add, delete, etc). The changes the user make is saved to the file (if user
 * wants to save them).
 * 
 * @author Murtaza Shah
 */

import java.util.*;
import java.io.*;

public class PersonalDirectory {

	// Declare and initialize global variables needed
	static Contact[] contacts = new Contact[100];
	static Contact[] trash = new Contact[50];
	static int entriesMade = 0, trashEntries = 0;
	static boolean updtFile = false;

	/**
	 * Checks if the file exists
	 * 
	 * @param fileName String - the name of the file to check
	 * @return boolean - either true or false depending on whether or not the file
	 *         exists
	 */
	public static boolean checkFileExists(String fileName) throws IOException {

		// Check if the file entered into the function exists or nor, store value in
		// exists (boolean)
		File tempFile = new File(fileName);
		boolean exists = tempFile.exists();

		// return true or false
		return exists;
	} // boolean checkFileExists(String)

	/**
	 * This method populates the two arrays (trash and contacts) with the
	 * appropriate data from the file
	 * where the contacts of the user are stored. If the file does not exist, it
	 * will create it first
	 * before opening the file to read in the data to the arrays.
	 */
	public static void populateArray() throws IOException {

		// Declare variables needed
		String text;
		Contact currentContact;

		// Check if file doesn't exist. If so, call the updateFile() method which opens
		// the file to write, so the
		// file can be created.
		if (!checkFileExists("gmi-de-best-contacts.csv")) {
			updateFile();
		} // end if

		// Open file to read
		BufferedReader in = new BufferedReader(new FileReader("gmi-de-best-contacts.csv"));

		// read data from file - store in text
		text = in.readLine();

		// If text is not null (not end of file)...
		while (text != null) {

			// Construct a new Contact object and store it in currentContact (use the
			// constructor which only requires on String)
			currentContact = new Contact(text);

			// Check if the contact object created above is supposed to be in trash (if
			// inTrash is true). If so, store that specific
			// contact object in the trash array and increment trashEntries by 1
			if (currentContact.getInTrash()) {
				trash[trashEntries] = currentContact;
				trashEntries++;
			}

			// Otherwise, contact is not supposed to be in trash, so store that contact
			// object in the main contacts array and
			// increment entriesMade by 1
			else {
				contacts[entriesMade] = currentContact;
				entriesMade++;
			} // end if

			// Read a line from the file
			text = in.readLine();
		} // end while

		// close file
		in.close();

	} // void populateArray(void)

	/**
	 * Displays the contact header for when the "minimized" versions of the contacts
	 * are being displayed
	 */
	public static void displayContactHeader() {

		// Display the headings using formatting, and display the underline
		System.out.printf("\n%3s. %-50s %-40s %-55s%n", "#", "Name (first last)", "Cell Number", "Email");
		System.out.println(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------");
	} // void displayContactHeader(void)

	/**
	 * Displays the title (Skips 2 lines, displays the title and then underlines it)
	 * 
	 * @param section String - the title of the section
	 */
	public static void displaySectionTitle(String section) {

		// Declare and initialize the underline String
		String underline = "";

		// Skip 2 lines and display the title
		System.out.println("\n\n" + section);

		// For loop to keep on adding equal signs to underline for however many
		// characters there are in "section" to get an appropriate
		// length of the underline.
		for (int i = 0; i < section.length(); i++) {
			underline += "=";
		} // end for

		// Display the underline
		System.out.println(underline);

	} // void displaySectionTitle(String)

	/**
	 * Waits for the user to press enter before continuing to the next stage
	 * 
	 * @param prompt String - a statement to tell user why to press enter
	 */
	public static void pressEnter(String prompt) {

		// Declare scanner
		Scanner s = new Scanner(System.in);

		// Display the prompt (what is happening/ why to press enter)
		System.out.print(prompt);

		// Prompt user to press enter
		s.nextLine();

	} // void pressEnter(String)

	/**
	 * Obtains a valid value from the user which is within the range of accepted
	 * integers
	 * 
	 * @param prompt  String - sentence to tell user why to enter a value
	 * @param lowest  int - the lowest acceptable integer
	 * @param highest int - the highest acceptable integer
	 * @return int - the valid value (integer) user entered
	 */
	public static int getValidVal(String prompt, int lowest, int highest) {

		// Declare and initialize the scanner
		Scanner s = new Scanner(System.in);

		// Declare choice (int)
		int choice;

		// Error trap for choice values
		while (true) {

			// Prompt the user to enter an integer, and store the value in choice.
			System.out.print(prompt);
			choice = s.nextInt();

			// Check if the entered integer is within the accepted range, if so, break out
			// of loop
			if (choice >= lowest && choice <= highest) {
				break;
			} // end if

			// If choice is not within the accepted range, then display an error message.
			System.out.println("Please enter a valid choice between " + lowest + " and " + highest + ".");
		} // end while (error trap for choice values)

		// return the valid integer that the user entered.
		return choice;

	} // int getValidVal(int, int)

	/**
	 * Obtains a valid character (depending on the situation there are different
	 * valid characters) from the user and returns that valid character
	 * 
	 * @param prompt     String - a statement that tells the user why to enter a
	 *                   character
	 * @param validChars - char[] - an array of all the valid characters
	 * @return char - the valid character chosen by user
	 */
	public static char getValidChar(String prompt, char[] validChars) {

		// Declare scanner
		Scanner s = new Scanner(System.in);

		// Declare variables needed
		char usrOpt;
		boolean validChar = false;

		// Error trap for obtaining a valid character
		while (true) {

			// Display the prompt
			System.out.print(prompt);

			// Get user's input, convert to lower case, and take the first char. Store it in
			// usrOpt
			usrOpt = s.nextLine().toLowerCase().charAt(0);

			// For loop to iterate through the validChars array
			for (int i = 0; i < validChars.length; i++) {

				// If the character the user entered was one of the valid characters, set
				// validChar (a valid char has been entered) to true,
				// and break out of the for loop
				if (validChars[i] == usrOpt) {
					validChar = true;
					break;
				} // end if
			} // end for

			// If a valid char was entered, break out of error trap (while loop)
			if (validChar == true) {
				break;
			} // end if

			// Otherwise if an invalid char was entered, display an error message
			System.out.println("Please enter a valid option.");
		} // end while

		// return usrOpt (the valid char entered by user)
		return usrOpt;

	} // char getValidChar(String, char[])

	/**
	 * Obtains either y or n from the user. Overloaded method (validChars are set to
	 * y or n by default if no array is passed in)
	 * 
	 * @param prompt String - a statement to tell user why to enter y or n
	 * @return char - either y or n
	 */
	public static char getValidChar(String prompt) {

		// Make an array of validChars
		char[] validChars = { 'y', 'n' };

		// return the valid character
		return getValidChar(prompt, validChars);

	} // char getValidChar(String)

	/**
	 * Displays the list of all the fields/ categories of a contact (name, address,
	 * email, etc.) and the corresponding chars of those
	 * categories. It then obtains a valid character from the user for which
	 * category the user wants.
	 * 
	 * @param prompt     String - description of what/ why to enter a valid
	 *                   character
	 * @param menuOption int - a number which indicates which menu option is calling
	 *                   this method
	 * @return char - a valid character that represents the category the user
	 *         selected (or x to exit if menu option is 2)
	 */
	public static char displayCategoryMenu(String prompt, int menuOption) {

		// Declare variable needed
		char choice;

		// Display prompt and the category menu
		System.out.println(prompt);
		System.out.println("a - First Name");
		System.out.println("b - Last Name");
		System.out.println("c - Work Phone");
		System.out.println("d - Cell Number");
		System.out.println("e - Street Address");
		System.out.println("f - City");
		System.out.println("g - Province");
		System.out.println("h - Country");
		System.out.println("i - email");
		System.out.println("j - Company");
		System.out.println("k - Job Title");
		System.out.println("l - Year of Birth");
		System.out.println("m - Starred");
		System.out.println("n - Notes");

		// If the menu option is 1, make an array of validChars accordingly, and pass in
		// the array and the prompt that is for the menu option
		// 1. Store the return value from the getValidChar() method in choice
		if (menuOption == 1) {
			char[] validChars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n' };
			choice = getValidChar("\nEnter your choice (enter the letter corresponding to the category): ", validChars);
		}

		// If the menu option is 2, make an array of validChars and pass that array, and
		// the prompt into getValidChar() method accordingly for
		// option 2, and store the returned value in choice
		else {
			char[] validChars2 = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'x' };
			choice = getValidChar(
					"\nEnter your choice (enter the letter corresponding to the category) (enter x to exit): ",
					validChars2);
		} // end if

		// return choice (the valid char user entered when prompted to select a
		// category)
		return choice;

	} // char displayCategoryMenu(String, int)

	/**
	 * When passed in how many occurrences of an event occur (count), and the
	 * location of those occurrences (int spot array), this
	 * method obtains a valid value from the user representing the event chosen. It
	 * then returns an integer value accordingly to
	 * what the count is and what the user entered
	 * 
	 * @param prompt String - statement which tells user why to enter a valid value
	 *               (make a selection)
	 * @param spot   int[] - array of integers which stores all the indexes of the
	 *               desired event
	 * @param count  int - how many times the desired event occurred
	 * @return int - a value which indicates what the user selected, and what the
	 *         outcome was when a certain event was desired
	 */
	public static int obtainValidSelection(String prompt, int[] spot, int count) {

		// Declare variables needed
		int returnValue;
		int contactChoice;

		// If count is 0 - if the desired event never occurred, then set the return
		// value to -2
		if (count == 0) {
			returnValue = -2;
		}

		// If the desired event did occur
		else {

			// Call the getValidVal() method to obtain a valid value for which of the many
			// desired events the user wants to select
			// Store the returned value in contactChoice
			contactChoice = getValidVal(prompt, 0, count);

			// If user entered 0- they did not want o select any, and want to exit, set
			// returned value to -1
			if (contactChoice == 0) {
				returnValue = -1;
			}

			// Otherwise user wants to choose from a series of wanted events, and set the
			// returned value to the index of where that
			// event (contact) is found in the main array (contacts)
			else {
				returnValue = spot[contactChoice - 1];
			} // end if
		} // end if

		// return the value indicating the outcome of what the desired events were, how
		// many were there, and which one the user selected
		return returnValue;

	} // int obtainValidSelection(String, int[], int)

	/**
	 * Allows the user to search through the contacts (partial matches, zero
	 * matches, and different cases are handles). It keeps
	 * track of all the indexes of the search results in the main array (contacts)
	 * 
	 * @param promptSearch    String - statement which tells user why to search
	 * @param promptSelection String - statement which tells user why to enter a
	 *                        value to select a contact
	 * @return int - the index of the chosen contact in the main array (contacts)
	 */
	public static int searchContacts(String promptSearch, String promptSelection) {

		// Declare scanner
		Scanner s = new Scanner(System.in);

		// Declare variables needed
		String search;
		char searchByCategory;
		char whichCategory;

		// Declare and initialize variables needed to keep track of the indexes of the
		// wanted contacts
		int count = 0;
		int[] spot = new int[100];

		// Display prompt and get input from user (store it in search)
		System.out.print(promptSearch);
		search = s.nextLine();

		// Ask user if they want to search by a specific category, and store their
		// response in searchByCategory
		searchByCategory = getValidChar("\nWould you like to search by a specific category (y or n)? ");

		// If user wants to search by category...
		if (searchByCategory == 'y') {

			/// ...call the displayCategoryMenu() method to allow user to select the
			/// category they want to search by
			// Store their response in whichCategory
			whichCategory = displayCategoryMenu("\nWhich category do you want to search by:\n", 1);

			// Display the header for the table where all the wanted contacts may appear
			// (call the displayContactHeader() method)
			displayContactHeader();

			// Loop through the contacts array entriesMade times
			for (int i = 0; i < entriesMade; i++) {

				// Check if the search entered by user matches to the value of the category they
				// chose to search by for a contact
				if (whichCategory == 'a' && contacts[i].getFirstName().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'b'
								&& contacts[i].getLastName().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'c'
								&& contacts[i].getWorkPhone().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'd'
								&& contacts[i].getCellNum().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'e'
								&& contacts[i].getStreetAddress().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'f' && contacts[i].getCity().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'g'
								&& contacts[i].getProvince().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'h'
								&& contacts[i].getCountry().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'i' && contacts[i].getEmail().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'j'
								&& contacts[i].getCompany().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'k'
								&& contacts[i].getJobTitle().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'l'
								&& contacts[i].getStringYear().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'm'
								&& contacts[i].getStringStarred().toUpperCase().indexOf(search.toUpperCase()) != -1
						||
						whichCategory == 'n'
								&& contacts[i].getNotes().toUpperCase().indexOf(search.toUpperCase()) != -1) {

					// if there is a match then record the index of the contact (in the contacts
					// array) the match is found in and
					// increment count by 1, and display the "minimized" version of that specific
					// contact
					spot[count] = i;
					count++;

					// Display the contact (pass in count to display the numbers when contact is
					// being displayed in a list)
					System.out.println(contacts[i].displayContact(count));

				} // end if
			} // end for

		}
		// If user doesn't want to search by a specific category...
		else {

			// Display the header for the table where all the wanted contacts may appear
			// (call the displayContactHeader() method)
			displayContactHeader();

			// Iterate through the contacts array entriesMade times
			for (int i = 0; i < entriesMade; i++) {

				// Check if there is a match between the user's search and any of the values of
				// any of the categories
				if (contacts[i].getFirstName().toUpperCase().indexOf(search.toUpperCase()) != -1
						|| contacts[i].getLastName().toUpperCase().indexOf(search.toUpperCase()) != -1 ||
						contacts[i].getWorkPhone().toUpperCase().indexOf(search.toUpperCase()) != -1
						|| contacts[i].getStreetAddress().toUpperCase().indexOf(search.toUpperCase()) != -1 ||
						contacts[i].getCity().toUpperCase().indexOf(search.toUpperCase()) != -1
						|| contacts[i].getProvince().toUpperCase().indexOf(search.toUpperCase()) != -1 ||
						contacts[i].getCountry().toUpperCase().indexOf(search.toUpperCase()) != -1
						|| contacts[i].getEmail().toUpperCase().indexOf(search.toUpperCase()) != -1 ||
						contacts[i].getCellNum().toUpperCase().indexOf(search.toUpperCase()) != -1
						|| contacts[i].getCompany().toUpperCase().indexOf(search.toUpperCase()) != -1 ||
						contacts[i].getJobTitle().toUpperCase().indexOf(search.toUpperCase()) != -1
						|| contacts[i].getStringYear().toUpperCase().indexOf(search.toUpperCase()) != -1 ||
						contacts[i].getStringStarred().toUpperCase().indexOf(search.toUpperCase()) != -1
						|| contacts[i].getNotes().toUpperCase().indexOf(search.toUpperCase()) != -1) {

					// if there is a match then record the index of the contact (in the contacts
					// array) the match is found in and
					// increment count by 1, and display the "minimized" version of that specific
					// contact
					spot[count] = i;
					count++;
					// Display the contact (pass in count to display the numbers when contact is
					// being displayed in a list)
					System.out.println(contacts[i].displayContact(count));

				} // end if
			} // end for

		} // end if

		// Call the obtainValidSelection() method (pass in prompt, spot array, and
		// count) to get the integer which indicates what happened (no
		// contacts were found - -2, user exited - -1, or if user selected a contact).
		// Return the value obtained from the obtainValidSelection() method
		return obtainValidSelection(promptSelection, spot, count);

	} // int searchContacts(String, String)

	/**
	 * Depending on what num is (the value which indicates what happened with the
	 * desired contacts), this method acts appropriately on
	 * what to do next (display the contact, not display it, etc,)
	 * 
	 * @param num                   int - the value which indicates what happened
	 *                              with the desired contacts
	 * @param nothingFoundStatement String - statement which is displayed if there
	 *                              were no desired contacts
	 * @param duplicateContacts     Contact[] - the Contact[] which is the duplicate
	 *                              of the main contacts array from which to display
	 *                              the contact
	 * @param trash                 boolean - true or false depending if this method
	 *                              is called from the displayTrash() method or not
	 */
	public static void takeAppropriateAction(int num, String nothingFoundStatement, Contact[] duplicateContacts,
			boolean trash) {

		// If num not equal to -1, then continue with the rest (-1 means that user
		// wanted to exit)
		if (num != -1) {

			// If num equals -2, no contacts found, then display the statement
			// (nothingFoundStatement)
			if (num == -2) {
				System.out.println(nothingFoundStatement);
			}
			// If user selected a valid desired contact
			else {

				// Display the expanded contact from the duplicated contacts array
				System.out.println(duplicateContacts[num].toString());

				// If this method is called from within trash
				if (trash == true) {

					// Ask user if they want to recover the contact which is currently in trash
					// (call getValidChar() method)
					char recover = getValidChar("\nDo you want to recover this contact (y or n)? ");

					// If user wants to recover the contact
					if (recover == 'y') {

						// Call the recoverContact() method and pass in num (the index of that specific
						// contact)
						recoverContact(num);
					}

					// If user does not want to recover
					else {

						// Ask user if the permanently want to delete this contact (call getValidChar())
						char deleteForever = getValidChar(
								"\nDo you want to permanently delete this contact (y or n)? ");

						// if user wanted to delete the contact forever, call the deletePermanently()
						// method and pass in num (index of the contact)
						if (deleteForever == 'y') {
							deletePermanently(num);
						} // end if
					} // end if
				} // end if

			} // end if

			// Call pressEnter() to prompt user to press enter to continue
			pressEnter("\nPlease press enter to continue...");

		} // end if

	} // void takeAppropriateAction(int, String, Contact[], boolean)

	/**
	 * Overloaded method (takeAppropriateAction) where the Contact[]
	 * duplicateContacts is set to the main array (contacts) by default
	 * 
	 * @param num                   int - the index of the contact or the number
	 *                              which indicates what happened with the wanted
	 *                              contacts
	 * @param nothingFoundStatement String - statement which is displayed if there
	 *                              were no desired contacts
	 */
	public static void takeAppropriateAction(int num, String nothingFoundStatement) {
		takeAppropriateAction(num, nothingFoundStatement, contacts, false);
	} // void takeAppropriateAction(int, String)

	/**
	 * The search contacts menu option which allows user to search contacts and
	 * expand the contact they select
	 */
	public static void searchContactMenuOption() {

		// Declare variable needed
		int usrChoice;

		// Display the title using the displaySectionTitle() method and display opening
		// information
		displaySectionTitle("Search Your Contacts");
		System.out.println("Search ANYTHING you wish from ANY category!");
		System.out.println(
				"You can search text, numbers (if you want to find phone numbers or year of birth), or you can even search 'true' or");
		System.out.println(
				"'false' and select the 'starred' category to search by to search all starred (true) or un-starred (false) contacts.");

		// Call the searchContacts() method to allow user to search and store the
		// returned value (index of contact selected, or -1 if user
		// exited, or -2 if contacts were not found) in usrChoice
		usrChoice = searchContacts("\nEnter your search: ",
				"\nEnter the number of the contact you wish to expand (0 to exit): ");

		// Call the takeAppropriateAction() method to display accordingly to what the
		// usrChoice is (the returned value)
		takeAppropriateAction(usrChoice, "\nSorry, no search results found.");
	} // void searchContactMenuOption(void)

	/**
	 * Displays all the starred contacts and returns the num which indicates what
	 * happened when the desired outcome of displaying
	 * starred contacts was selected
	 * 
	 * @param selectionPrompt String - statement which tells user why to enter a
	 *                        valid integer value
	 * @return int - number which indicates what happened when the desired outcome
	 *         of displaying starred contacts was selected
	 */
	public static int displayStarredContacts(String selectionPrompt) {

		// Declare and initialize variables needed
		int count = 0;
		int[] spot = new int[100];

		// Tell the user that starred contacts are being displayed
		System.out.println("\nAll your starred contacts are displayed below:");

		// Display the heading of the table in which all the contacts will be displayed
		// in
		displayContactHeader();

		// Iterate through the contacts array entriesMade times
		for (int i = 0; i < entriesMade; i++) {

			// If the contact is starred, then store its index in the contacts array in the
			// spot array, increment count by 1, and display that
			// specific contact in minimized form
			if (contacts[i].getStarred() == true) {
				spot[count] = i;
				count++;
				System.out.println(contacts[i].displayContact(count));
			} // end if
		} // end for

		// return the value obtained by calling the obtainValidSelection() method
		return obtainValidSelection(selectionPrompt, spot, count);

	} // int displayStarredContacts(String)

	/**
	 * Displays all the contacts which are starred and allows user to select one to
	 * expand
	 */
	public static void displayStarredMenuOption() {

		// Declare variable needed
		int usrChoice;

		// Display the title for starred contacts
		displaySectionTitle("Starred Contacts");

		// Call the displayStarredContacts() method to obtain a valid selection from
		// user or the program if none starred contacts existed
		usrChoice = displayStarredContacts(
				"\nEnter the number of the starred contact you want to expand (0 to exit): ");

		// Call the takeAppropriateAction() to display the results accordingly
		takeAppropriateAction(usrChoice, "\nSorry, you have no starred contacts");

	} // void displayStarredMenuOption(void)

	/**
	 * Displays the all the contacts from oldest added to recently added. Returns
	 * num which indicates what happened when the desired outcome of displaying
	 * contacts in this order was selected
	 * 
	 * @param selectionPrompt String - statement which tells user why to select a
	 *                        contact
	 * @return int - the value obtained from the ontainValidSelection() method
	 *         (user's choice or a value that represents if no contacts were found
	 *         or user exited)
	 */
	public static int displayContactsOldToNew(String selectionPrompt) {

		// Declare variables needed to keep track of the indexes of all the contacts
		// desired
		int[] spot = new int[entriesMade];
		int count = entriesMade;

		// Display the table header
		displayContactHeader();

		// Iterate through the contacts array entriesMade times, and display the
		// contacts in the order they appear in the array (old to new)
		for (int i = 0; i < entriesMade; i++) {
			spot[i] = i;
			System.out.println(contacts[i].displayContact(i + 1));
		} // end for

		// Call the obtainValidSelection() method and return the value returned from
		// that function
		return obtainValidSelection(selectionPrompt, spot, count);

	} // int displayContactsOldToNew(String)

	/**
	 * Displays all the contacts (old to new) an allows user to select an option
	 * (contact)
	 */
	public static void displayOldToNewMenu() {

		// Declare variable needed
		int usrChoice;

		// Call the displayContactsOldToNew() method and store the returned value
		// (user's choice or a value which indicates what happened) in usrChoice
		usrChoice = displayContactsOldToNew("\nEnter the number of the contact you wish to expand (0 to exit): ");

		// Call the takeAppropriateAction() method to display the results accordingly
		takeAppropriateAction(usrChoice, "\nSorry, no contacts found.");
	} // void displayOldToNew(void)

	/**
	 * Allows user to delete a contact
	 */
	public static void deleteContact() {

		// Display the title
		displaySectionTitle("Delete Contact");

		// Declare/ initialize variables needed
		char proceed;
		int index;
		char usrChoice;
		char[] validChars = { 's', 'd', 't' };

		// Ask user how they wish to delete and call the getValidChar() method to obtain
		// a valid response/. Store response in usrChoice
		usrChoice = getValidChar(
				"\nHow do you wish to delete? (s - by searching, d - by viewing all contacts, t - by viewing all starred contacts): ",
				validChars);

		// If user would like to delete by searching, call the searchContacts() method,
		// and store the returned value in index
		if (usrChoice == 's') {
			index = searchContacts("\nSearch for what you want to delete: ",
					"\nEnter the number of the contact you wish to delete (0 to exit): ");
		}

		// If user wants to delete by viewing all contacts, call the
		// displayContactsOldToNew() method and store returned value in index
		else if (usrChoice == 'd') {
			index = displayContactsOldToNew("\nEnter the number of the contact you wish to delete (0 to exit): ");
		}

		// If user wants to delete by viewing all starred contacts, call the
		// displayStarredContacts() method and store returned value in index
		else {
			index = displayStarredContacts("\nEnter the number of the contact you wish to delete (0 to exit): ");
		} // end if

		// If index does not equal -1 (user doesn't want to exit)
		if (index != -1) {

			// If index equals -2 (contacts are not found)
			if (index == -2) {

				// Display that no contacts are found and call the pressEnter() method
				System.out.println("\nSorry, no contacts found.");
				pressEnter("\nPlease press enter to continue...");
			}

			// If index does not equal -2 (contacts are found)
			else {

				// Tell the user which contact they are deleting (display in maximized view)
				System.out.println("\nThe contact you are deleting is:");
				System.out.println(contacts[index].toString());

				// Ask user if they want to proceed by deleting this contact (store response in
				// proceed by calling the getValidChar() method)
				proceed = getValidChar("\nAre you sure you want to delete this contact (y or n)? ");

				// If proceed equals y (yes), the delete the contact...
				if (proceed == 'y') {

					// If there is space in trash then put the contact being deleted in trash
					if (trashEntries < 50) {

						// Set updtFile to true since a change has been made
						updtFile = true;

						// Set the inTrash value of that contact to true
						contacts[index].setInTrash(true);

						// Place that specific contact into the trash array and increment trashEntries
						// by 1
						trash[trashEntries] = contacts[index];
						trashEntries++;

						// Delete that specific contact from the main contacts array, and decrement
						// entriesMade by 1
						contacts[index] = null;
						entriesMade--;

						// Algorithm for moving every contact up by 1 in the array once a contact has
						// been deleted
						for (int i = index; i < 99; i++) {

							// If the next index of the contacts array is null, that means the last contact
							// has been adjusted and break out of loop
							if (contacts[i + 1] == null) {
								break;
							} // end if

							// Set the current index value to the next index value, and then set the next
							// index to null
							contacts[i] = contacts[i + 1];
							contacts[i + 1] = null;

						} // end for

						// Display how many more contacts the user can add, and the space in trash. Then
						// call the pressEnter() method
						System.out.printf("\nYou can now add %d new contact%s.%n", 100 - entriesMade,
								100 - entriesMade != 1 ? "s" : "");
						System.out.printf("Space in trash: %d contact%s.%n", 50 - trashEntries,
								50 - trashEntries != 1 ? "s" : "");
						pressEnter("\nPlease press enter to continue (contact moved to trash)...");
					}

					// If there is no space in trash
					else {

						// Ask user if they rather permanently delete the contact rather than having to
						// move it to trash
						char deletePermanently;
						deletePermanently = getValidChar(
								"\nSorry, you have run out of space in trash (50 contacts), would you like to permanently delete this contact instead (y or n)? ");

						// If they would like to permanently delete the contact
						if (deletePermanently == 'y') {

							// Set updtFile to true
							updtFile = true;

							// Decrement entriesMade by 1 and delete the contact from the main contacts
							// array
							entriesMade--;
							contacts[index] = null;

							// Iterate through the contacts array
							for (int i = index; i < 99; i++) {

								// If the next object int the array is null, break out of loop
								if (contacts[i + 1] == null) {
									break;
								} // end if

								// Shift the contacts up by 1
								contacts[i] = contacts[i + 1];
								contacts[i + 1] = null;

							} // end for

							// Call the pressEnter() method
							pressEnter("\nPlease press enter to continue (contact permanently deleted)...");
						}

						// If the user does not want to delete forever
						else {

							// Call the pressEnter() method
							pressEnter("\nPlease press enter to continue (contact not deleted permanently)...");
						} // end if
					} // end if
				}

				// If user does not want to delete (proceed equals n)
				else {

					// Call the pressEnter() method
					pressEnter("\nPlease press enter to continue (contact NOT deleted)...");
				} // end if
			} // end if
		} // end if

	} // void deleteContact(void)

	/**
	 * Allows user to view contacts in trash and allows them to select one if they
	 * want to expand it, recover it, or delete it forever
	 */
	public static void displayTrash() {

		// Declare variables needed
		int usrChoice;
		int count = trashEntries;
		int[] spot = new int[trashEntries];

		// Display the title and the opening information
		displaySectionTitle("View Contacts in Trash");
		System.out.println(
				"In trash, you can only view/expand your deleted contacts, or you can restore them (if enough space in main");
		System.out.println(
				"directory), or you can delete them forever. To be able to edit these contacts, you must restore them.");

		// Display total contacts in trash
		System.out.println("\nTrash (" + trashEntries + ")");

		// display the table header in which the contacts will be displayed
		displayContactHeader();

		// Iterate through the trash array trashEntries times and display all the
		// contacts in minimized form
		for (int i = 0; i < trashEntries; i++) {
			spot[i] = i;
			System.out.println(trash[i].displayContact(i + 1));
		} // end for

		// store value returned from the obtainValidSelection() method in usrChoice
		usrChoice = obtainValidSelection(
				"\nEnter the number of the contact you wish to expand/ recover/ delete forever, (0 to exit): ", spot,
				count);

		// Call the takeAppropriateAction() method to display results accordingly
		takeAppropriateAction(usrChoice, "\nSorry, no contacts in trash.", trash, true);

	} // void displayTrash(void)

	/**
	 * Allows user to recover a contact from trash into the main contacts array if
	 * there is enough pace
	 * 
	 * @param index int - the index of the contact object that has to be recovered
	 *              in the trash array
	 */
	public static void recoverContact(int index) {

		// If there is enough space to recover a contact in the main contacts array
		if (entriesMade < 100) {

			// Set updtFile to true
			updtFile = true;

			// Put that specific contact from the trash array into the main contacts array
			// Set the inTrash value for that specific contact to false
			// Increment entriesMade by 1
			contacts[entriesMade] = trash[index];
			contacts[entriesMade].setInTrash(false);
			entriesMade++;

			// Delete the contact from the trash
			// Decrement trashEntries by 1
			trash[index] = null;
			trashEntries--;

			// Iterate through the trash array from the index that the contact was deleted
			// from
			for (int i = index; i < 50; i++) {

				// If the next index is null, the last contact has been adjusted and is moved up
				// by 1, so break out of loop
				if (trash[i + 1] == null) {
					break;
				} // end if

				// Shift the contact objects up by 1
				trash[i] = trash[i + 1];
				trash[i + 1] = null;

			} // end for

			// Display what has happened and the storage space in trash and main contacts
			// array
			System.out.println("\n(contact recovered)");
			System.out.printf("You can now add %d new contact%s.%n", 100 - entriesMade,
					100 - entriesMade != 1 ? "s" : "");
			System.out.printf("Space in trash: %d contact%s.%n", 50 - trashEntries, 50 - trashEntries != 1 ? "s" : "");

		}

		// If not enough space in main contacts array, display an error message
		else {
			System.out.println(
					"\nSorry, you don't have enough space in your main directory to recover this, first remove some contacts from your main directory.");
		} // end if

	} // void recoverContact(int)

	/**
	 * Permanently deletes a contact from trash
	 * 
	 * @param index int - the index of the contact object to be deleted in the trash
	 *              array
	 */
	public static void deletePermanently(int index) {

		// Set updtFile to true
		updtFile = true;

		// Delete the contact from trash
		trash[index] = null;
		trashEntries--;

		// Adjust the trash array by moving every contact object up by 1 after the
		// delete has taken place
		for (int i = index; i < 50; i++) {

			if (trash[i + 1] == null) {
				break;
			}

			trash[i] = trash[i + 1];
			trash[i + 1] = null;

		} // end for

		// Display updated storage space and tell user what happened
		System.out.printf("\nSpace in trash: %d contact%s.%n", 50 - trashEntries, 50 - trashEntries != 1 ? "s" : "");
		System.out.println("Contact permanently deleted");

	} // void deletePermanently(int)

	/**
	 * Take input from the user while displaying the prompt and makes sure email has
	 * an @ sign and a period after the @ sign. It then
	 * returns the input
	 * 
	 * @param prompt String - tells user what they have to enter
	 * @param email  boolean - indicates whether or not an email is being entered
	 *               (true - email, false - no email)
	 * @return String - user's input
	 */
	public static String takeInput(String prompt, boolean email) {

		// Declare scanner
		Scanner s = new Scanner(System.in);

		// Declare variable needed
		String usrInp;

		// Start loop for taking input
		while (true) {

			// Display the prompt and take in input
			System.out.print(prompt);
			usrInp = s.nextLine();

			// If email is being entered
			if (email == true) {

				// Make sure the email has an @ sign and a period after it. If it does, break
				// out of loop
				if (usrInp.indexOf("@") != -1 && usrInp.lastIndexOf(".") > usrInp.indexOf("@") || usrInp.equals("")) {
					break;
				} // end if

				// If the email is invalid, display an error message
				System.out.println(
						"\nInvalid email address (you are either missing the @ sign or the dot after the @ sign or both).");
			}

			// if an email is not being entered, just break out of loop
			else {
				break;
			} // end if

		} // end while

		// return the user's input
		return usrInp;
	} // String takeInput(String, boolean)

	/**
	 * Obtains a valid year of birth from user
	 * 
	 * @return int - the valid year of birth
	 */
	public static int getValidBirthYear() {

		// Declare scanner
		Scanner s = new Scanner(System.in);

		// Declare variable needed
		int yOfB;

		// Error trap for year of birth values
		while (true) {

			// Prompt the user for year of birth
			System.out.print(
					"Enter contact's year of birth (enter 0 (default) if it is unknown, or it must be between 1900 and 2021): ");
			yOfB = s.nextInt();

			// If the year of birth is 0 or if it is between 1900 and 2021, break out of
			// loop
			if (yOfB == 0 || (yOfB >= 1900 && yOfB <= 2021)) {
				break;
			} // end if

			// If invalid, display an error message
			System.out.println(
					"\nInvalid date of birth (enter 0 (if unknown), or between 1900 (currently no one born before 1900) and 2021 (current year)).");
		} // end while

		// return the valid year of birth
		return yOfB;

	} // int getValidBirthYear()

	/**
	 * Allows user to edit a contact
	 */
	public static void editContact() {

		// Declare variables needed
		char proceed;
		char whichCategory;
		int index;
		char usrChoice;
		char[] validChars = { 's', 'd', 't' };

		// Display the title
		displaySectionTitle("Edit Contact");

		// Ask user how they wish to edit a contact (call getValidChar() method to get a
		// valid char)
		usrChoice = getValidChar(
				"\nHow do you wish to edit a contact? (s - by searching, d - by viewing all contacts, t - by viewing all starred contacts): ",
				validChars);

		// Depending on what the user selected, call the method that does that, and
		// store the returned value in index
		if (usrChoice == 's') {
			index = searchContacts("\nSearch for which contact you want to edit: ",
					"\nEnter the number of the contact you wish to edit (0 to exit): ");
		} else if (usrChoice == 'd') {
			index = displayContactsOldToNew("\nEnter the number of the contact you wish to edit (0 to exit): ");
		} else {
			index = displayStarredContacts("\nEnter the number of the contact you wish to edit (0 to exit): ");
		} // end if

		// If user did not want to exit
		if (index != -1) {

			// If no such contacts were found
			if (index == -2) {

				// Display a message that tells user that no contacts were found and call the
				// pressEnter() method
				System.out.println("\nSorry, no contacts found.");
				pressEnter("\nPlease enter to continue...");
			}

			// If contacts were found and user selected one
			else {

				// Display which contact the user is editing
				System.out.println("\nThe contact you are editing is:");
				System.out.println(contacts[index].toString());

				// Ask user if they want to proceed further in editing this contact
				proceed = getValidChar("\nAre you sure you want to edit this contact (y or n)? ");

				// If user is sure that they want to edit the contact...
				if (proceed == 'y') {

					// set updtFile to true
					updtFile = true;

					// Loop for continuously asking user to enter a category they wish to edit
					while (true) {

						// Call the displayCategoryMenu() method to obtain a valid character that
						// represents the category selected
						whichCategory = displayCategoryMenu("\nWhich category do you want to edit:\n", 2);

						// If user entered x, exit out of loop
						if (whichCategory == 'x') {
							break;
						} // end if

						// Depending on which category the user selected, edit that category using the
						// setter for that category and call the
						// takeInput() method to get the changed value for that category
						if (whichCategory == 'a') {
							contacts[index].setFirstName(takeInput("\nEnter changed first name: ", false));
						} else if (whichCategory == 'b') {
							contacts[index].setLastName(takeInput("\nEnter changed last name: ", false));
						} else if (whichCategory == 'c') {
							contacts[index].setWorkPhone(takeInput("\nEnter changed work phone number: ", false));
						} else if (whichCategory == 'd') {
							contacts[index].setCellNum(takeInput("\nEnter changed cell number: ", false));
						} else if (whichCategory == 'e') {
							contacts[index].setStreetAddress(takeInput("\nEnter changed street address: ", false));
						} else if (whichCategory == 'f') {
							contacts[index].setCity(takeInput("\nEnter changed city: ", false));
						} else if (whichCategory == 'g') {
							contacts[index].setProvince(takeInput("\nEnter changed province: ", false));
						} else if (whichCategory == 'h') {
							contacts[index].setCountry(takeInput("\nEnter changed country: ", false));
						} else if (whichCategory == 'i') {
							contacts[index].setEmail(takeInput("\nEnter changed email: ", true));
						} else if (whichCategory == 'j') {
							contacts[index].setCompany(takeInput("\nEnter changed company: ", false));
						} else if (whichCategory == 'k') {
							contacts[index].setJobTitle(takeInput("\nEnter changed job title: ", false));
						} else if (whichCategory == 'l') {
							contacts[index].setBirthYear(getValidBirthYear());
						} else if (whichCategory == 'm') {
							contacts[index].setStarred(
									getValidChar("\nDo you want this contact to be starred (y) or not starred (n): "));
						} else {
							contacts[index].setNotes(takeInput("\nEnter changed notes: ", false));
						} // end if

						// Call the pressEnter() method
						pressEnter("\nPlease press enter to continue (contact updated)...");

						// Display the updated contact
						System.out.println("\nThe updated contact is now:");
						System.out.println(contacts[index].toString());

						// Call pressEnter() method before asking for category again
						pressEnter("\nPlease press enter to continue...");

					} // end while
				} // end if
			} // end if
		} // end if
	} // void editContact(void)

	/**
	 * Allows user to add a contact
	 */
	public static void addContact() {

		// Declare scanner
		Scanner s = new Scanner(System.in);

		// Declare variables needed
		char[] validChars = { 'o', 'm' };
		char requiredInfo;
		char allInfo;

		// Declare variables needed to pass in to constructor for minimum information
		String fName, lName, workPhone, cellNum;
		char starred;

		// Display the title and tell user how many contacts they can make
		displaySectionTitle("Add Contact");
		System.out.printf("NOTE: you can make %d more new contact%s.%n", 100 - entriesMade,
				100 - entriesMade != 1 ? "s" : "");

		// If there is still space in the directory
		if (entriesMade < 100) {

			// Ask user if they at least have the required information
			requiredInfo = getValidChar(
					"\nDo you have at least the minimum information (last/ first name + work/ cell number) to create a quick contact (y or n)? ");

			// If they do have the required information
			if (requiredInfo == 'y') {

				// Set updtFile to true
				updtFile = true;

				// Ask user the required information and store their input in the appropriate
				// variables
				starred = getValidChar("\nDo you want to star the contact you are currently creating (y or n): ");
				fName = takeInput("Enter contact's first name: ", false);
				lName = takeInput("Enter contact's last name: ", false);
				workPhone = takeInput("Enter contact's work phone number: ", false);
				cellNum = takeInput("Enter contact's cell number: ", false);

				// Ask user if they have all the information
				allInfo = getValidChar(
						"\nDo you only have these 4 pieces of information (o), or would you like to enter ALL the information (m) (o or m)? ",
						validChars);

				// If user wants to enter all the information
				if (allInfo == 'm') {

					// Declare all variables needed
					String streetAddress, city, province, country, email, company, jobTitle, notes;
					int yearOfBirth;

					// Ask user to enter all fields and store the input in the appropriate variables
					streetAddress = takeInput("\nEnter contact's street address: ", false);
					city = takeInput("Enter the city contact lives in: ", false);
					province = takeInput("Enter the province contact lives in: ", false);
					country = takeInput("Enter the country contact lives in: ", false);
					email = takeInput("Enter contact's email address: ", true);
					company = takeInput("Enter related company/ organization of contact (ex. workplace name): ", false);
					jobTitle = takeInput("Enter contact's job title: ", false);
					notes = takeInput("Enter notes for the contact: ", false);
					yearOfBirth = getValidBirthYear();

					// If user all the info, construct a contact object that requires all the
					// information and store it in the next available spot in
					// the contacts array
					contacts[entriesMade] = new Contact(fName, lName, workPhone, cellNum, streetAddress, city, province,
							country, email, company, jobTitle, yearOfBirth, starred, notes);

				}

				// If user wants to make a quick and short entry, construct a contact object
				// that requires only the 4 pieces of information
				else {
					contacts[entriesMade] = new Contact(fName, lName, workPhone, cellNum, starred);
				} // end if

				// increment entriesMade by 1
				entriesMade++;

				// Call the pressEnter() method
				pressEnter("\nPlease press enter to continue (contact created)...");

			}

			// If user says that they do not have the required information to make a quick
			// entry, tell them they at least need those in order to make
			// a contact. Then call the pressEnter() method
			else {
				System.out.println(
						"\nSorry, you must at least have last/ first name + work/ cell number to create a quick contact.");
				pressEnter("\nPlease press enter to continue...");
			} // end if
		}

		// If user has reached max limit for a 100 contacts, display a message telling
		// them so, and call the pressEnter() method
		else {

			System.out.println(
					"\nSorry, you cannot create a new contact, you have reached the max limit of 100 contacts. You may want to delete some unwanted contacts first.");
			pressEnter("\nPlease press enter to continue...");

		} // end if

	} // void addContact(void)

	/**
	 * Sorts and displays all the contacts in alphabetical order or from Z to A
	 * (only first names are taken into consideration when doing so)
	 * 
	 * @param AtoZ boolean - indicates whether to go A to Z (true) or Z to A (false)
	 */
	public static void sortBasedOnAlphabet(boolean AtoZ) {

		// Declare variables needed
		int usrChoice;
		int count = entriesMade;
		int[] spot = new int[entriesMade];
		int compareStrings;

		// Duplicate the original contacts array using .clone() to avoid having side
		// effects and so that my original contacts array does not get modified
		Contact[] contacts2 = contacts.clone();

		// Use nested for loop to sort the array
		// Sort used: bubble sort
		for (int i = 1; i < entriesMade; i++) {
			for (int j = 0; j < entriesMade - 1; j++) {

				// Store if the current first name is less than, equal to, or greater than the
				// next first name
				compareStrings = contacts2[j].getFirstName().toLowerCase()
						.compareTo(contacts2[j + 1].getFirstName().toLowerCase());

				// If compareStrings is greater than 0 and the user wanted to sort
				// alphabetically, or if compareStrings is less than 0 and user
				// wanted to sort Z to A, then sort the array accordingly
				if (compareStrings > 0 && AtoZ || compareStrings < 0 && AtoZ == false) {

					// swap arr[j+1] and arr[j]
					Contact temp = contacts2[j];
					contacts2[j] = contacts2[j + 1];
					contacts2[j + 1] = temp;
				} // end if
			} // end for
		} // end for

		// Display the table header in which the contacts will be displayed in
		displayContactHeader();

		// Display the sorted array
		for (int j = 0; j < entriesMade; j++) {
			spot[j] = j;
			System.out.println(contacts2[j].displayContact(j + 1));
		} // end for

		// Call the obtainValidSelection() and takeAppropriateAction() method to get
		// user to enter a valid listed contact and then
		// allow them to select and expand it if they choose to do so
		usrChoice = obtainValidSelection("\nEnter the number of the contact you wish to expand (0 to exit): ", spot,
				count);
		takeAppropriateAction(usrChoice, "\nSorry, no contacts found.", contacts2, false);
	} // void sortBasedOnAlphabet(boolean)

	/**
	 * Sorts and displays all contacts based on age (from youngest to oldest, or
	 * vice versa)
	 * 
	 * @param youngTOold boolean which indicates if the user wants to sort youngest
	 *                   to oldest(true) or oldest to youngest (false)
	 */
	public static void sortBasedOnAge(boolean youngTOold) {

		// Declare variables needed
		int usrChoice;
		int count = entriesMade;
		int[] spot = new int[entriesMade];
		int currentAge, nextAge;

		// Duplicate the contacts array
		Contact[] contacts2 = contacts.clone();

		// Use nested for loops (bubble sort) to sort the array
		for (int i = 1; i < entriesMade; i++) {
			for (int j = 0; j < entriesMade - 1; j++) {

				// Initialize currentAge and nextAge
				currentAge = contacts2[j].getBirthYear();
				nextAge = contacts2[j + 1].getBirthYear();

				// Make sure the array gets sorted to how the user would want to sort it
				if (currentAge < nextAge && youngTOold || currentAge > nextAge && youngTOold == false) {

					// swap arr[j+1] and arr[j]
					Contact temp = contacts2[j];
					contacts2[j] = contacts2[j + 1];
					contacts2[j + 1] = temp;
				} // end if
			} // end for
		} // end for

		// Display the contact header for table
		displayContactHeader();

		// display the sorted array
		for (int j = 0; j < entriesMade; j++) {
			spot[j] = j;
			System.out.println(contacts2[j].displayContact(j + 1));
		} // end for

		// Call the obtainValidSelection() and takeAppropriateAction() method to get
		// user to enter a valid listed contact and then
		// allow them to select and expand it if they choose to do so
		usrChoice = obtainValidSelection("\nEnter the number of the contact you wish to expand (0 to exit): ", spot,
				count);
		takeAppropriateAction(usrChoice, "\nSorry, no contacts found.", contacts2, false);

	} // void sortBasedOnAge(boolean)

	/**
	 * Displays contacts which in order of recently added to older contacts
	 */
	public static void displayContactsNewToOld() {

		// Declare variables needed
		int usrChoice;
		int count = entriesMade;
		int[] spot = new int[entriesMade];

		// Display table header
		displayContactHeader();

		// Display the contacts (do not include the ones in trash) from recent to old
		// contacts
		for (int i = entriesMade - 1; i >= 0; i--) {
			spot[entriesMade - i - 1] = i;
			System.out.println(contacts[i].displayContact(entriesMade - i));
		} // end for

		// Call the obtainValidSelection() and takeAppropriateAction() method to get
		// user to enter a valid listed contact and then
		// allow them to select and expand it if they choose to do so
		usrChoice = obtainValidSelection("\nEnter the number of the contact you wish to expand (0 to exit): ", spot,
				count);
		takeAppropriateAction(usrChoice, "\nSorry, no contacts found");

	} // void displayContactsNewToOld(void)

	/**
	 * Combines all the different sort methods, displays the sort menu (which
	 * character represents which sort), and asks user to choose a sort
	 * It then displays all contacts sorted in that way
	 */
	public static void displayContactsWithSorts() {

		// Display the title and a brief description
		displaySectionTitle("View Contacts With Different Sorts");
		System.out.println("Here you can choose from several different sorts to view your all your contacts in.");

		// Display the variables needed
		char[] validChars = { 'a', 'b', 'c', 'd', 'e', 'f' };
		char sort;

		// Display the sorting menu
		System.out.println("\nChoose the type of sort you would like to view your contacts in:");
		System.out.println("a - Sort oldest to newest contacts");
		System.out.println("b - Sort newest to oldest contacts");
		System.out.println("c - Sort from A to Z (alphabetically by first name)");
		System.out.println("d - Sort from Z to A (reverse alphabetically by first name)");
		System.out.println("e - Sort from youngest to oldest (age) contacts");
		System.out.println("f - Sort from oldest to youngest (age) contacts");

		// Prompt user to enter a sort they wish to view their contacts in
		sort = getValidChar("\nChoose your sort: ", validChars);

		// Display total contacts
		System.out.println("\nContacts (" + entriesMade + ")");

		// Display the sort (call the appropriate method()) accordingly to which sort
		// the user selected
		if (sort == 'a') {
			displayOldToNewMenu();
		} else if (sort == 'b') {
			displayContactsNewToOld();
		} else if (sort == 'c') {
			// pass in true because sort needs to be A to Z
			sortBasedOnAlphabet(true);
		} else if (sort == 'd') {
			// pass in false because sort needs to be Z to A
			sortBasedOnAlphabet(false);
		} else if (sort == 'e') {
			// pass in true because sort needs to be young to old
			sortBasedOnAge(true);
		} else {
			// pass in false because sort needs to be old to young
			sortBasedOnAge(false);
		} // end if

	} // void displayContactsWithSorts(void)

	/**
	 * Opens the file to make necessary changes if the user has made changes. Also
	 * called to create a file if the file doesn't exist
	 */
	public static void updateFile() throws IOException {

		// open file to write
		PrintWriter f = new PrintWriter(new FileWriter("gmi-de-best-contacts.csv"));

		// Write to file all the values of the fields for each of the contact objects in
		// the contacts array and the trash array
		// using the object's class's .toCSV() method
		for (int i = 0; i < entriesMade; i++) {
			f.println(contacts[i].toCSV());
		} // end for

		for (int i = 0; i < trashEntries; i++) {
			f.println(trash[i].toCSV());
		} // end for

		// close the file
		f.close();

	} // void updateFile(void)

	// MAIN

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// Declare variable needed
		int usrChoice;

		// Display the title and opening instructions
		displaySectionTitle("WELCOME TO GMI DE BEST PERSONAL DIRECTORY");
		System.out.println("\nIf at any time you are confused and are in need of help on how to use/ navigate");
		System.out.println("through this program, please read the user manual provided. Please note that this");
		System.out.println("directory can only hold up to a max of 100 contacts, and a max of 50 contacts in");
		System.out.println("trash. If your trash is filled up, delete contacts from trash, and if your main");
		System.out.println("directory is filled up, same thing (delete some contacts if you want to make some");
		System.out.println("space). However, this program guarantees to be the BEST one out there, so ENJOY!");

		// Call the pressEnter() method
		pressEnter("\nPlease press enter to continue (proceed to main menu)...");

		// Call the populateArray() method to populate the arrays
		populateArray();

		// Loop continuously to keep on asking the user for their option
		while (true) {

			// Display the menu
			System.out.println(
					"\n\n________________________________________________________________________________________________________________________________________________________\n");
			System.out.println("\nMAIN MENU:\n");
			System.out.println("1 - Search Your Contacts");
			System.out.println("2 - Add a New Contact");
			System.out.println("3 - Edit a Contact");
			System.out.println("4 - Delete a Contact");
			System.out.println("5 - View All Starred Contacts");
			System.out.println("6 - View All Your Contacts (With Different Sorts)");
			System.out.println("7 - View All Contacts in Trash");
			System.out.println("8 - Save and Exit Directory");

			// Get the user's choice
			usrChoice = getValidVal("\nEnter your menu option choice: ", 1, 8);

			System.out.println(
					"\n\n________________________________________________________________________________________________________________________________________________________");

			// Depending on what the user's choice is, call the method accordingly
			if (usrChoice == 1) {
				searchContactMenuOption();
			} else if (usrChoice == 2) {
				addContact();
			} else if (usrChoice == 3) {
				editContact();
			} else if (usrChoice == 4) {
				deleteContact();
			} else if (usrChoice == 5) {
				displayStarredMenuOption();
			} else if (usrChoice == 6) {
				displayContactsWithSorts();
			} else if (usrChoice == 7) {
				displayTrash();
			} else {
				break;
			} // end if

		} // end while

		// If the user has made changes (updtFile equals true)
		if (updtFile == true) {

			// Ask user if they would like to save their changes, if so, call the
			// updateFile() method and display a message that tells the
			// user that it has been saved
			if (getValidChar(
					"\nYou have made some change(s) to your directory. Would you like to save those changes (y or n)? ") == 'y') {
				updateFile();
				System.out.println("\nDirectory saved! Thanks for using the program!");
			}

			// If user would not like to save, then display a message that tells user that
			// the directory was not saved
			else {
				System.out.println("\nDirectory not saved...thanks for using the program!");
			} // end if
		} else {
			System.out.println("\nThanks for using the program!");
		} // end if

		// Display ending comment
		System.out.println("Designed by Murtaza Shah");

	} // end main
} // end class (PersonalDirectory)
