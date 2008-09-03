package net.anotheria.util.queue;

public interface IWorker <T extends Object>{
	
	void doWork(T workingElement) throws Exception;
	
}
