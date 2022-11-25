package landRegistry;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * 
 * @author Vlad Evans
 * @version 1
 * 
 */

public class RegView {

	private static Scanner scan;
	private static RegControl rc;
	
	public static String PROPERTIES_FILE = "LandRegistry.prop";
	public static String REGISTRANTS_FILE = "LandRegistry.reg";

	private final static int ADD_NEW_REGISTRANT = 1;
	private final static int FIND_REGISTRANT = 2;
	private final static int LIST_REGISTRANTS = 3;
	private final static int DELETE_REGISTRANT = 4; 
	private final static int ADD_NEW_PROPERTY = 5;
	private final static int DELETE_PROPERTY = 6; 
	private final static int CHANGE_PROPERTY_REGISTRANT = 7;
	private final static int LIST_PROPERTY_BY_REGUM = 8;
	private final static int LIST_ALL_PROPERTIES = 9;
	private final static int LOAD_LAND_REGISTRY_FROM_BACKUP = 10; 
	private final static int SAVE_LAND_REGISTRY_TO_BACKUP = 11; 
	private final static int EXIT = 0;
	
	/**
	 * 
	 * 
	 * @returns
	 */
	private RegControl getRegControl() {
		return rc;
	}
	/**
	 * 
	 * 
	 */
	public RegView() {
		scan = new Scanner(System.in);
		rc = new RegControl();
	}
	/**
	 * 
	 * 
	 */
	public static void launch() {
		int theChoice;
		do {
			theChoice = displayMenu();
			if (theChoice != EXIT)
				executeMenuItem(theChoice);
		} while (theChoice != EXIT);
		System.out.print("Exiting program");
	}
	/**
	 * 
	 * 
	 * @returns
	 */
	private static int displayMenu() {
		System.out.println("Enter a selection from the following menu:\r\n" + "1. Enter a new registrant\r\n"
				+ "2. Find registrant by registration number\r\n" + "3. Display list of registrants\r\n"
				+ "4. Delete Registrant\r\n" + "5. Register a new property\r\n"
				+ "6. Delete Property\r\n"
				+ "7. Change a property's registrant\r\n"
				+ "8. Display all properties with the same registration number\r\n"
				+ "9. Display all registered properties\r\n"
				+ "10. Load land registry from backup file\r\n"
				+ "11. Save land registry to backup file\r\n"
				+ "0. Exit program");
		int button = -1;
		while (button != EXIT) {
			switch (button = scan.nextInt()) {
			case ADD_NEW_REGISTRANT:
				return ADD_NEW_REGISTRANT;
			case FIND_REGISTRANT:
				return FIND_REGISTRANT;
			case LIST_REGISTRANTS:
				return LIST_REGISTRANTS;
			case DELETE_REGISTRANT:
				return DELETE_REGISTRANT;
			case ADD_NEW_PROPERTY:
				return ADD_NEW_PROPERTY;
			case DELETE_PROPERTY:
				return DELETE_PROPERTY;
			case CHANGE_PROPERTY_REGISTRANT:
				return CHANGE_PROPERTY_REGISTRANT;
			case LIST_PROPERTY_BY_REGUM:
				return LIST_PROPERTY_BY_REGUM;
			case LIST_ALL_PROPERTIES:
				return LIST_ALL_PROPERTIES;
			case LOAD_LAND_REGISTRY_FROM_BACKUP:
				return LOAD_LAND_REGISTRY_FROM_BACKUP;
			case SAVE_LAND_REGISTRY_TO_BACKUP:
				return SAVE_LAND_REGISTRY_TO_BACKUP;
			}
		}
		return EXIT;
	}
	/**
	 * 
	 * @param choice
	 * 
	 */
	private static void executeMenuItem(int choice) {

		switch (choice) {
		case ADD_NEW_REGISTRANT:
			viewAddNewRegistrant();
			break;
		case FIND_REGISTRANT:
			viewFindRegistrant();
			break;
		case LIST_REGISTRANTS:
			viewListOfRegistants();
			break;
		case DELETE_REGISTRANT:
			viewDeleteRegistrant();
			break;
		case ADD_NEW_PROPERTY:
			viewAddNewProperty();
			break;
		case DELETE_PROPERTY:
			viewDeleteProperty();
			break;
		case CHANGE_PROPERTY_REGISTRANT:
			viewChangePropertyRegistrant();
			break;
		case LIST_PROPERTY_BY_REGUM:
			viewListPropertyByRegNum();
			break;
		case LIST_ALL_PROPERTIES:
			viewListAllProperties();
			break;
		case LOAD_LAND_REGISTRY_FROM_BACKUP:
			viewLoadLandRegistryFromBackUp();
			break;
		case SAVE_LAND_REGISTRY_TO_BACKUP:
			viewSaveLandRegistryToBackUp();
			break;
		}
	}
	
