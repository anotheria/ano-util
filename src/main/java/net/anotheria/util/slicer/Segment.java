package net.anotheria.util.slicer;

import java.io.Serializable;

/**
 * Used as parameter class in a slice request.
 * @author lrosenberg
 */
public class Segment implements Serializable {
	
	/**
	 * Default serialVersion variable.
	 */
	private static final long serialVersionUID = -1075806075594192415L;

	/**
	 * The requested slice number.
	 */
	private int sliceNumber;
	
	/**
	 * Number of the elements per slice (number of items on a page).
	 */
	private int elementsPerSlice;
	
	/**
	 * Creates a new Segment with given slice number and elements per page.
	 * @param aSliceNumber
	 * @param aElementsPerSlice
	 */
	public Segment(int aSliceNumber, int aElementsPerSlice){
		sliceNumber = aSliceNumber;
		elementsPerSlice = aElementsPerSlice;
	}
	
	public Segment(){
		
	}
	

	public int getElementsPerSlice() {
		return elementsPerSlice;
	}

	public void setElementsPerSlice(int aElementsPerSlice) {
		this.elementsPerSlice = aElementsPerSlice;
	}

	public int getSliceNumber() {
		return sliceNumber;
	}

	public void setSliceNumber(int aSliceNumber) {
		this.sliceNumber = aSliceNumber;
	}
	
	@Override public String toString(){
        return "SliceNumber: "+ sliceNumber +", ElementsPerSlice: "+ elementsPerSlice;
	}

}
