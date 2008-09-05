package net.anotheria.util.queue;

import java.util.concurrent.atomic.AtomicBoolean;

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

	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, IQueueFactory<T> aQueueFactory, Logger log) {
		this(aName, aWorker,  aQueueFactory, DEF_QUEUE_SIZE, DEF_SLEEP_TIME, log);
	}

	public QueuedProcessor(String aName, IQueueWorker<T> aWorker, Logger log) {
		this(aName, aWorker,  null, DEF_QUEUE_SIZE, DEF_SLEEP_TIME, log);
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
				try{
					synchronized(queue){
						queue.wait();
					}
				}catch(InterruptedException ignored){}
			}
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
