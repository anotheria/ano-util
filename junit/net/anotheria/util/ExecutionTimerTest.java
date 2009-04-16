package net.anotheria.util;

import org.junit.Test;
import static junit.framework.Assert.*;

public class ExecutionTimerTest {
	@Test
	public void testContinue() throws InterruptedException{
		ExecutionTimer t = new ExecutionTimer("1");
		t.startExecution("sleep");
		Thread.sleep(500);
		t.stopExecution("sleep");
		//t.printExecutionTimesOrderedByCreation();
		t.continueExecution("sleep");
		Thread.sleep(500);
		t.stopExecution("sleep");
		//t.printExecutionTimesOrderedByCreation();
		t.continueExecution("sleep");
		Thread.sleep(500);
		t.stopExecution("sleep");
		t.printExecutionTimesOrderedByCreation();
		
		assertTrue(t.getExecutionTime("sleep")>(1500-10));
		assertTrue(t.getExecutionTime("sleep")<(1500+10));
	}

	@Test public void testFailover() throws InterruptedException{
		ExecutionTimer timer = new ExecutionTimer();
		
		timer.continueExecution("sleep");
		Thread.sleep(100);
		timer.stopExecution("sleep");
		assertTrue(timer.getExecutionTime("sleep")>(100-10));
		assertTrue(timer.getExecutionTime("sleep")<(100+10));
		
	}
	
	@Test public void testMultiple() throws InterruptedException{
		ExecutionTimer timer = new ExecutionTimer();
		for (int i=1; i<=10; i++){
			String key = ""+(11-i);
			timer.startExecution(key);
			Thread.sleep(100);
			timer.stopExecution(key);
		}
		
		assertEquals(10, timer.getExecutionTimerEntriesOrderedByCreation().size());
		assertEquals(10, timer.getExecutionTimerEntriesOrderedByKeys().size());
		assertEquals(10, timer.getExecutionTimerEntriesOrderedByTime().size());
		
		assertTrue(timer.getTotalExecutionTime()>1000-10);
		assertTrue(timer.getTotalExecutionTime()<1000+10);

		timer.printExecutionTimesOrderedByCreation();
		timer.printExecutionTimesOrderedByTime();
		timer.printExecutionTimesOrderedByKeys();
}

}