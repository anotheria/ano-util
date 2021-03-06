package net.anotheria.util.concurrency;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IdBasedLockManagerTest {
	
	private static final int COUNTERS = 5;
	private static final int WORKERS = 5;
	
	//private SafeIdBasedLockManager lockManager = new SafeIdBasedLockManager();
	
	static class Counter{
		private int value = 0;
		
		void increaseBy(int aValue){
			value += aValue;
		}
		
		int getValue(){
			return value;
		}
	}
	
	static class Worker {
		
		protected int iterations = 1000000;//100000;
		protected Random rnd = new Random(System.nanoTime());
		protected long addedValue = 0;
		
		protected CountDownLatch ready;
		protected CountDownLatch start;
		protected CountDownLatch finish;
		protected IdBasedLockManager<String> lockManager;

		public Worker(CountDownLatch ready, CountDownLatch start, CountDownLatch finish){
			this(new SafeIdBasedLockManager<String>(), ready, start, finish);
		}
		public Worker(IdBasedLockManager <String>aLockManager, CountDownLatch ready, CountDownLatch start, CountDownLatch finish){
			this.ready = ready;
			this.start = start;
			this.finish = finish;
			lockManager = aLockManager;
		}
		
		public long getAddedValue(){
			return addedValue;
		}
	}
	class UnsynchedWorker extends Worker implements Runnable{
		
		public UnsynchedWorker(CountDownLatch ready, CountDownLatch start, CountDownLatch finish){
			super(ready, start, finish);
		}
		
		public void run(){
			ready.countDown();
			try{
				start.await();
			}catch(InterruptedException e){
				//
			}
			for (int i=0; i<iterations; i++){
				int counterId = rnd.nextInt(COUNTERS);
				int valueToAdd = 1;//rnd.nextInt(100);
				counters.get(String.valueOf(counterId)).increaseBy(valueToAdd);
				addedValue += valueToAdd;
			}
			finish.countDown();
		}
	}
	
	class SynchedWorker extends Worker implements Runnable{
		
		public SynchedWorker(IdBasedLockManager lockManager, CountDownLatch ready, CountDownLatch start, CountDownLatch finish){
			super(lockManager, ready, start, finish);
		}
		
		public void run(){
			ready.countDown();
			try{
				start.await();
			}catch(InterruptedException e){
				//
			}
			for (int i=0; i<iterations; i++){
				int counterId = rnd.nextInt(COUNTERS);
				IdBasedLock lock = lockManager.obtainLock(String.valueOf(counterId));
				//SafeIdBasedLockManager.out("worker id "+counterId+" lockcount: "+lock.getReferenceCount());
				lock.lock();
				int valueToAdd = 1;//rnd.nextInt(100);
				counters.get(String.valueOf(counterId)).increaseBy(valueToAdd);
				lock.unlock();
				addedValue += valueToAdd;
			}
			finish.countDown();
		}
	}

	private HashMap<String, Counter> counters = null;
	
	@Before public void init(){
		counters = new HashMap<>();
		for (int i=0; i<COUNTERS; i++)
			counters.put(String.valueOf(i), new Counter());
	}
	private static long unsynchedErrors;
	@Test public void testUnsynched(){
		CountDownLatch ready = new CountDownLatch(WORKERS);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(WORKERS);
		
		UnsynchedWorker[] workers = new UnsynchedWorker[WORKERS];
		for (int i=0; i<WORKERS; i++){
			workers[i] = new UnsynchedWorker(ready, start, finish);
			new Thread(workers[i]).start();
		}
		
		try{
			ready.await();
		}catch(InterruptedException e){}
		
		long startTime = System.currentTimeMillis();
		start.countDown();
		try{
			finish.await();
		}catch(InterruptedException e){}
		long duration = System.currentTimeMillis() - startTime; 

		long workersAdded = 0;
		for (UnsynchedWorker worker : workers){
			workersAdded+=worker.getAddedValue();
		}

		long countersCounted = 0;
		for (Counter c : counters.values()){
			countersCounted += c.getValue();
		}
		
		unsynchedErrors = workersAdded-countersCounted;
		System.out.println("Workers "+workersAdded+", Counters: "+countersCounted+" -> "+(unsynchedErrors)+" in "+duration+" ms, ErrorRate: "+((double)(workersAdded-countersCounted)/workersAdded));
		assertTrue(workersAdded>countersCounted);
	}
	
	@Test public void testSafelySynched(){
		CountDownLatch ready = new CountDownLatch(WORKERS);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(WORKERS);
		IdBasedLockManager lockManager = new SafeIdBasedLockManager();
		
		SynchedWorker[] workers = new SynchedWorker[WORKERS];
		for (int i=0; i<WORKERS; i++){
			workers[i] = new SynchedWorker(lockManager, ready, start, finish);
			new Thread(workers[i]).start();
		}
		
		try{
			ready.await();
		}catch(InterruptedException e){}
		long startTime = System.currentTimeMillis();
		start.countDown();
		
		try{
			finish.await();
		}catch(InterruptedException e){}
		long duration = System.currentTimeMillis() - startTime; 
			
		long workersAdded = 0;
		for (SynchedWorker worker : workers){
			workersAdded+=worker.getAddedValue();
		}

		long countersCounted = 0;
		for (Counter c : counters.values()){
			countersCounted += c.getValue();
		}
		
		System.out.println("Workers "+workersAdded+", Counters: "+countersCounted+" -> "+(workersAdded-countersCounted)+" in "+duration+" ms , ErrorRate: "+((double)(workersAdded-countersCounted)/workersAdded));
		System.out.println(((AbstractIdBasedLockManager)lockManager).getLockSize());
		System.out.println(((AbstractIdBasedLockManager)lockManager).debugString());
		assertEquals(workersAdded,countersCounted);
		assertEquals(0, ((AbstractIdBasedLockManager)lockManager).getLockSize());
	}
	
	@Test public void testUnsafelySynched(){
		CountDownLatch ready = new CountDownLatch(WORKERS);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(WORKERS);
		IdBasedLockManager lockManager = new UnsafeIdBasedLockManager();
		
		SynchedWorker[] workers = new SynchedWorker[WORKERS];
		for (int i=0; i<WORKERS; i++){
			workers[i] = new SynchedWorker(lockManager, ready, start, finish);
			new Thread(workers[i]).start();
		}
		
		try{
			ready.await();
		}catch(InterruptedException e){}
		long startTime = System.currentTimeMillis();
		start.countDown();
		
		try{
			finish.await();
		}catch(InterruptedException e){}
		long duration = System.currentTimeMillis() - startTime; 
			
		long workersAdded = 0;
		for (SynchedWorker worker : workers){
			workersAdded+=worker.getAddedValue();
		}

		long countersCounted = 0;
		for (Counter c : counters.values()){
			countersCounted += c.getValue();
		}
		
		System.out.println("Workers "+workersAdded+", Counters: "+countersCounted+" -> "+(workersAdded-countersCounted)+" in "+duration+" ms , ErrorRate: "+((double)(workersAdded-countersCounted)/workersAdded));
		System.out.println(((AbstractIdBasedLockManager)lockManager).getLockSize());
		System.out.println(((AbstractIdBasedLockManager)lockManager).debugString());
		assertFalse(workersAdded==countersCounted);
		assertEquals(0, ((AbstractIdBasedLockManager)lockManager).getLockSize());
		//System.out.println(unsynchedErrors+" "+unsynchedErrors/100);
		assertTrue("expected "+unsynchedErrors/100+" errors, got "+(workersAdded-countersCounted),(workersAdded-countersCounted)<(unsynchedErrors/100));

	}

}
