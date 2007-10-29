package net.anotheria.util.slicer;

public class Segment {
	/**
	 * The requested slice number.
	 */
	private int sliceNumber;
	
	/**
	 * Number of the elements in a slice.
	 */
	private int elementsPerSlice;
	
	public Segment(int aSliceNumber, int anElementsPerSlice){
		sliceNumber = aSliceNumber;
		elementsPerSlice = anElementsPerSlice;
	}
	
	public Segment(){
		
	}
	

	public int getElementsPerSlice() {
		return elementsPerSlice;
	}

	public void setElementsPerSlice(int elementsPerSlice) {
		this.elementsPerSlice = elementsPerSlice;
	}

	public int getSliceNumber() {
		return sliceNumber;
	}

	public void setSliceNumber(int sliceNumber) {
		this.sliceNumber = sliceNumber;
	}
	
	public String toString(){
		return "SliceNumber: "+getSliceNumber()+", ElementsPerSlice: "+getElementsPerSlice();
	}

}
