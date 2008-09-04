package net.anotheria.util.queue;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;



public class QueuedProcessor <T extends Object> extends Thread{
	private String name;
	private int counter;
	private Logger log;
	private static Logger defaultLog;
	private IQueue<T> queue;
	private IQueueWorker<T> worker;
	public static final long DEF_SLEEP_TIME = 50;
	public static final int DEF_QUEUE_SIZE = 1000;
	private int queueSize;
	private long sleepTime;
	private int overflowCount;
	private int throwAwayCount;
	
	private IQueueFactory<T> queueFactory = new StandardQueueFactory<T>();
	
	
	
	private AtomicBoolean stopAfterQueueProcessing;
	private AtomicBoolean stopImmediately;
	
	
	static {
		defaultLog = Logger.getLogger(QueuedProcessor.class);
	}

	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, int aQueueSize, long aSleepTime, Logger aLog) {
		super(aName);
		setDaemon(true);
		
		queueSize = aQueueSize;
		sleepTime = aSleepTime;
		worker = aWorker;
		

		log = aLog;
		if (log == null) {
			defaultLog.warn("Tried to assign null logger, switching to defLogger");
			log = defaultLog;
		}
		
		init();
	}

	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, Logger log) {
		this(aName, aWorker, DEF_QUEUE_SIZE, DEF_SLEEP_TIME, log);
	}

	public QueuedProcessor(String aName, IQueueWorker<T> aWorker) {
		this(aName, aWorker, defaultLog);
	}
	
	public void init(){
		queue = queueFactory.createQueue(queueSize);
		stopAfterQueueProcessing = new AtomicBoolean(false);
		stopImmediately = new AtomicBoolean(false);
	}

	public void reset(){
		init();
	}
	
	private Lock lock = new ReentrantLock();
	private Condition notFull  = lock.newCondition(); 
	
	/**
	 * Inserts the specified element at the tail of the processing queue, waiting if
     * necessary for space in the queue to become available 
	 * @param element the element to add
	 */
	public void addToQueueAndWait(T element) {
		lock.lock();
		try {
			try{
				while(queue.size() - 1 <= queue.getElementCount()){
					notFull.await();
				}
			}catch(InterruptedException e){
				notFull.signal();
			}
			queue.putElement(element);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @param element
	 * @throws UnrecoverableQueueOverflowException
	 */
	public void addToQueue(T element) throws UnrecoverableQueueOverflowException{
		addToQueueDontWait(element);
	}
	
	/**
	 * Inserts the specified element at the tail of the processing queue if the queue is not full
     * 
	 * @param element
	 * @throws UnrecoverableQueueOverflowException if the processing queue is full
	 */
	public void addToQueueDontWait(T element) throws UnrecoverableQueueOverflowException{
		lock.lock();
		try{
			if(stopAfterQueueProcessing.get())
				throw new RuntimeException("Queueing is stopped!");
			try {
				queue.putElement(element);
			} catch (QueueOverflowException e1) {
				overflowCount++;
				// ok, first exception, we try to recover
				synchronized (this) {
					try {
						Thread.sleep(100);
					} catch (Exception ignored) {
					}
				}
				try {
					queue.putElement(element);
				} catch (QueueOverflowException e2) {
					throwAwayCount++;
					log.error("couldn't recover from queue overflow, throwing away " + element);
					throw new UnrecoverableQueueOverflowException("Element: " + element + ", stats:" + getStatsString());
				}
			}
		}finally{
			lock.unlock();
		}
	}
	
	@Override
	public void run() {
		try {
			counter = 0;
			while (!stopImmediately.get()) {				
				if (queue.hasElements()) {
					counter++;
					if ((counter / 100 * 100) == counter) {
						logOutInfo();
					}
					try {
						T element = null;
						lock.lock();
						try{
							element = (T) queue.nextElement();
							notFull.signal();
						}finally{
							lock.unlock();
						}
						worker.doWork(element);
					} catch (Exception e) {
						log.error("myChannel.push", e);
					}
				} else {
					if(stopAfterQueueProcessing.get())
						break;
					try {
						sleep(sleepTime);
					} catch (InterruptedException ignored) {
					}
				}
			}
		} catch (Throwable ttt) {
			try {
				log.error("run ", ttt);
			} catch (Exception e) {
				System.out.println(QueuedProcessor.class + " Can't log!!!");
				ttt.printStackTrace();
			}
		}
	}

	/**
	 * Deny queueing and sends signal to stop the QueueProcessor running Thread after all elements that already in processing queue will be worked.
	 */
	public void stopAfterQueueProcessing(){
		stopAfterQueueProcessing.set(true);
	}
	
	/**
	 * Sends signal to stop the QueueProcessor running Thread after working current element.
	 */
	public void stopImmediately(){
		stopImmediately.set(true);
	}
	
	/**
	 * @return true if processing was stopped by calling stopAfterQueueProcessing() or stopImmediately().
	 */
	public boolean isStopped(){
		return stopImmediately.get() || stopAfterQueueProcessing.get();
	}
	
	/**
	 * @return
	 */
	public int getQueueOverflowCount() {
		return overflowCount;
	}
	
	public int getQueueSize(){
		return queue.getElementCount();
	}

	/**
	 * @return
	 */
	public int getThrowAwayCount() {
		return throwAwayCount;
	}

	public String getStatsString() {
		return counter + " elements worked, queue: " + queue.toString() + ", OC:" + overflowCount + ", TAC:" + throwAwayCount;
	}

	public void logOutInfo() {
		log.info(name + ": " + counter + " elements worked, stat: " + queue.toString() + ", OC:" + overflowCount + ", TAC:" + throwAwayCount);
	}

	public IQueueFactory<T> getQueueFactory() {
		return queueFactory;
	}

	public void setQueueFactory(IQueueFactory<T> queueFactory) {
		this.queueFactory = queueFactory;
	}

}
