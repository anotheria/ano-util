package net.anotheria.util.queue;

public interface IQueueWorker<T extends Object>{
	
	void doWork(T workingElement) throws Exception;
	
}
