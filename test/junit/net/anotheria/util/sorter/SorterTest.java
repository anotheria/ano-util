package net.anotheria.util.sorter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import static junit.framework.Assert.*;

public class SorterTest {
	@Test public void quickSort(){
		testSorter(new QuickSorter<TestComparable>());
	}
	
	@Test public void bubbleSort(){
		System.out.println("BubbleSorter not tested");
		//testSorter(new BubbleSorter<TestComparable>());
	}

	@Test public void insertSort(){
		System.out.println("InsertSorter not tested");
		//testSorter(new InsertSorter<TestComparable>());
	}

	private void testSorter(Sorter<TestComparable> sorter){
		List<TestComparable> presorted = createList();
		List<TestComparable> sortResult, sortResult2;
		
		List<TestComparable> reversed = new ArrayList<TestComparable>();
		reversed.addAll(presorted);
		Collections.reverse(reversed);
		
		sortResult = sorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_INTEGER));
		assertEquals(presorted, sortResult);
		assertEquals(presorted.size(), sortResult.size());
		
		sortResult = sorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_STRING));
		assertEquals(presorted, sortResult);
		assertEquals(presorted.size(), sortResult.size());

		sortResult = sorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_DOUBLE));
		assertEquals(presorted, sortResult);
		assertEquals(presorted.size(), sortResult.size());

		try{
			sortResult = sorter.sort(presorted, new TestSortType(TestSortType.UNSUPPORTED_SORT_TYPE));
			fail("Expected exception by unsupported sorttype");
		}catch(IllegalArgumentException e){}
		
		//SORTING
		Collections.shuffle(presorted);
		sortResult2 = sorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_INTEGER));
		compareLists(sortResult, sortResult2);

		Collections.shuffle(presorted);
		sortResult2 = sorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_STRING));
		compareLists(sortResult, sortResult2);

		Collections.shuffle(presorted);
		sortResult2 = sorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_DOUBLE));
		compareLists(sortResult, sortResult2);
		
		///////////////////////////////////////////////////////
		sortResult = sorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_INTEGER, TestSortType.DESC));
		assertEquals(reversed, sortResult);
		assertEquals(reversed.size(), sortResult.size());
		
		sortResult = sorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_STRING, TestSortType.DESC));
		assertEquals(reversed, sortResult);
		assertEquals(reversed.size(), sortResult.size());

		sortResult = sorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_DOUBLE, TestSortType.DESC));
		assertEquals(reversed, sortResult);
		assertEquals(reversed.size(), sortResult.size());

		try{
			sortResult = sorter.sort(reversed, new TestSortType(TestSortType.UNSUPPORTED_SORT_TYPE, TestSortType.DESC));
			fail("Expected exception by unsupported sorttype");
		}catch(IllegalArgumentException e){}
		
		//SORTING
		Collections.shuffle(reversed);
		sortResult2 = sorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_INTEGER, TestSortType.DESC));
		compareLists(sortResult, sortResult2);

		Collections.shuffle(reversed);
		sortResult2 = sorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_STRING, TestSortType.DESC));
		compareLists(sortResult, sortResult2);

		Collections.shuffle(reversed);
		sortResult2 = sorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_DOUBLE, TestSortType.DESC));
		compareLists(sortResult, sortResult2);
		
		
	}
	
	private void compareLists(List<?> list1, List<?> list2){
		assertEquals(list1.size(), list2.size());
		for (int i=0; i<list1.size(); i++)
			assertEquals(list1.get(i), list2.get(i));
	}
	
	private List<TestComparable> createList(){
		List<TestComparable> ret = new ArrayList<TestComparable>();
		
		for (int i=0; i<100; i++)
			ret.add(new TestComparable("name-"+i, i, 500.0 - i));
		
		return ret;
	}
	
	@Test public void testStaticQuickSorter(){
		List<TestComparable> presorted = createList();
		List<TestComparable> sortResult, sortResult2;
		
		List<TestComparable> reversed = new ArrayList<TestComparable>();
		reversed.addAll(presorted);
		Collections.reverse(reversed);
		
		sortResult = StaticQuickSorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_INTEGER));
		assertEquals(presorted, sortResult);
		assertEquals(presorted.size(), sortResult.size());
		
		sortResult = StaticQuickSorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_STRING));
		assertEquals(presorted, sortResult);
		assertEquals(presorted.size(), sortResult.size());

		sortResult = StaticQuickSorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_DOUBLE));
		assertEquals(presorted, sortResult);
		assertEquals(presorted.size(), sortResult.size());

		try{
			sortResult = StaticQuickSorter.sort(presorted, new TestSortType(TestSortType.UNSUPPORTED_SORT_TYPE));
			fail("Expected exception by unsupported sorttype");
		}catch(IllegalArgumentException e){}
		
		//SORTING
		Collections.shuffle(presorted);
		sortResult2 = StaticQuickSorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_INTEGER));
		compareLists(sortResult, sortResult2);

		Collections.shuffle(presorted);
		sortResult2 = StaticQuickSorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_STRING));
		compareLists(sortResult, sortResult2);

		Collections.shuffle(presorted);
		sortResult2 = StaticQuickSorter.sort(presorted, new TestSortType(TestSortType.SORT_BY_DOUBLE));
		compareLists(sortResult, sortResult2);
		
		///////////////////////////////////////////////////////
		sortResult = StaticQuickSorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_INTEGER, TestSortType.DESC));
		assertEquals(reversed, sortResult);
		assertEquals(reversed.size(), sortResult.size());
		
		sortResult = StaticQuickSorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_STRING, TestSortType.DESC));
		assertEquals(reversed, sortResult);
		assertEquals(reversed.size(), sortResult.size());

		sortResult = StaticQuickSorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_DOUBLE, TestSortType.DESC));
		assertEquals(reversed, sortResult);
		assertEquals(reversed.size(), sortResult.size());

		try{
			sortResult = StaticQuickSorter.sort(reversed, new TestSortType(TestSortType.UNSUPPORTED_SORT_TYPE, TestSortType.DESC));
			fail("Expected exception by unsupported sorttype");
		}catch(IllegalArgumentException e){}
		
		//SORTING
		Collections.shuffle(reversed);
		sortResult2 = StaticQuickSorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_INTEGER, TestSortType.DESC));
		compareLists(sortResult, sortResult2);

		Collections.shuffle(reversed);
		sortResult2 = StaticQuickSorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_STRING, TestSortType.DESC));
		compareLists(sortResult, sortResult2);

		Collections.shuffle(reversed);
		sortResult2 = StaticQuickSorter.sort(reversed, new TestSortType(TestSortType.SORT_BY_DOUBLE, TestSortType.DESC));
		compareLists(sortResult, sortResult2);
		
		
	}

}
