package net.anotheria.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ArrayUtilsTest {
	
	public static class TestObject{
		private String value; 
		public TestObject(String aValue){
			value = aValue;
		}
		public String toString(){
			return value;
		}
		@Override public boolean equals(Object aObj){
			if(!(aObj instanceof TestObject))
				return false;
			return value != null && value.equals(((TestObject)aObj).value);
		}
	}
	
	public static int[] intSortedArray1;
	public static int[] intSortedArray2;
	public static int[] intUnsortedArray1;
	public static int[] intUnsortedArray2;
	public static Integer[] integerSortedArray1;
	public static Integer[] integerSortedArray2;
	public static Integer[] integerUnsortedArray1;
	public static Integer[] integerUnsortedArray2;
	public static List<String> stringList;
	public static List<TestObject> testObjectsList;
	
	static{
		intSortedArray1 = new int[]{1,2,3,4,5};
		intSortedArray2 = new int[]{6,7,8,9,10,11};
		intUnsortedArray1 = new int[]{1,2,4,3,5};
		intUnsortedArray2 = new int[]{6,8,9,7,11};
		integerSortedArray1 = new Integer[]{1,2,3,4,5};
		integerSortedArray2 = new Integer[]{6,7,8,9,10,11};
		integerUnsortedArray1 = new Integer[]{1,2,4,3,5};
		integerUnsortedArray1 = new Integer[]{6,8,9,7,11};
        
		stringList = new ArrayList<>();
		testObjectsList = new ArrayList<>();
        for(int i: intSortedArray1){
        	testObjectsList.add(new TestObject(String.valueOf(i)));
        	stringList.add(Integer.toString(i));
        }

        System.out.println("Test int array1: " + ArrayUtils.toString(intSortedArray1));
        System.out.println("Test Integer array1: " + ArrayUtils.toString(integerSortedArray1));
        System.out.println("Test Objects List1: " + testObjectsList);
	}
	
	@Test public void toStringList(){
		
		List<String> stringList1 = ArrayUtils.toStringList(intSortedArray1);
		assertEquals(intSortedArray1.length, stringList1.size());
		for(int i = 0; i < intSortedArray1.length; i++)
			assertEquals(Integer.toString(intSortedArray1[i]), stringList1.get(i));

		List<String> stringList2 = ArrayUtils.toStringList(integerSortedArray1);
		assertEquals(integerSortedArray1.length, stringList2.size());
		for(int i = 0; i < integerSortedArray1.length; i++)
			assertEquals(Integer.toString(integerSortedArray1[i]), stringList2.get(i));
		assertEquals(stringList1, stringList2);
		
		List<String> stringList3 = ArrayUtils.toStringList(testObjectsList);
		assertEquals(testObjectsList.size(), stringList3.size());
		for(int i = 0; i < testObjectsList.size(); i++)
			assertEquals(testObjectsList.get(i).toString(), stringList3.get(i));
		assertEquals(stringList1, stringList3);
	}
	
	@Test public void parseStringList(){
		int[] array = ArrayUtils.parseStringListAsInt(stringList);
		assertTrue(Arrays.equals(intSortedArray1, array));
	}
	
	@Test public void toXArray(){
		Integer[] convertedIntegerArray = ArrayUtils.toIntegerArray(intSortedArray1);
		//By default arrays equality check is not deep so Arrays.equals() method is used instead!
		assertTrue(Arrays.equals(integerSortedArray1, convertedIntegerArray));
		int[] backConvertedIntArray = ArrayUtils.toIntArray(convertedIntegerArray);
		assertTrue(Arrays.equals(intSortedArray1, backConvertedIntArray));
	}
	
	@Test public void mergeArrays(){
		int intMergedLength = intSortedArray1.length + intSortedArray2.length;
		int[] intMergedArray = ArrayUtils.mergeArrays(intSortedArray1, intSortedArray2);
		assertEquals(intMergedLength, intMergedArray.length);
		for(int i = 0; i < intSortedArray1.length; i++)
			assertEquals(intSortedArray1[i], intMergedArray[i]);
		for(int i = 0; i < intSortedArray2.length; i++)
			assertEquals(intSortedArray2[i], intMergedArray[intSortedArray1.length + i]);
		
		int integerMergedLength = integerSortedArray1.length + integerSortedArray2.length;
		Integer[] integerMergedArray = ArrayUtils.mergeArrays(integerSortedArray1, integerSortedArray2);
		assertEquals(integerMergedLength, integerMergedArray.length);
		for(int i = 0; i < integerSortedArray1.length; i++)
			assertEquals(integerSortedArray1[i], integerMergedArray[i]);
		for(int i = 0; i < integerSortedArray2.length; i++)
			assertEquals(integerSortedArray2[i], integerMergedArray[integerSortedArray1.length + i]);
	}
	
	@Test public void addToArray(){
		int intNumber = 123;

		int[] intResultArray = ArrayUtils.addToArray(intSortedArray1, intNumber);
		assertEquals(intSortedArray1.length + 1, intResultArray.length);
		for(int j = 0; j < intSortedArray1.length; j++)
			assertEquals(intSortedArray1[j], intResultArray[j]);
		assertEquals(intNumber, intResultArray[intResultArray.length - 1]);
		
		assertTrue(Arrays.equals(intResultArray, ArrayUtils.mergeArrays(intSortedArray1, intNumber)));
		
		Integer[] integerResultArray = ArrayUtils.addToArray(integerSortedArray1, intNumber);
		assertEquals(integerSortedArray1.length + 1, integerResultArray.length);
		for(int j = 0; j < integerSortedArray1.length; j++)
			assertEquals(integerSortedArray1[j], integerResultArray[j]);
		Integer integerNumber = intNumber;
		assertEquals(integerNumber, integerResultArray[integerResultArray.length - 1]);
		
		assertTrue(Arrays.equals(integerResultArray, ArrayUtils.mergeArrays(integerSortedArray1, integerNumber)));
	}
	
	@Test public void subArray(){
		int from = 1;
		int to = 4;
		int[] intSubArray = ArrayUtils.subArray(intSortedArray1, from, to);
		assertEquals(to - from, intSubArray.length);
		for(int i = from; i < to ; i++)
			assertEquals(intSortedArray1[i], intSubArray[i - from]);
		
		Integer[] integerSubArray = ArrayUtils.subArray(integerSortedArray1, from, to);
		assertEquals(to - from, integerSubArray.length);
		for(int i = from; i < to ; i++)
			assertEquals(integerSortedArray1[i], integerSubArray[i - from]);
	}
	
	@Test public void contains(){
		assertTrue(ArrayUtils.contains(intSortedArray1, intSortedArray1[3]));
		assertFalse(ArrayUtils.contains(intSortedArray1, -1));
		assertFalse(ArrayUtils.contains(intSortedArray1, 1000));
		assertTrue(ArrayUtils.contains(integerSortedArray1, integerSortedArray1[3]));
		assertFalse(ArrayUtils.contains(integerSortedArray1, -1));
		assertFalse(ArrayUtils.contains(integerSortedArray1, 1000));
		assertTrue(ArrayUtils.contains(integerSortedArray1, intSortedArray1[3]));
	}
	
	@Test public void getIntersection(){
		assertTrue(Arrays.equals(intSortedArray1, ArrayUtils.intersection(intSortedArray1, intSortedArray1)));
		int[] intSubArray = ArrayUtils.subArray(intSortedArray1, 1, 3);
		int[] intIntersection = ArrayUtils.intersection(intSortedArray1, intSubArray);
		assertTrue(Arrays.equals(intSubArray, intIntersection));
		intIntersection = ArrayUtils.intersection(intSubArray, intSortedArray1);
		assertTrue(Arrays.equals(intSubArray, intIntersection));
		assertEquals(0, ArrayUtils.intersection(intSortedArray1, intSortedArray2).length);
		try{
			ArrayUtils.intersection(intSortedArray1, intUnsortedArray1);
			fail("ArrayUtils.intersection() for not sorted arrays must throw exception!");
		}catch(RuntimeException ignored){}
		try{
			ArrayUtils.intersection(intUnsortedArray1,intSortedArray1);
			fail("ArrayUtils.intersection() for not sorted arrays must throw exception!");
		}catch(RuntimeException ignored){}
		try{
			ArrayUtils.intersection(intUnsortedArray1, intUnsortedArray2);
			fail("ArrayUtils.intersection() for not sorted arrays must throw exception!");
		}catch(RuntimeException ignored){}
	}
	
	@Test public void isSorted(){
		assertTrue(ArrayUtils.isSorted(intSortedArray1));
		assertFalse(ArrayUtils.isSorted(true, intSortedArray1));
		assertFalse(ArrayUtils.isSorted(intUnsortedArray1));
		assertFalse(ArrayUtils.isSorted(true, intUnsortedArray1));
		
		int[] array = Arrays.copyOf(intUnsortedArray1, intUnsortedArray1.length);
		Arrays.sort(array);
		assertTrue(ArrayUtils.isSorted(array));
		assertFalse(ArrayUtils.isSorted(true,array));
				
		assertTrue(ArrayUtils.isSorted(1,2,3,4,5,6,10));
		assertFalse(ArrayUtils.isSorted(2,3,4,11,5,6,10));
	}
	
	@Test public void asListTest(){
		
		List<String> stringList = new ArrayList<>();
		stringList.add("s1");
		stringList.add("s2");
		stringList.add("s3");
		assertEquals(stringList, ArrayUtils.asList("s1","s2","s3"));
		assertEquals(stringList, ArrayUtils.asList("s1","s2","s3"));
		
		List<TestObject> testObjectList = new ArrayList<>();
		testObjectList.add(new TestObject("1"));
		testObjectList.add(new TestObject("2"));
		testObjectList.add(new TestObject("3"));
		assertEquals(testObjectList, ArrayUtils.asList(new TestObject("1"),new TestObject("2"),new TestObject("3")));
		assertEquals(testObjectList, ArrayUtils.asList(new TestObject("1"),new TestObject("2"),new TestObject("3")));
		
		List<Integer> integerList = new ArrayList<>();
		integerList.add(1);
		integerList.add(2);
		integerList.add(3);
		assertEquals(integerList, ArrayUtils.asList(1,2,3));
		assertEquals(integerList, ArrayUtils.asList(new int[]{1,2,3}));
		assertEquals(integerList, ArrayUtils.asList(1,2,3));
		
		List<Long> longList = new ArrayList<>();
		longList.add(1L);
		longList.add(2L);
		longList.add(3L);
		assertEquals(longList, ArrayUtils.asList(1L,2L,3L));
		assertEquals(longList, ArrayUtils.asList(new long[]{1L,2L,3L}));
		assertEquals(longList, ArrayUtils.asList(1L,2L,3L));
		
		List<Float> floatList = new ArrayList<>();
		floatList.add(1F);
		floatList.add(2F);
		floatList.add(3F);
		assertEquals(floatList, ArrayUtils.asList(1F,2F,3F));
		assertEquals(floatList, ArrayUtils.asList(new float[]{1F,2F,3F}));
		assertEquals(floatList, ArrayUtils.asList(1F,2F,3F));
	}

}
 