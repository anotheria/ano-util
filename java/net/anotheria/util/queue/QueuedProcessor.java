package net.anotheria.util.queue;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class QueuedProcessor<T extends Object> extends Thread {
	/**
	 * Name of the processor.
	 */
	private String name;
	/**
	 * Number of processed elements.
	 */
	private int counter;
	/**
	 * The log for this processor.
	 */
	private Logger log;
	/**
	 * The default log to use if no other log is supplied.
	 */
	private static Logger defaultLog;
	/**
	 * The queue in which items are stored.
	 */
	private IQueue<T> queue;
	/**
	 * A worker which processes elements in the queue.
	 */
	private IQueueWorker<T> worker;
	/**
	 * Default time to sleep.
	 */
	public static final long DEF_SLEEP_TIME = 50;
	/**
	 * Default queue size.
	 */
	public static final int DEF_QUEUE_SIZE = 1000;
	/**
	 * Size of the queue used by the producer.
	 */
	private int queueSize;
	/**
	 * Time to sleep after an overflow happened and before retrying.
	 */
	private long sleepTime;
	/**
	 * Number of overflows encountered by this processor.
	 */
	private int overflowCount;
	/**
	 * Number of thrown away queue elements. 'Thrown away' means that a queue overflow happened and the queue was still full after the sleep attempt.
	 * This will result in an UnrecoverableQueueOverflowException.
	 */
	private AtomicInteger throwAwayCount = new AtomicInteger(0);
	/**
	 * Time spent by other thread waiting in the queue. If this time is getting too large it indicates that the processing is far too slow. The time is measured in ms.
	 */
	private AtomicLong waitingTime = new AtomicLong(0);
	
	/**
	 * The default queue factory.
	 */
	private final IQueueFactory<T> DEF_QUEUE_FACTORY = new StandardQueueFactory<T>();
	/**
	 * The factory for creating queues.
	 */
	private IQueueFactory<T> queueFactory;
	private AtomicBoolean stopImmediately;
	
	static {
		defaultLog = Logger.getLogger(QueuedProcessor.class);
	}

	/**
	 * Creates a new QueuedProcessor. This is the standard constructor used by all other constructors.
	 * @param aName name of the processor.
	 * @param aWorker worker for the queued processor.
	 * @param aQueueFactory factory to create the underlying queue.
	 * @param aQueueSize size of the queue.
	 * @param aSleepTime sleep time in case of an overflow.
	 * @param aLog logger for output. 
	 */
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

	/**
	 * Creates a new QueuedProcessor. Uses DEF_QUEUE_SIZE and DEF_SLEEP_TIME.
	 * @param aName name of the processor.
	 * @param aWorker worker for the queued processor.
	 * @param aQueueFactory factory to create the underlying queue.
	 * @param aLog logger for output. 
	 */
	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, IQueueFactory<T> aQueueFactory, Logger aLog) {
		this(aName, aWorker,  aQueueFactory, DEF_QUEUE_SIZE, DEF_SLEEP_TIME, aLog);
	}
	
	/**
	 * Creates a new QueuedProcessor. Uses DEF_QUEUE_FACTORY.
	 * @param aName name of the processor.
	 * @param aWorker worker for the queued processor.
	 * @param aQueueSize size of the queue.
	 * @param aSleepTime sleep time in case of an overflow.
	 * @param aLog logger for output. 
	 */
	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, int aQueueSize, long aSleepTime, Logger aLog) {
		this(aName, aWorker,  null, aQueueSize, aSleepTime, aLog);
	}

	/**
	 * Creates a new QueuedProcessor. Uses DEF_QUEUE_FACTORY and DEF_SLEEP_TIME.
	 * @param aName name of the processor.
	 * @param aWorker worker for the queued processor.
	 * @param aQueueSize size of the queue.
	 * @param aLog logger for output. 
	 */
	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, int aQueueSize, Logger aLog) {
		this(aName, aWorker,  aQueueSize, DEF_SLEEP_TIME, aLog);
	}
	
	/**
	 * Creates a new QueuedProcessor. Uses DEF_QUEUE_FACTORY, DEF_QUEUE_SIZE and DEF_SLEEP_TIME.
	 * @param aName name of the processor.
	 * @param aWorker worker for the queued processor.
	 * @param aLog logger for output. 
	 */
	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, Logger aLog) {
		this(aName, aWorker, DEF_QUEUE_SIZE, DEF_SLEEP_TIME, aLog);
	}

	/**
	 * Creates a new QueuedProcessor. Uses DEF_SLEEP_TIME, DEF_QUEUE_SIZE and default logger.
	 * @param aName name of the processor.
	 * @param aWorker worker for the queued processor.
	 * @param aQueueSize size of the queue.
	 * @param aLog logger for output. 
	 */
	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, IQueueFactory<T> aQueueFactory) {
		this(aName, aWorker, aQueueFactory, defaultLog);
	}
	
	
	/**
	 * Shortest queued processor constructor. Creates a new QueuedProcessor. Uses DEF_SLEEP_TIME, DEF_QUEUE_SIZE, DEF_QUEUE_FACTORY and default logger.
	 * @param aName name of the processor.
	 * @param aWorker worker for the queued processor.
	 */
	public QueuedProcessor(String aName, IQueueWorker<T> aWorker) {
		this(aName, aWorker, null, defaultLog);
	}

	/**
	 * Initializes internal processor structurues.
	 */
	private void init(){
		queue = queueFactory.createQueue(queueSize);
		stopImmediately = new AtomicBoolean(false);
	}

	/**
	 * Resets the queue. Useful for inittesting. Doesn't restart the processor but deletes the underlying queue. The elements stuck in queue will NOT be delivered.
	 */
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
						waitingTime.addAndGet(System.currentTimeMillis() - waitStart);
					}
				} catch(InterruptedException ignored) {
					log.warn("Ignored exception: " + ignored.getMessage(), ignored);
				}
			}
		}
	}

	/**
	 * Default method to add an element to the queue. Calls addToQueueDontWait internally.
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
					waitingTime.addAndGet(System.currentTimeMillis() - waitStart);
				} catch (Exception ignored) {
				}
			}
			try {
				queue.putElement(aElement);
			} catch (QueueOverflowException e2) {
				throwAwayCount.incrementAndGet();
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
							element = queue.nextElement();
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
		return throwAwayCount.intValue();
	}

	public String getStatsString() {
		return "QueuedProcessor "+name+ ": "+counter + " elements worked, queue: " + queue.toString() + ", OC:" + overflowCount +  ", WT:" + waitingTime +", TAC:" + throwAwayCount;
	}

	public void logOutInfo() {
		log.info("QueuedProcessor "+name + ": " + counter + " elements worked, stat: " + queue.toString() + ", OC:" + overflowCount +  ", WT:" + waitingTime +", TAC:" + throwAwayCount);
	}

	public IQueueFactory<T> getQueueFactory() {
		return queueFactory;
	}

	public void setQueueFactory(IQueueFactory<T> queueFactory) {
		this.queueFactory = queueFactory;
	}

}
