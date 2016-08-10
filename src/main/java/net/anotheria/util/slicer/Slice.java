package net.anotheria.util.slicer;

import java.util.List;
/**
 * A slice is a piece of data which correspond to a page/frame in a larger data package. It's actually a piece of cake :-)
 *
 * @author lrosenberg
 * @param <T> the type parameter for this slice.
 * @version $Id: $Id
 */
public class Slice<T> {
	/**
	 * The requested segment.
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
	 *
	 * @param aSegment the requested segment.
	 */
	public Slice(Segment aSegment){
		segment = aSegment;
	}
	
	/**
	 * Returns the segment this slice has been initialized with.
	 *
	 * @return a {@link net.anotheria.util.slicer.Segment} object.
	 */
	public Segment getSegment() {
		return segment;
	}
	
	
	void setSegment(Segment aSegment) {
		segment = aSegment;
	}
	
	/**
	 * Returns the total number of items.
	 *
	 * @return a int.
	 */
	public int getTotalNumberOfItems() {
		return totalNumberOfItems;
	}
	/**
	 * <p>Setter for the field <code>totalNumberOfItems</code>.</p>
	 *
	 * @param aTotalNumberOfItems a int.
	 */
	public void setTotalNumberOfItems(int aTotalNumberOfItems) {
		totalNumberOfItems = aTotalNumberOfItems;
	}
	
	/**
	 * Returns the total number of slices.
	 *
	 * @return a int.
	 */
	public int getTotalNumberOfSlices() {
		return totalNumberOfSlices;
	}
	/**
	 * <p>Setter for the field <code>totalNumberOfSlices</code>.</p>
	 *
	 * @param aTotalNumberOfSlices a int.
	 */
	public void setTotalNumberOfSlices(int aTotalNumberOfSlices) {
		totalNumberOfSlices = aTotalNumberOfSlices;
	}
	
	/**
	 * Returns the number of the current slice.
	 *
	 * @return a int.
	 */
	public int getCurrentSlice(){
		return segment.getSliceNumber();
	}
	
	/**
	 * <p>getElementsPerSlice.</p>
	 *
	 * @return a int.
	 */
	public int getElementsPerSlice(){
		return segment.getElementsPerSlice();
	}

	/**
	 * Returns the data contained in this slice.
	 *
	 * @return a list with data items for this slice.
	 */
	public List<T> getSliceData() {
		return sliceData;
	}

	/**
	 * <p>Setter for the field <code>sliceData</code>.</p>
	 *
	 * @param someSliceData a {@link java.util.List} object.
	 */
	public void setSliceData(List<T> someSliceData) {
		sliceData = someSliceData;
	}
	
	/** {@inheritDoc} */
	@Override public String toString(){
		return "TOT I: "+totalNumberOfItems+", TOT P: "+totalNumberOfSlices+", SEG: {"+segment+"}, contents: "+sliceData;
	}
	
	/**
	 * Returns true if there is a next slice.
	 *
	 * @return true if this slice is not the last one
	 */
	public boolean hasNextSlice(){
        return getCurrentSlice() < totalNumberOfSlices;
	}
	
	/**
	 * Returns true if there is a previous pageable slice (i.e. this slice number >1)
	 *
	 * @return true if this slice is not the first
	 */
	public boolean hasPrevSlice(){
		return getCurrentSlice()>1;
	}
	
	/**
	 * Returns if this slice is the first slice.
	 *
	 * @return true if this slice is first
	 */
	public boolean isFirstSlice(){
		return getCurrentSlice() == 1;
	}
	
	/**
	 * Returns true if this slice is the last slice.
	 *
	 * @return true if this slice is the last one
	 */
	public boolean isLastSlice(){
		return getCurrentSlice() == totalNumberOfSlices;
	}
}
