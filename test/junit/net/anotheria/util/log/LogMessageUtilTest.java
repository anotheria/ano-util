package net.anotheria.util.log;

import static net.anotheria.util.log.LogMessageUtil.failMsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Test for {@link LogMessageUtil} (examples).
 * 
 * @author Alexandr Bolbat
 */
public class LogMessageUtilTest {

	/**
	 * Examples how to use it.
	 */
	@Test
	public void examples() {
		System.out.println("----- LogMessageUtilTest: Started examples() -----");
		new Date().toString(); // initialization

		Throwable t = new IllegalArgumentException("ias");

		// no arguments
		long startTime = System.currentTimeMillis();
		String msg = failMsg(t);
		System.out.println("No args: " + msg);

		// basic
		startTime = System.currentTimeMillis();
		msg = failMsg(t, null, "second_param", "third_param");
		System.out.println("Basic args(" + (System.currentTimeMillis() - startTime) + "ms): " + msg);

		// array
		int[] array = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		startTime = System.currentTimeMillis();
		msg = failMsg(t, array);
		System.out.println("Primitive types array(" + (System.currentTimeMillis() - startTime) + "ms): " + msg);

		// multiple arrays
		int[][] mArray = new int[][] { new int[] { 1, 2, 3, 4, 5, 6 }, null, null, new int[] { 3, 4, 5 } };
		startTime = System.currentTimeMillis();
		msg = failMsg(t, (Object) mArray);
		System.out.println("Primitive type multiple array(" + (System.currentTimeMillis() - startTime) + "ms): " + msg);

		// complex objects
		Object dates = (Object) new Date[] { new Date(), new Date() };
		startTime = System.currentTimeMillis();
		msg = failMsg(t, dates);
		System.out.println("Complex type array(" + (System.currentTimeMillis() - startTime) + "ms): " + msg);

		// list
		List<String> arrayList = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
		startTime = System.currentTimeMillis();
		msg = failMsg(t, arrayList);
		System.out.println("List(" + (System.currentTimeMillis() - startTime) + "ms): " + msg);

		// set
		startTime = System.currentTimeMillis();
		msg = failMsg(t, new HashSet<String>(arrayList));
		System.out.println("Set(" + (System.currentTimeMillis() - startTime) + "ms): " + msg);

		// list of lists
		List<List<String>> arrayListOfLists = new ArrayList<List<String>>();
		arrayListOfLists.add(Arrays.asList("1", "2", "3", "4"));
		arrayListOfLists.add(Arrays.asList("5", "6", "7", "8", "9", "10", "11", "12"));
		arrayListOfLists.add(Arrays.asList("x", "y"));
		arrayListOfLists.add(Arrays.asList("z"));
		startTime = System.currentTimeMillis();
		msg = failMsg(t, arrayListOfLists);
		System.out.println("List of lists(" + (System.currentTimeMillis() - startTime) + "ms): " + msg);

		// map
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 10; i++)
			map.put("key" + i, "value" + i);

		startTime = System.currentTimeMillis();
		msg = failMsg(t, map);
		System.out.println("Map(" + (System.currentTimeMillis() - startTime) + "ms): " + msg);

		// all in one
		Map<String, Object> hugeArgument = new HashMap<String, Object>();
		hugeArgument.put("array", array);
		hugeArgument.put("mArray", mArray);
		hugeArgument.put("datesArray", dates);

		startTime = System.currentTimeMillis();
		msg = failMsg(t, hugeArgument, arrayList, arrayListOfLists, map);
		System.out.println("Huge argument(" + (System.currentTimeMillis() - startTime) + "ms): " + msg);

		System.out.println("----- LogMessageUtilTest: Finished examples() -----");
	}

}
