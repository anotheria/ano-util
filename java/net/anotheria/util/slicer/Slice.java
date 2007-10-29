package net.anotheria.util.slicer;

import java.util.List;

public class Slice<T> {
	private Segment segment;
	private int totalNumberOfItems;
	private int totalNumberOfSlices;
	private List<T> sliceData;
	
	public Slice(Segment aSegment){
		segment = aSegment;
	}
	
	public Segment getSegment() {
		return segment;
	}
	void setSegment(Segment aSegment) {
		segment = aSegment;
	}
	public int getTotalNumberOfItems() {
		return totalNumberOfItems;
	}
	public void setTotalNumberOfItems(int aTotalNumberOfItems) {
		totalNumberOfItems = aTotalNumberOfItems;
	}
	public int getTotalNumberOfSlices() {
		return totalNumberOfSlices;
	}
	public void setTotalNumberOfSlices(int aTotalNumberOfSlices) {
		totalNumberOfSlices = aTotalNumberOfSlices;
	}
	
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
	
	public String toString(){
		return "TOT I: "+totalNumberOfItems+", TOT P: "+totalNumberOfSlices+", SEG: {"+segment+"}, contents: "+sliceData;
	}
	
	public boolean hasNextSlice(){
		return getCurrentSlice() < getTotalNumberOfSlices();
	}
	
	public boolean hasPrevSlice(){
		return getCurrentSlice()>1;
	}
	
	public boolean isFirstSlice(){
		return getCurrentSlice() == 1;
	}
	
	public boolean isLastPage(){
		return getCurrentSlice() == totalNumberOfSlices;
	}
}
