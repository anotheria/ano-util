package net.anotheria.util.queue;

public interface IQueueFactory<T> {
	public IQueue<T> createQueue(int size);
}
