package landRegistry;

/**
 * 
 * @author Vlad Evans
 * @version 1
 * 
 */

public class Property {
	private static double TAX_RATE_PER_M2 = 12.50;
	private static int DEFAULT_REGNUM = 999;
	private int xLeft;
	private int yTop;
	private int xLength;
	private int yWidth;
	private int regNum;
	private int area;
	private double taxes;
	
	public static final long serialVersionUID = 1L; 
	/**
	 * 
	 * 
	 */
	public Property() {
		this(0, 0, 0, 0);
	}
	/**
	 * 
	 * @param Xlength
	 * @param yWidth
	 * @param xLeft
	 * @param yTop
	 */
	public Property(int Xlength, int yWidth, int xLeft, int yTop) {
		this(Xlength, yWidth, xLeft, yTop, 999);
	}
	/**
	 * 
	 * @param prop
	 * @param regNum
	 */
	public Property(Property prop, int regNum) {
		this(prop.xLength, prop.yWidth, prop.xLeft, prop.yTop, regNum);
	}
	/**
	 * 
	 * @param XLength
	 * @param yWidth
	 * @param xLeft
	 * @param yTop
	 * @param regNum
	 */
	public Property(int XLength, int yWidth, int xLeft, int yTop, int regNum) {
		setXLength(XLength);
		setYWidth(yWidth);
		setXLeft(xLeft);
		setYTop(yTop);
		setRegNum(regNum);
	}
	/**
	 * 
	 * @returns
	 */
	public int getXLeft() {
		return xLeft;
	}
	/**
	 * 
	 * @param left
	 */
	public void setXLeft(int left) {
		this.xLeft = left;
	}
	/**
	 * 
	 * @returns
	 */
	public int getXRight() {
		return getXLeft() + getXLength();
	}
	/**
	 * 
	 * @returns
	 */
	public int getYTop() {
		return yTop;
	}
	/**
	 * 
	 * @param top
	 */
	public void setYTop(int top) {
		this.yTop = top;
	}
	/**
	 * 
	 * @returns
	 */
	public int getYBottom() {
		return getYTop() + getYWidth();
	}
	/**
	 * 
	 * @returns
	 */
	public int getYWidth() {
		return yWidth;
	}
	/**
	 * 
	 * @param width
	 */
	public void setYWidth(int width) {
		this.yWidth = width;
	}
	/**
	 * 
	 * @returns
	 */
	public int getXLength() {
		return xLength;
	}
	/**
	 * 
	 * @param length
	 */
	public void setXLength(int length) {
		this.xLength = length;
	}
	/**
	 * 
	 * @returns
	 */
	public int getRegNum() {
		return regNum;
	}
	/**
	 * 
	 * @param regNum
	 */
	private void setRegNum(int regNum) {
		this.regNum = regNum;
	}
	/**
	 * 
	 * @returns
	 */
	public int getArea() {
		return getXLength() * getYWidth();
	}
	/**
	 * 
	 * @returns
	 */
	public double getTaxes() {
		return getXLength() * getYWidth() * TAX_RATE_PER_M2;
	}
	/**
	 * 
	 * @returns
	 */
	public String toString() {
		return "Coordinates: " + getXLeft() + ", " + getYTop() + "\nLength: " + getXLength() + " m Width: "
				+ getYWidth() + " m\n" + "Registrant: #" + getRegNum() + "\n" + "Area: " + getArea() + " m2\n"
				+ "Property Taxes: $" + getTaxes() + "\n";
	}
	/**
	 * 
	 * @param prop
	 * @returns
	 */
	public boolean hasSameSides(Property prop) {
		if (getXLength() == prop.getXLength() && getYWidth() == prop.getYWidth())
			return true;
		else
			return false;
	}
	/**
	 * 
	 * @param prop
	 * @returns
	 */
	public boolean overlaps(Property prop) {
		if (((((getYTop() <= prop.getYTop()) && (prop.getYTop() <= getYTop() + getYWidth()))
				|| ((prop.getYTop() <= getYTop()) && (getYTop() <= prop.getYTop() + prop.getYWidth())))
				&& (((getXLeft() <= prop.getXLeft()) && (prop.getXLeft() <= (getXLeft() + getXLength())))
						|| ((prop.getXLeft() <= getXLeft()) && (getXLeft() <= prop.getXLeft() + prop.getXLength())))))
			return true;
		else
			return false;
	}
}
