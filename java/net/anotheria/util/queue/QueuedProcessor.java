package net.anotheria.util.queue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

public class QueuedProcessor<T extends Object> extends Thread {
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
	private long waitingTime;
	
	private final IQueueFactory<T> DEF_QUEUE_FACTORY = new StandardQueueFactory<T>();
	private IQueueFactory<T> queueFactory;
	private AtomicBoolean stopImmediately;
	
	static {
		defaultLog = Logger.getLogger(QueuedProcessor.class);
	}

	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, IQueueFactory<T> aQueueFactory, int aQueueSize, long aSleepTime, Logger aLog) {
		super(aName);
		setDaemon(true);
		
		queueSize = aQueueSize;
		sleepTime = aSleepTime;
		worker = aWorker;

		queueFactory = aQueueFactory == null ? DEF_QUEUE_FACTORY : aQueueFactory;
		

		log = aLog;
		if (log == null) {
			defaultLog.warn("Tried to assign null logger, switching to defLogger");
			log = defaultLog;
		}
		
		init();
	}

	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, IQueueFactory<T> aQueueFactory, Logger aLog) {
		this(aName, aWorker,  aQueueFactory, DEF_QUEUE_SIZE, DEF_SLEEP_TIME, aLog);
	}
	
	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, int aQueueSize, long aSleepTime, Logger aLog) {
		this(aName, aWorker,  null, aQueueSize, aSleepTime, aLog);
	}

	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, int aQueueSize, Logger aLog) {
		this(aName, aWorker,  aQueueSize, DEF_SLEEP_TIME, aLog);
	}
	
	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, Logger aLog) {
		this(aName, aWorker, DEF_QUEUE_SIZE, DEF_SLEEP_TIME, aLog);
	}

	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, IQueueFactory<T> aQueueFactory) {
		this(aName, aWorker, aQueueFactory, defaultLog);
	}
	
	
	public QueuedProcessor(String aName, IQueueWorker<T> aWorker) {
		this(aName, aWorker, null, defaultLog);
	}

	public void init(){
		queue = queueFactory.createQueue(queueSize);
		stopImmediately = new AtomicBoolean(false);
	}

	public void reset(){
		init();
	}
	
	/**
	 * Inserts the specified element at the tail of the processing queue, waiting if
     * necessary for space in the queue to become available 
	 * @param element the element to add
	 */

	public void addToQueueAndWait(T element) {
		while(true){
			try{
				queue.putElement(element);
				return;
			}catch(QueueOverflowException e){
				overflowCount++;
				try {
					synchronized(queue) {
						long waitStart = System.currentTimeMillis();
						queue.wait();
						waitingTime += System.currentTimeMillis() - waitStart;
					}
				} catch(InterruptedException ignored) {
					log.warn("Ignored exception: " + ignored.getMessage(), ignored);
				}
			}
		}
	}

	/**
	 * @param aElement
	 * @throws UnrecoverableQueueOverflowException if the processing queue is full.
	 */
	public void addToQueue(T aElement) throws UnrecoverableQueueOverflowException {
		addToQueueDontWait(aElement);
	}
	
	/**
	 * Inserts the specified element at the tail of the processing queue if the queue is not full
     * 
	 * @param aElement
	 * @throws UnrecoverableQueueOverflowException if the processing queue is full.
	 */
	public void addToQueueDontWait(T aElement) throws UnrecoverableQueueOverflowException {
		try {
			queue.putElement(aElement);
		} catch (QueueOverflowException e1) {
			overflowCount++;
			// ok, first exception, we try to recover
			synchronized (this) {
				try {
					long waitStart = System.currentTimeMillis();
					Thread.sleep(100);
					waitingTime += System.currentTimeMillis() - waitStart;
				} catch (Exception ignored) {
				}
			}
			try {
				queue.putElement(aElement);
			} catch (QueueOverflowException e2) {
				throwAwayCount++;
				log.error("couldn't recover from queue overflow, throwing away " + aElement);
				throw new UnrecoverableQueueOverflowException("Element: " + aElement + ", stats:" + getStatsString());
			}
		}
	}
	
	@Override
	public void run() {
		final AtomicBoolean shutdown = new AtomicBoolean(false);
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run(){
				log.info("Shutting down processor!");
				shutdown.set(true);
				try {
					synchronized (shutdown) {
						log.info("Wait while queue processing complete...");
						shutdown.wait();						
					}
				} catch (InterruptedException ignored) {}
				log.info("Processor is shutted down!");
				log.info("Stats: " + getStatsString());
			}
		});
		try {
			counter = 0;
			while (!stopImmediately.get()) {				
				if (queue.hasElements()) {
					counter++;
//					if ((counter / 100 * 100) == counter) {
//						logOutInfo();
//					}
					try {
						T element = null;
						synchronized (queue) {
							element = (T) queue.nextElement();
							queue.notify();
						}
						worker.doWork(element);
					} catch (Exception e) {
						log.error("Failure while working under element: ", e);
					}
				} else {
					if(shutdown.get()){
						log.info("Queue is empty. Processing completed!");
						synchronized (shutdown) {
							shutdown.notify();						
						}
						break;
					}
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
	 * Sends signal to stop the QueueProcessor running Thread after working current element.
	 */
	public void stopImmediately(){
		stopImmediately.set(true);
	}
	
	/**
	 * @return true if processing was stopped by calling stopAfterQueueProcessing() or stopImmediately().
	 */
	public boolean isStopped(){
		return stopImmediately.get();
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
		return counter + " elements worked, queue: " + queue.toString() + ", OC:" + overflowCount +  ", WT:" + waitingTime +", TAC:" + throwAwayCount;
	}

	public void logOutInfo() {
		log.info(name + ": " + counter + " elements worked, stat: " + queue.toString() + ", OC:" + overflowCount +  ", WT:" + waitingTime +", TAC:" + throwAwayCount);
	}

	public IQueueFactory<T> getQueueFactory() {
		return queueFactory;
	}

	public void setQueueFactory(IQueueFactory<T> queueFactory) {
		this.queueFactory = queueFactory;
	}

}