	/**
	 * 
	 * @param s
	 * @returns
	 * @throws BadLandRegistryException
	 */
	private static String getResponseTo(String s) {
		
		String string;
		while (true) {
			try {
				System.out.print(s);
				string = scan.nextLine();
				if (string.length() == 0)
					throw new BadLandRegistryException("Missing an input value", "Missing value");
				else if (string == null)
					throw new BadLandRegistryException("An attempt was made to pass null value to a variable.", "Null value entered");
				break;
			} catch (BadLandRegistryException ex) {
				System.out.println(ex.getHeader() + ": " + ex.getMessage());
			}
		}
		return string;
		
		
	}
	/**
	 * 
	 * @returns
	 * @throws BadLandRegistryException
	 */
	private static int requestRegNum() {
		String number;
		int numberForReturn = 0;
		System.out.print("Enter registration number: ");
		String buffer = scan.nextLine();
		number = scan.nextLine();
		
		try {
			Integer.decode(number);
	    } catch (BadLandRegistryException ex) {
	    	throw new BadLandRegistryException("Missing an input value", "Missing value");
	    }
		return numberForReturn;
	}
	/**
	 * 
	 * @returns
	 */
	private static Registrant makeNewRegistrantFromUserInput() {
		scan.nextLine();
		String firstLastName;
		firstLastName = getResponseTo("Enter registant's first and Last name: ");
		return new Registrant(firstLastName);
	}
	/**
	 * 
	 * @returns
	 */
	private static Property makeNewPropertyFromUserInput() {

		int registrationNumber;
		registrationNumber = requestRegNum();
		scan.nextLine();
		String coordinates = getResponseTo("Enter top and left coordinates of property (as X, Y): ");
		String dimension = getResponseTo("Enter length and width of property (as length, width): ");
		return new Property(Integer.parseInt((dimension.split(",")[0]).trim()),
				Integer.parseInt((dimension.split(",")[1]).trim()),
				Integer.parseInt((coordinates.split(",")[0]).trim()),
				Integer.parseInt((coordinates.split(",")[0]).trim()), registrationNumber);
	}
	/**
	 * 
	 */
	public static void viewAddNewRegistrant() {
		Registrant registrant;
		registrant = makeNewRegistrantFromUserInput();
		if (rc.addNewRegistrant(registrant) != null) {
			System.out.println("Registrant added:");
			System.out.println(registrant.toString());
		} else {
			System.out.println("Could not add new registrant; registrant array is full.");
		}
	}
	/**
	 * 
	 */
	public static void viewFindRegistrant() {
		int registrantNumber = 0;
		registrantNumber = requestRegNum();
		Registrant registrant = rc.findRegistrant(registrantNumber);
		if (registrant == null) {
			System.out.println("No registrant with #" + registrantNumber + " registration number");
		} else {
			System.out.println(registrant.toString());
		}
	}
	/**
	 * 
	 */
	public static void viewListOfRegistants() {
		ArrayList<Registrant> registrants = rc.listOfRegistrants();
		if (registrants.size() == 0) {
			System.out.println("No Registrants loaded yet.\n");
		} else {
			System.out.println("List of registrants: \n");
			for (int i = 0; i < registrants.size(); i++) {
				System.out.println(registrants.get(i).toString());
			}
		}
	}
	
