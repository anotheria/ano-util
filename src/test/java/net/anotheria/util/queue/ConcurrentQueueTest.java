package net.anotheria.util.queue;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

public class ConcurrentQueueTest {
	
	public static final long REPETITIONS = 20000;
	public static final int FILLER_COUNT = 5;
	public static final int QUEUE_SIZE = 10000;
	
	private static final AtomicLong overflowCount = new AtomicLong(0); 
	private static final AtomicLong elementCount = new AtomicLong(0);
	private static final AtomicLong elementSum = new AtomicLong(0);
	final CountDownLatch startLatch = new CountDownLatch(1);
	final CountDownLatch stopLatch = new CountDownLatch(FILLER_COUNT);
	
	private static final AtomicBoolean runThreadRun = new AtomicBoolean(true);
	
	@Test public void test() throws InterruptedException{
		
		
		long start = System.nanoTime(), end1, end2;
		IQueue<Integer> queue = new StandardQueueFactory<Integer>().createQueue(QUEUE_SIZE);
		final Worker worker = new Worker(queue);
		worker.start();
		
		for (int i=0; i<FILLER_COUNT; i++){
			new Filler(queue).start();
		}
		
		startLatch.countDown();
		stopLatch.await();
		end1 = System.nanoTime();
		runThreadRun.set(false);
		System.out.println("CQT Overflow count: "+overflowCount+", elementCount:" +elementCount+", Sum: "+elementSum);
		while(worker.running){
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}
		end2 = System.nanoTime();
		System.out.println("CQT Worker: count: "+worker.elementCount+", sum: "+worker.elementSum);
		assertEquals("ElementCount should be similar", elementCount.get(), worker.elementCount);
		assertEquals("ElementSum should be similar", elementSum.get(), worker.elementSum);
		System.out.println("CQT Time1 "+(end1-start)/1000/1000+" ms");
		System.out.println("CQT Time2 "+(end2-start)/1000/1000+" ms");
		System.out.println("CQT Time2-1 "+(end2-end1)/1000/1000+" ms");
	}
	
	class Filler extends Thread{
		
		private IQueue<Integer> queue;
		private long elementCount = 0;
		private long elementSum = 0;
		private Random rnd = new Random(System.nanoTime());
		
		Filler(IQueue<Integer> aQueue){
			queue = aQueue;
		}
		
		public void run(){
			try{
				startLatch.await();
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
			for (int i=0; i<REPETITIONS; i++){
				int r = rnd.nextInt(10)+1;
				try{
					queue.putElement(r);
					elementCount++;
					elementSum += r;
				}catch (QueueOverflowException e){
					overflowCount.incrementAndGet();
				}
			}
			
			ConcurrentQueueTest.elementCount.addAndGet(elementCount);
			ConcurrentQueueTest.elementSum.addAndGet(elementSum);
			stopLatch.countDown();
		}
	}
	
	class Worker extends Thread{

		private long elementCount = 0;
		private long elementSum = 0;
		private IQueue<Integer> queue;
		
		boolean started = false;
		volatile boolean running = true;
		
		Worker(IQueue<Integer> aQueue){
			queue = aQueue;
		}
		
		@Override
		public void run() {
			//wait for first element.
			while(!queue.hasElements());
			while(runThreadRun.get() || queue.hasElements()){
				if (queue.hasElements()){
					Integer i = queue.nextElement();
					elementSum += i;
					elementCount++;
				}
			}
			running = false;
		}
		
	}
}
