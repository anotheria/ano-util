package net.anotheria.util.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceTest {

	@BeforeEach public void reset(){
		FixtureLoader.reset();
	}

	private static void changeFixtureContent(String content){
		FixtureLoader.setContent(content);
	}

	@Test public void testContentNonExistance(){
		assertThrows(IllegalArgumentException.class, () -> {
			Resource res = new Resource("nonExistingResource", new FixtureLoader(), false);
			res.getContent();
		});
	}

	@Test public void testLastChangeTimestampNonExistance(){
		assertThrows(IllegalArgumentException.class, () -> {
			Resource res = new Resource("nonExistingResource", new FixtureLoader(), false);
			res.getLastChangeTimestamp();
		});
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

	@Disabled
	@Test public void testWatching() throws InterruptedException{
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
		resourceUpdated.await(18, TimeUnit.SECONDS);
		//Resource is changed!
		//Ensure that resourceUpdated was notified
		assertTrue(notified.get(), "Resource Listener wasn't notified about changes!");
		assertEquals(newContent, res.getContent(), "Content wasn't updated!");
		assertTrue(res.getLastChangeTimestamp() > FixtureLoader.ORIGINAL_LAST_UPDATE, "LastUpdate timestamp wasn't updated!");

	}

}
