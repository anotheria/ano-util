package net.anotheria.util.queue;

/**
 * TODO Please remind lrosenberg to comment this class.
 * @author lrosenberg
 * Created on 08.11.2004
 */
public interface ITimestampedQueue extends IQueue{
	
	public long getLastPutTimestamp();
	
	public long getLastGetTimestamp();

}
