package net.anotheria.util.sorter;

import net.anotheria.util.BasicComparable;

public class TestComparable implements IComparable{
	
	private String name;
	private int intValue;
	private double doubleValue;
	
	public TestComparable(){
		
	}
	
	public TestComparable(String aName, int anIntValue, double aDoubleValue){
		name = aName;
		intValue = anIntValue;
		doubleValue = aDoubleValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	@Override
	public int compareTo(IComparable anotherObject, int method) {
		TestComparable another = (TestComparable) anotherObject;
		switch(method){
		case TestSortType.SORT_BY_INTEGER:
			return BasicComparable.compareInt(intValue, another.intValue);
		case TestSortType.SORT_BY_DOUBLE:
			return BasicComparable.compareDouble(doubleValue, another.doubleValue);
		case TestSortType.SORT_BY_STRING:
			return BasicComparable.compareString(name, another.name);
		}
		throw new IllegalArgumentException("Unsupported method: "+method);
	}
	
	public String toString(){
		return getName()+", "+getIntValue()+", "+getDoubleValue();
	}

}
