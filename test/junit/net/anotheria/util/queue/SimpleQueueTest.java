package net.anotheria.util.queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SimpleQueueTest {
	@Test public void testReadWrite(){
		IQueue<Integer> queue = new StandardQueueFactory().createQueue(50);
		for (int i=0; i<50; i++){
			queue.putElement(i);
		}
		
		assertEquals(50, queue.getElementCount());
		assertTrue(queue.hasElements());
		
		int expected = 0;
		int read = 0;
		while(queue.hasElements()){
			assertEquals(expected, queue.nextElement().intValue());
			expected++;
			read++;
		}
		
		assertEquals(50, read);
	}
	
	@Test public void testEmptyRead(){
		IQueue<Integer> queue = new StandardQueueFactory<Integer>().createQueue(100);
		try{
			queue.nextElement();
			fail("expected exception");
		}catch(Exception e){}
	}

	/**
	 * This test tests whether the read-rotate works (and write rotate for this matter).
	 */
	@Test public void testOverread(){
		IQueue<Integer> queue = new StandardQueueFactory<Integer>().createQueue(100);
		//read 50 elements away.
		for (int i=0; i<50; i++){
			queue.putElement(i);
		}
		for (int i=0; i<50; i++){
			queue.nextElement();
		}


		for (int i=0; i<99; i++){
			queue.putElement(i);
		}
		for (int i=0; i<99; i++){
			queue.nextElement();
		}
	}


	@Test public void testOverflow(){
		IQueue<Integer> queue = new StandardQueueFactory<Integer>().createQueue(3);
		queue.putElement(1);
		queue.putElement(2);
		queue.putElement(3);
		try{
			queue.putElement(1000);
			fail("overflow expected");
		}catch(QueueOverflowException e){}
		
		
		
		queue.nextElement();
		queue.nextElement();
		queue.nextElement();
		queue.putElement(4);
		queue.putElement(5);
		queue.putElement(6);
		try{  
			queue.putElement(1000);
			fail("overflow expected");
		}catch(QueueOverflowException e){}
		
	}
}
