package net.anotheria.util.queue;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

public class QueuedProcessorTest {
	
	public static final long REPETITIONS = 20000;
	public static final int FILLER_COUNT = 5;
	public static final int QUEUE_SIZE = 10000;
	
	private static final AtomicLong overflowCount = new AtomicLong(0); 
	private static final AtomicLong elementCount = new AtomicLong(0);
	private static final AtomicLong elementSum = new AtomicLong(0);
	final CountDownLatch startLatch = new CountDownLatch(1);
	final CountDownLatch stopLatch = new CountDownLatch(FILLER_COUNT);
	
	@Test public void test() throws InterruptedException{
		
		long start = System.nanoTime(), end1, end2;
		final Worker worker = new Worker();
		QueuedProcessor<Integer> processor = new QueuedProcessor<Integer>("test", worker, QUEUE_SIZE, LoggerFactory.getLogger(QueuedProcessorTest.class));
		processor.start();
		
		for (int i=0; i<FILLER_COUNT; i++){
			new Filler(processor).start();
		}
		
		startLatch.countDown();
		stopLatch.await();
		end1 = System.nanoTime();
		System.out.println("CPT Overflow count: "+overflowCount+", elementCount:" +elementCount+", Sum: "+elementSum);
		while(processor.getQueueSize()!=0){
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}
		end2 = System.nanoTime();
		System.out.println("CPT Worker: count: "+worker.elementCount+", sum: "+worker.elementSum);
		assertEquals("ElementCount should be similar", elementCount.get(), worker.elementCount);
		assertEquals("ElementSum should be similar", elementSum.get(), worker.elementSum);
		System.out.println("CPT Time1 "+(end1-start)/1000/1000+" ms");
		System.out.println("CPT Time2 "+(end2-start)/1000/1000+" ms");
		System.out.println("CPT Time2-1 "+(end2-end1)/1000/1000+" ms");
	
	} 
	
	class Filler extends Thread{
		
		private QueuedProcessor<Integer> proc;
		private long elementCount = 0;
		private long elementSum = 0;
		private Random rnd = new Random(System.nanoTime());
		
		Filler(QueuedProcessor<Integer> aProc){
			proc = aProc;
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
					proc.addToQueue(r);
					elementCount++;
					elementSum += r;
				}catch (UnrecoverableQueueOverflowException e){
					overflowCount.incrementAndGet();
				}
			}
			
			QueuedProcessorTest.elementCount.addAndGet(elementCount);
			QueuedProcessorTest.elementSum.addAndGet(elementSum);
			stopLatch.countDown();
		}
	}
	
	class Worker implements IQueueWorker<Integer>{

		private long elementCount = 0;
		private long elementSum = 0; 
		
		@Override
		public void doWork(Integer workingElement) throws Exception {
			elementSum += workingElement;
			elementCount++;
		}
		
	}
}