	/**
	 * 
	 */
	public static void viewDeleteRegistrant() { 
		System.out.println("Enter registration number of registrant to delete: ");
		int number = scan.nextInt();
		scan.nextLine();
		System.out.println("You are about to delete a registrant number and all the its\r\n" + 
				"associated properties; do you wish to continue? (Enter â€?Yâ€™ to\r\n" + 
				"proceed.): ");
		char y = scan.next().charAt(0);
		if (y == 'Y' || y == 'y') {
			rc.deleteRegistrant(number);
			ArrayList<Property> properties = rc.listOfProperties(number);
			if (properties.size() != 0)
			rc.deleteProperties(properties);
			System.out.println("Registrant and related properties deleted; ");
		}
	}
	/**
	 * 
	 */
	public static void viewAddNewProperty() {
		Property newProperty;
		ArrayList<Property> properties = rc.listOfAllProperties();
		if (properties.size() < 3) {
			newProperty = makeNewPropertyFromUserInput();
			Property property = rc.addNewProperty(newProperty);
			
			
			if (property != newProperty) {
				System.out.println("\nNew property could not be registered;\r\n" + 
						"There is already a property registered at:");
				System.out.println(property.toString());
			}
			else {
				System.out.println("\nNew property has been registered as:");
				System.out.println(property.toString());
			}
		} else
			System.out.println("The array of properties is full!");
	}
	/**
	 * 
	 */
	public static void viewDeleteProperty() { 
		System.out.println("Enter registration number of property to delete: ");
		ArrayList<Property> properties = rc.listOfProperties(scan.nextInt());
		if (properties.size() == 0)
			System.out.println("No properties are associated with that registration number.\n");
		else {
			rc.deleteProperties(properties);
		}
	}
	/**
	 * 
	 */
	public static void viewChangePropertyRegistrant() {
		ArrayList<Property> properties;
		properties = rc.listOfAllProperties();
		if (properties.size() == 0)
			System.out.println("Property Registry empty; can't change the registrants number!");
		else {
			int originalRegNum;
			scan.nextLine();
			originalRegNum = Integer.parseInt(getResponseTo("Enter original registrants number: "));
			int newNum;
			newNum = Integer.parseInt(getResponseTo("Enter new registrants number: "));
			properties = rc.listOfProperties(originalRegNum);
			rc.changePropertyRegistrant(properties, newNum);
			System.out.println("Operation completed; the new registration number, " + newNum + ", has\n replaced "
					+ originalRegNum + " in all affected properties.\n");
		}
	}
	/**
	 * 
	 */
	public static void viewListPropertyByRegNum() {
		ArrayList<Property> properties = rc.listOfAllProperties();
		ArrayList<Property> propertiesForShow = rc.listOfAllProperties();
		if (properties.size() == 0)
			System.out.println("Property Registry empty; no properties to display");
		else {
			propertiesForShow = rc.listOfProperties(requestRegNum());
			if (propertiesForShow.isEmpty()) {
				System.out.println("\nNo properties to display, with such registration number\n");
			}
			else {
				for (int i = 0; i < propertiesForShow.size(); i++) {
					System.out.println(propertiesForShow.get(i).toString());
				}
			}
		}
	}
	/**
	 * 
	 */
	public static void viewListAllProperties() {
		ArrayList<Property> properties;
		properties = rc.listOfAllProperties();
		if (properties.size() == 0) {
			System.out.println("Property Registry empty; no properties to display\n");
		} else {
			for (int i = 0; i < properties.size(); i++) {
				System.out.println(properties.get(i).toString() + "\n");
			}
		}
	}
	/**
	 * 
	 */
	public static void viewLoadLandRegistryFromBackUp() {
		System.out.println("You are about to overwrite existing records; do you wish to continue?");
		scan.nextLine();
		System.out.print("(Enter 'Y' or 'y' to proceed.): ");
		String response = scan.nextLine();
			if(response.equals("Y") || response.equals("y")) {
				ArrayList<Registrant> regList = rc.loadFromFile(REGISTRANTS_FILE);
				ArrayList<Property> propList = rc.loadFromFile(PROPERTIES_FILE);
				for (Registrant reg : rc.listOfRegistrants())
					rc.deleteRegistrant(reg.getRegNum());
				for (Registrant reg : regList)
					rc.addNewRegistrant(reg);
				for (Property prop : propList)
					rc.addNewProperty(prop);
				System.out.println("Land Registry has been loaded from backup file");
			} else
				System.out.println("Properties are not deleted");
	}
	/**
	 * 
	 */
	public static void viewSaveLandRegistryToBackUp() {
		rc.saveToFile(rc.listOfRegistrants(), REGISTRANTS_FILE);
		rc.saveToFile(rc.listOfAllProperties(), PROPERTIES_FILE);
		System.out.println("Land Registry has been backed up to file");
	}
	/**
	 * 
	 * @param displayList
	 * @returns
	 */
	private static <T> String toString(ArrayList<T> displayList){
		
		return toString(displayList);
	}
}
