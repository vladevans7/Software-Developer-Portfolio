package landRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 
 * @author Vlad Evans
 * @version 1
 * 
 */

public class RegControl {
	
	private ArrayList<Registrant> registrants = new ArrayList<>();
	private ArrayList<Property> properties = new ArrayList<>();
	/**
	 * 
	 */
	public RegControl() {
	};
	/**
	 * 
	 * @param reg
	 * @returns
	 */
	public Registrant addNewRegistrant(Registrant reg) {
		getRegistrants().add(reg);
		return reg;
	}
	/**
	 * 
	 * @param regNum
	 * @returns
	 */
	public Registrant findRegistrant(int regNum) {
		for (Registrant reg: getRegistrants()) {
			if (reg.getRegNum() == regNum) {
				return reg;
			}
		}
		return null;
	}
	/**
	 * 
	 * @returns
	 */
	public ArrayList<Registrant> listOfRegistrants() {
		return getRegistrants();
	}
	/**
	 * 
	 * @param regNum
	 * @returns
	 */
	public Registrant deleteRegistrant(int regNum) {
		Registrant currentRegistrant = null;
		for (int i = 0; i < getRegistrants().size(); i++) {
		currentRegistrant = getRegistrants().get(i);
		if (currentRegistrant.getRegNum() == regNum) {
			getRegistrants().remove(i);
			}
		}
		return currentRegistrant;
	}
	/**
	 * 
	 * @param prop
	 * @returns
	 */
	public Property addNewProperty(Property prop) {              
		Property overlap = propertyOverlaps(prop);
		if (overlap != null) {
			return propertyOverlaps(prop);
		} else {
			getProperties().add(getProperties().size(), prop);
			return prop;
		}
	}
	/**
	 * 
	 * @param property
	 * @returns
	 */
	public boolean deleteProperties(ArrayList<Property> property) { 
		for (int i = 0; i < property.size(); i++){
			property.get(i);
			for	(int j = 0; j < getProperties().size(); j++) {
				if (getProperties().get(j) == property.get(i))
					getProperties().remove(j);
			}
		}
		return true;
	}
	/**
	 * 
	 * @param originalProperty
	 * @param newRegNum
	 * @returns
	 */
	public Property changePropertyRegistrant1(Property originalProperty, int newRegNum) {
		return (new Property(originalProperty, newRegNum));
	}
	/**
	 * 
	 * @param oldRegNumPropertyArrayList
	 * @param newRegNum
	 * @returns
	 */
	public ArrayList<Property> changePropertyRegistrant(ArrayList<Property> oldRegNumPropertyArrayList, int newRegNum){
		ArrayList<Property> newRegNumPropertyArrayList = new ArrayList<>();;
		
		for (int i = 0; i < oldRegNumPropertyArrayList.size(); i++) {
				newRegNumPropertyArrayList.add(i, new Property(oldRegNumPropertyArrayList.get(i), newRegNum));
				listOfAllProperties().add(new Property(oldRegNumPropertyArrayList.get(i), newRegNum));
				listOfAllProperties().remove(i);
			}
			
		return newRegNumPropertyArrayList;
		
	}
	/**
	 * 
	 * @param regNum
	 * @returns
	 */
	public ArrayList<Property> listOfProperties(int regNum) {
		ArrayList<Property> properties;
		properties = getProperties();
		int propsWithRegNum = 0;
		ArrayList<Property> propertiesForReturn = new ArrayList<>();
		if (regNum != 0) {
		for (Property prop: properties) {
			if (prop.getRegNum() == regNum)
				propertiesForReturn.add(prop);
			}

		return propertiesForReturn;
		}
		else {
			return listOfAllProperties();
		}
	}
	
	/**
	 * 
	 * @returns
	 */
	public ArrayList<Property> listOfAllProperties() {
		return getProperties();
	}
	/**
	 * 
	 * @param prop
	 * @returns
	 */
	private Property propertyOverlaps(Property prop) {
		ArrayList<Property> properties = getProperties();
		int lastPropertyindex = properties.size();
		for (int i = 0; i < lastPropertyindex; i++) {
			if (prop.overlaps(getProperties().get(i))) {
				return getProperties().get(i);
			}
		}
		return null;
	}
	/**
	 * 
	 * @param source
	 * @param fileName
	 * @returns
	 */
	public <T> boolean saveToFile(ArrayList<T> source, String fileName) {
		try {
			FileOutputStream outputStream = new FileOutputStream(fileName);
			ObjectOutputStream stream = new ObjectOutputStream(outputStream);
			if (source.stream().allMatch(n -> n instanceof Registrant)) {
				ArrayList<Registrant> regList = (ArrayList<Registrant>) source;
				stream.writeObject(regList);
			} else {
				ArrayList<Property> propList = (ArrayList<Property>) source;
				stream.writeObject(propList);
			}
			stream.flush();
			stream.close();
			outputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 
	 * @param fileName
	 * @returns
	 */
	public <T> ArrayList<T> loadFromFile(String fileName){
		try {
			FileInputStream inputStream = new FileInputStream(fileName);
			ObjectInputStream readStream = new ObjectInputStream(inputStream);
			ArrayList<T> list = (ArrayList<T>) readStream.readObject();
			readStream.close();
			inputStream.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @returns
	 */
	private ArrayList<Registrant> getRegistrants() {
		return registrants;
	}
	/**
	 * 
	 * @returns
	 */
	private ArrayList<Property> getProperties() {
		return properties;
	}
	/**
	 * 
	 */
	public void refreshProperties() {
		
	}
	/**
	 * 
	 */
	public void refreshRegistrants() {
		
	}
}
