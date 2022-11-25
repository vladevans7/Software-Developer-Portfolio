package landRegistry;

/**
 * 
 * @author Vlad Evans
 * @version 1
 * 
 */

public class BadLandRegistryException extends java.lang.RuntimeException {
	
	
	private static final long serialVersionUID = 1L;
	private String header;
	/**
	 * 
	 */
	public BadLandRegistryException() {
		this("Please try again", "Bad land registry data entered");
	}
	/**
	 * 
	 * @param message we get message as parameter
	 * @param header we get header as parameter
	 */
	public BadLandRegistryException(String message, String header) {
		super(message);
		setHeader(header);
	}
	/**
	 * 
	 * @return we return header
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * 
	 * @param header we get header as parameter
	 */
	public void setHeader(String header) {
		this.header = header;
	}
}
