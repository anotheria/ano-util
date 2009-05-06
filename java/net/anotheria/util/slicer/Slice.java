package net.anotheria.util.slicer;

import java.util.List;
/**
 * A slice is a piece of data which correspond to a page/frame in a larger data package. It's actually a piece of cake :-)
 * @author lrosenberg
 *
 * @param <T> the type parameter for this slice.
 */
public class Slice<T> {
	/**
	 * The requested segment
	 */
	private Segment segment;
	/**
	 * Total number of items in the data.
	 */
	private int totalNumberOfItems;
	/**
	 * Total number of slices cutable from the data. If you have 25 messages and present 6 messages on a page, you have 5 slices, 4 with 6 elements each and one with 1 element.
	 */
	private int totalNumberOfSlices;
	/**
	 * The data which belongs to this slice.
	 */
	private List<T> sliceData;
	
	/**
	 * Creates a new slice for the given segment.
	 * @param aSegment
	 */
	public Slice(Segment aSegment){
		segment = aSegment;
	}
	
	public Segment getSegment() {
		return segment;
	}
	void setSegment(Segment aSegment) {
		segment = aSegment;
	}
	
	/**
	 * Returns the total number of items.
	 * @return 
	 */
	public int getTotalNumberOfItems() {
		return totalNumberOfItems;
	}
	public void setTotalNumberOfItems(int aTotalNumberOfItems) {
		totalNumberOfItems = aTotalNumberOfItems;
	}
	
	/**
	 * Returns the total number of slices.
	 * @return
	 */
	public int getTotalNumberOfSlices() {
		return totalNumberOfSlices;
	}
	public void setTotalNumberOfSlices(int aTotalNumberOfSlices) {
		totalNumberOfSlices = aTotalNumberOfSlices;
	}
	
	/**
	 * Returns the number of the current slice.
	 * @return
	 */
	public int getCurrentSlice(){
		return segment.getSliceNumber();
	}
	
	public int getElementsPerSlice(){
		return segment.getElementsPerSlice();
	}

	public List<T> getSliceData() {
		return sliceData;
	}

	public void setSliceData(List<T> someSliceData) {
		sliceData = someSliceData;
	}
	
	@Override public String toString(){
		return "TOT I: "+totalNumberOfItems+", TOT P: "+totalNumberOfSlices+", SEG: {"+segment+"}, contents: "+sliceData;
	}
	
	/**
	 * Returns true if there is a next slice.
	 * @return true if this slice is not the last one
	 */
	public boolean hasNextSlice(){
		return getCurrentSlice() < getTotalNumberOfSlices();
	}
	
	/**
	 * Returns true if there is a previous pageable slice (i.e. this slice number >1)
	 * @return true if this slice is not the first
	 */
	public boolean hasPrevSlice(){
		return getCurrentSlice()>1;
	}
	
	/**
	 * Returns if this slice is the first slice.
	 * @return true if this slice is first
	 */
	public boolean isFirstSlice(){
		return getCurrentSlice() == 1;
	}
	
	/**
	 * Returns true if this slice is the last slice.
	 * @return true if this slice is the last one
	 */
	public boolean isLastSlice(){
		return getCurrentSlice() == totalNumberOfSlices;
	}
}
