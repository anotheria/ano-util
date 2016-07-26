package net.anotheria.util.slicer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class SlicerTest {
	@Test
	public void test(){
		List<Integer> data = new ArrayList<Integer>(20);
		for (int i=0; i<22; i++)
			data.add(i+1);
		
		Segment segment = new Segment();
		segment.setElementsPerSlice(4);
		segment.setSliceNumber(6);
		
		Slice<Integer> last = Slicer.slice(segment, data);
		assertEquals(2, last.getSliceData().size());
		assertFalse(last.isFirstSlice());
		assertTrue(last.isLastSlice());
		assertFalse(last.hasNextSlice());
		assertTrue(last.hasPrevSlice());

		
		Slice<Integer> first = Slicer.slice(new Segment(1, 5), data);
		assertEquals(5, first.getSliceData().size());
		assertEquals(1, first.getSliceData().get(0).intValue());
		assertEquals(5, first.getTotalNumberOfSlices());
		assertEquals(22, first.getTotalNumberOfItems());
		assertEquals(1, first.getCurrentSlice());
		assertEquals(5, first.getElementsPerSlice());
		assertTrue(first.isFirstSlice());
		assertFalse(first.isLastSlice());
		assertTrue(first.hasNextSlice());
		assertFalse(first.hasPrevSlice());
		
		Slice<Integer> second = Slicer.slice(new Segment(2, 5), data);
		assertEquals(5, second.getSliceData().size());
		assertEquals(6, second.getSliceData().get(0).intValue());
		assertEquals(5, second.getTotalNumberOfSlices());
		assertEquals(22, second.getTotalNumberOfItems());
		assertEquals(2, second.getCurrentSlice());
		assertEquals(5, second.getElementsPerSlice());
		assertFalse(second.isFirstSlice());
		assertFalse(second.isLastSlice());
		assertTrue(second.hasNextSlice());
		assertTrue(second.hasPrevSlice());
		
		assertNotNull(second.toString());
		assertNotNull(first.toString());
		assertNotNull(last.toString());
	}
	
	@Test public void testOverflow(){
		List<Integer> data = new ArrayList<Integer>(5);
		for (int i=0; i<5; i++)
			data.add(i+1);
		Slice<Integer> second = Slicer.slice(new Segment(2, 5), data);
		assertEquals(0, second.getSliceData().size());
		
		try{
			Slicer.slice(new Segment(-1, 5), data);
			fail("Negative page numbers must throw errors");
		}catch(AssertionError error){}
		
	}
}
