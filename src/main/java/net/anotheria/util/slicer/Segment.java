package net.anotheria.util.slicer;

import java.io.Serializable;

/**
 * Used as parameter class in a slice request.
 *
 * @author lrosenberg
 * @version $Id: $Id
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
	 *
	 * @param aSliceNumber a int.
	 * @param aElementsPerSlice a int.
	 */
	public Segment(int aSliceNumber, int aElementsPerSlice){
		sliceNumber = aSliceNumber;
		elementsPerSlice = aElementsPerSlice;
	}
	
	/**
	 * <p>Constructor for Segment.</p>
	 */
	public Segment(){
		
	}
	

	/**
	 * <p>Getter for the field <code>elementsPerSlice</code>.</p>
	 *
	 * @return a int.
	 */
	public int getElementsPerSlice() {
		return elementsPerSlice;
	}

	/**
	 * <p>Setter for the field <code>elementsPerSlice</code>.</p>
	 *
	 * @param aElementsPerSlice a int.
	 */
	public void setElementsPerSlice(int aElementsPerSlice) {
		this.elementsPerSlice = aElementsPerSlice;
	}

	/**
	 * <p>Getter for the field <code>sliceNumber</code>.</p>
	 *
	 * @return a int.
	 */
	public int getSliceNumber() {
		return sliceNumber;
	}

	/**
	 * <p>Setter for the field <code>sliceNumber</code>.</p>
	 *
	 * @param aSliceNumber a int.
	 */
	public void setSliceNumber(int aSliceNumber) {
		this.sliceNumber = aSliceNumber;
	}
	
	/** {@inheritDoc} */
	@Override public String toString(){
        return "SliceNumber: "+ sliceNumber +", ElementsPerSlice: "+ elementsPerSlice;
	}

}
