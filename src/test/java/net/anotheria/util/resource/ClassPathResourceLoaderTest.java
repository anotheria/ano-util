package net.anotheria.util.resource;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClassPathResourceLoaderTest {

	@Test public void checkNonExistingFile(){
		assertThrows(IllegalArgumentException.class, () -> {
			ResourceLoader loader = new ClassPathResourceLoader();
			assertFalse(loader.isAvailable("foo"));
			loader.getLastChangeTimestamp("foo");
		});
	}

	@Test public void loadNonExistingFile(){
		assertThrows(IllegalArgumentException.class, () -> {
			ResourceLoader loader = new ClassPathResourceLoader();
			assertFalse(loader.isAvailable("foo"));
			loader.getContent("foo");
		});
	}

}
