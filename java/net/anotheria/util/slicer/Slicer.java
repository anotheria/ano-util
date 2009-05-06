package net.anotheria.util.slicer;

import java.util.ArrayList;
import java.util.List;
/**
 * Slicer is an utility for cutting large amount of data in slices needed for paging.
 * @author lrosenberg
 */
public class Slicer {

	public static final int FIRST_PAGE = 1;
	
	/**
	 * Returns the requested slice of the data if available.
	 * @param request slice request, i.e. number of elements per page and current page number.
	 * @param data the data to slice.
	 * @return a typed Slice.
	 */
	public static <T> Slice<T> slice(Segment segment, List<T> data){
		int totalElements = data.size();
		
		Slice<T> ret = new Slice<T>(segment);
		ret.setTotalNumberOfItems(totalElements);

		double pageCount = (double)totalElements / segment.getElementsPerSlice();
		int showedPageCount = (int)pageCount;
		if (showedPageCount!=pageCount)
			showedPageCount++;
		ret.setTotalNumberOfSlices(showedPageCount);
		
		int startIndex = segment.getElementsPerSlice()*(segment.getSliceNumber()-1);
		int endIndex = segment.getElementsPerSlice()*(segment.getSliceNumber());
		
		
		if (startIndex<0)
			startIndex = 0;
		if (startIndex>totalElements){
			ret.setSliceData(new ArrayList<T>());
			return ret;
		}
		
		if (endIndex>totalElements)
			endIndex = totalElements;
		
		if (endIndex<startIndex)
			throw new AssertionError("EndIndex greater as StartIndex: "+endIndex+" > "+startIndex);
		
		List<T> sliced = data.subList(startIndex, endIndex);
		ret.setSliceData(sliced);
		
		return ret;
	}
}
