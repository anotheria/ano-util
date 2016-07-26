package net.anotheria.util.concurrency;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ObjectConfinementSyncTestik {

	private static final class TestObject{
		
		private String id;
		private AtomicInteger modificationCounter = new AtomicInteger(0);
		private Random rnd = new Random(System.currentTimeMillis());
		
		private TestObject(String id){
			this.id = id;
		}

		public void modify(){
			int currentModification = modificationCounter.get();
			try {
				Thread.sleep(rnd.nextInt(100));
			} catch (InterruptedException e) {
				throw new AssertionError("Must never happen!");
			}
			if(modificationCounter.getAndIncrement() != currentModification)
				throw new ConcurrentModificationException("Synchronization error!");
		}
		
		@Override
		public int hashCode() {
			return id.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if(!(obj instanceof TestObject))
				return false;
			TestObject other = (TestObject) obj;
			return id.equals(other.id);
		}

		@Override
		public String toString() {
			return "TestObject [id=" + id + "]";
		}
	}
	
	private static ConcurrentHashMap<TestObject, ReentrantLock> objectConfinementLocks;
	
	@BeforeClass public static void init(){
		objectConfinementLocks = new ConcurrentHashMap<TestObject, ReentrantLock>();
	}
	
	private void acquireObjectConfinmenetLock(TestObject testObject){
		ReentrantLock lock = objectConfinementLocks.get(testObject);
		if(lock == null){
			ReentrantLock newLock = new ReentrantLock();
			lock = objectConfinementLocks.putIfAbsent(testObject, newLock);
			if(lock == null)
				lock = newLock;
		}
		lock.lock();
	}
	
	private void releaseObjectConfinmenetLock(TestObject testObject){
		ReentrantLock lock = objectConfinementLocks.get(testObject);
		if(lock == null)
			throw new AssertionError("Has lock was acquired?");
		try{
		}finally{
			//TODO: Time between check and remove is not synchronized!
			if(!lock.hasQueuedThreads()){
				try {
					//Increase that time!
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
				objectConfinementLocks.remove(testObject);
				System.out.println("DELETED LOCK: " + testObject + ", locks: " + objectConfinementLocks.size());
			}
			lock.unlock();
		}
	}
	
	@Test public void concurrencyTest(){
		int objCount = 3;
		int threadsCount = 100; 
		Random rnd = new Random(System.currentTimeMillis());
		TestObject[] testObjects = new TestObject[objCount];
		for(int i = 0; i < objCount; i++){
			testObjects[i] = new TestObject(i + "");
		}
		
		for(int j = 0; j < threadsCount; j++){
			for(final TestObject obj: testObjects){
				try {
					Thread.sleep(rnd.nextInt(300));
				} catch (InterruptedException e) {
					throw new AssertionError("Must never happen!");
				}
				new Thread(new Runnable() {
					@Override
					public void run() {
						handleTestObjectSync(obj);
					}
				}).start();
			}
		}
		while (Thread.activeCount() > 1) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignored) {
			}
		}
		int errorsRate = (int)((float)errorsCounter.get()/workedCounter.get() * 100);
		System.out.println("---------------------");
		System.out.println("STATS: totatl errors " + errorsCounter + ", errorsRate " + errorsRate + "%, locks rest " + objectConfinementLocks.size());
	}
	
	private AtomicInteger workedCounter = new AtomicInteger();
	private AtomicInteger errorsCounter = new AtomicInteger();
	private void handleTestObjectSync(TestObject testObject){
//		System.out.println("Handle " + testObject);
		acquireObjectConfinmenetLock(testObject);
		int worked = 0;
		int errors = 0;
		try{
			System.out.println("START: " + testObject);
			worked = workedCounter.incrementAndGet();
			testObject.modify();
		}catch(ConcurrentModificationException e){
			errors = errorsCounter.incrementAndGet();
		}finally{
			releaseObjectConfinmenetLock(testObject);
			int errorsRate = (int)((float)errors/worked * 100);
			System.out.println("FINISH: " + testObject + ", errors: " + errors + ", errorsRate: " + errorsRate + "%, locks: " + objectConfinementLocks.size());
		}
	}
	
	public static void main(String[] args) {
		ObjectConfinementSyncTestik.init();
		new ObjectConfinementSyncTestik().concurrencyTest();
	}
}
