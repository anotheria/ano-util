package net.anotheria.util.queue;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;



public class QueueProcessor <T extends Object> extends Thread{
	private String name;
	private int counter;
	private Logger log;
	private static Logger defaultLog;
	private IQueue queue;
	private IWorker<T> worker;
	public static final long DEF_SLEEP_TIME = 50;
	public static final int DEF_QUEUE_SIZE = 1000;
	private long sleepTime;
	private int overflowCount;
	private int throwAwayCount;
	
	private AtomicBoolean stopAfterQueueProcessing;
	private AtomicBoolean stopImmediately;
	
	
	static {
		defaultLog = Logger.getLogger(QueueProcessor.class);
	}

	public QueueProcessor(String aName, IWorker<T> aWorker, int queueSize, long aSleepTime, Logger aLog) {
		super(aName);
		setDaemon(true);
		sleepTime = aSleepTime;
		worker = aWorker;
		queue = QueueFactory.createQueue(queueSize);

		log = aLog;
		if (log == null) {
			defaultLog.warn("Tried to assign null logger, switching to defLogger");
			log = defaultLog;
		}
		
		stopAfterQueueProcessing = new AtomicBoolean(false);
		stopImmediately = new AtomicBoolean(false);
		
	}

	public QueueProcessor(String aName, IWorker<T> aWorker, Logger log) {
		this(aName, aWorker, DEF_QUEUE_SIZE, DEF_SLEEP_TIME, log);
	}

	public QueueProcessor(String aName, IWorker<T> aWorker) {
		this(aName, aWorker, defaultLog);
	}
	
	
	private Lock lock = new ReentrantLock();
	private Condition notFull  = lock.newCondition(); 
	
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

	public void addToQueue(T element) throws UnrecoverableQueueOverflowException{
		addToQueueDontWait(element);
	}
	
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
						if (element == null) {
							log.error("Event to send is NULL, skipped.");
						} else {
							worker.doWork(element);
						}
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
				System.out.println(QueueProcessor.class + " Can't log!!!");
				ttt.printStackTrace();
			}
		}
	}

	public void stopAfterQueueProcessing(){
		stopAfterQueueProcessing.set(true);
	}
	
	public void stopImmediately(){
		stopImmediately.set(true);
	}
	
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

}
