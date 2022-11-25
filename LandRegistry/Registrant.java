package landRegistry;

/**
 * 
 * @author Vlad Evans
 * @version 1
 * 
 */

public class Registrant {

	private static int REGNUM_START = 1000;
	private static int currentRegNum = REGNUM_START;
	private int REGNUM;
	private String firstName;
	private String lastName;
	
	public static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public Registrant() {
		this("unknown unknown");
	}
	/**
	 * 
	 * @param firstlastName
	 */
	public Registrant(String firstlastName) {
		firstlastName.split(" ");
		String[] cStr = firstlastName.split(" ");
		setFirstName(cStr[0]);
		setLastName(cStr[1]);
		REGNUM = currentRegNum;
		incrToNextRegNum();
	}
	/**
	 * 
	 * @returns
	 */
	public int getRegNum() {
		return REGNUM;
	}
	/**
	 * 
	 */
	private static void incrToNextRegNum() {
		currentRegNum++;
	}
	/**
	 * 
	 * @returns
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * 
	 * @returns
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * 
	 * @param obj
	 * @returns
	 */
	public boolean equals(Object obj) {
		if (obj.equals(this))
			return true;
		else
			return false;
	}
	/**
	 * 
	 * @returns
	 */
	public String toString() {
		return "Name: " + getFirstName() + " " + getLastName() + "\nRegistration Number: #" + getRegNum() + "\n";
	}
}
