package net.anotheria.util.queue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={SimpleQueueTest.class, QueuedProcessorTest.class, ConcurrentQueueTest.class} )
public class QueueTestSuite {

}
