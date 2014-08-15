package net.anotheria.util.resource;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

public class ResourceTest {
	
	@Before public void reset(){
		FixtureLoader.reset();
	}
	
	private static void changeFixtureContent(String content){
		FixtureLoader.setContent(content);
	}
	
	@Test (expected=IllegalArgumentException.class) public void testContentNonExistance(){
		Resource res = new Resource("nonExistingResource", new FixtureLoader(), false);
		res.getContent();
		fail("IllegalArgumentException must be throwed!");
	}
	
	@Test (expected=IllegalArgumentException.class) public void testLastChangeTimestampNonExistance(){
		Resource res = new Resource("nonExistingResource", new FixtureLoader(), false);
		res.getLastChangeTimestamp();
		fail("IllegalArgumentException must be throwed!");
	}
	
	@Test public void testContent(){
		FixtureLoader loader = new FixtureLoader();
		Resource res = new Resource(FixtureLoader.EXISTING_RESOURCE, loader, false);
		String content = res.getContent();
		assertEquals(FixtureLoader.ORIGINAL_CONTENT, content);
		
		String dummyContent = "dummy content";
		changeFixtureContent(dummyContent);
		//Resource wasn't changed: watching is disabled
		content = res.getContent();
		assertEquals(FixtureLoader.ORIGINAL_CONTENT, content);
	}
	
	@Test public void testLastChangeTimestamp() throws InterruptedException{
		FixtureLoader loader = new FixtureLoader();
		Resource res = new Resource(FixtureLoader.EXISTING_RESOURCE, loader, false);
		long lastChange = res.getLastChangeTimestamp();
		assertTrue(lastChange <= System.currentTimeMillis());
		
		changeFixtureContent("dummyContent");
		//Resource wasn't changed: watching is disabled
		assertEquals(lastChange, res.getLastChangeTimestamp());
	}
	
	@Test public void testWatchign() throws InterruptedException{
		FixtureLoader loader = new FixtureLoader();
		Resource res = new Resource(FixtureLoader.EXISTING_RESOURCE, loader, true);
		
		final AtomicBoolean notified = new AtomicBoolean(false);
		final CountDownLatch resourceUpdated = new CountDownLatch(1);
		
		res.addListener(new ResourceListener() {
			@Override
			public void resourceUpdated(Resource target) {
				resourceUpdated.countDown();
				notified.set(true);
			}
		});
		
		String newContent = "dummyContent";
		changeFixtureContent(newContent);
		resourceUpdated.await(12, TimeUnit.SECONDS);		
		//Resource is changed!
		//Ensure that resourceUpdated was notified
		assertTrue("Resource Listener wasn't notified about changes!", notified.get());
		assertEquals("Content wasn't updated!", newContent, res.getContent());
		assertTrue("LastUpdate timestamp wasn't updated!", res.getLastChangeTimestamp() > FixtureLoader.ORIGINAL_LAST_UPDATE);
		
	}
	
}
