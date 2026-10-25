package net.anotheria.util.content.template;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.URLClassLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Jackson is an optional dependency of ano-util, only {loop:...} tags need it.
 * Ensures that TemplateUtility, which registers the loop processor by default, works without jackson on the classpath.
 */
public class TemplateUtilityWithoutJacksonTest {

	@Test
	public void testReplaceVariablesWithoutJackson() throws Exception {
		URL classes = TemplateUtility.class.getProtectionDomain().getCodeSource().getLocation();
		try (URLClassLoader loader = new WithoutJacksonClassLoader(classes, getClass().getClassLoader())) {
			Class<?> contextClass = loader.loadClass(TemplateReplacementContext.class.getName());
			Object context = contextClass.getConstructor().newInstance();
			Object result = loader.loadClass(TemplateUtility.class.getName())
					.getMethod("replaceVariables", contextClass, String.class)
					.invoke(null, context, "static text");
			assertEquals("static text", result);
		}
	}

	/**
	 * Loads ano-util classes itself, hides jackson and delegates everything else to the parent.
	 */
	private static final class WithoutJacksonClassLoader extends URLClassLoader {
		private WithoutJacksonClassLoader(URL classes, ClassLoader parent) {
			super(new URL[]{classes}, parent);
		}

		@Override
		protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
			if (name.startsWith("com.fasterxml.jackson."))
				throw new ClassNotFoundException(name);
			if (!name.startsWith("net.anotheria.util."))
				return super.loadClass(name, resolve);
			synchronized (getClassLoadingLock(name)) {
				Class<?> loaded = findLoadedClass(name);
				if (loaded == null)
					loaded = findClass(name);
				if (resolve)
					resolveClass(loaded);
				return loaded;
			}
		}
	}
}
