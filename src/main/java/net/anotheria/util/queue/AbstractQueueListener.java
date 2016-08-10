package net.anotheria.util.queue;

/**
 * <p>Abstract AbstractQueueListener class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
abstract public class AbstractQueueListener implements IQueueListener{

	/** {@inheritDoc} */
	@Override
	public void queueElementAdded() {
	}

	/** {@inheritDoc} */
	@Override
	public void queueEmpty() {
	}

	/** {@inheritDoc} */
	@Override
	public void queueFull() {
	}

}
