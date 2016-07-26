package net.anotheria.util.resource;


import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;

public class ClassPathResourceLoaderTest {

	@Test (expected=IllegalArgumentException.class) public void checkNonExistingFile(){
		ResourceLoader loader = new ClassPathResourceLoader();
		assertFalse(loader.isAvailable("foo"));
		loader.getLastChangeTimestamp("foo");
		fail("An exception should have been thrown.");
	}

	@Test (expected=IllegalArgumentException.class) public void loadNonExistingFile(){
		ResourceLoader loader = new ClassPathResourceLoader();
		assertFalse(loader.isAvailable("foo"));
		loader.getContent("foo");
		fail("An exception should have been thrown.");
	}

}
