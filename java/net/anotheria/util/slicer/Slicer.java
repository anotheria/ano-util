package net.anotheria.util.slicer;

import java.util.ArrayList;
import java.util.List;

public class Slicer {
	
	public static final int FIRST_PAGE = 1;
	
	/**
	 * Returns the requested slice of the data if available.
	 * @param request slice request, i.e. number of elements per page and current page number.
	 * @param data the data to slice.
	 * @return
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
			
		List<T> sliced = data.subList(startIndex, endIndex);
		ret.setSliceData(sliced);
		
		return ret;
	}
	
	public static void main(String a[]){
		List<Integer> data = new ArrayList<Integer>(20);
		for (int i=0; i<22; i++)
			data.add(i+1);
		
		Segment segment = new Segment();
		segment.setElementsPerSlice(4);
		segment.setSliceNumber(6);
		
		List<Integer> ret = Slicer.slice(segment, data).getSliceData();
		
		System.out.println(ret);
		
		Slice<Integer> slice = new Slice<Integer>(new Segment(0, 1));
		slice.setTotalNumberOfSlices(1);
		while(slice.hasNextSlice()){
			slice = Slicer.slice(new Segment(slice.getCurrentSlice() + 1, 5), data);
			System.out.print("Slice "+slice.getCurrentSlice()+": ");
			for(int i:slice.getSliceData())
				System.out.print(i + ",");
			System.out.println();
		}
	}
	
}
