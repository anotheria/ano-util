package net.anotheria.util.mapper;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * VO mapper maps properties between objects.
 * Mapping can be configured with annotations, xml files, mapping by default.
 * <p/>
 * <p/>
 * <P>Mapping automatically performed for all fields with the same property
 * name from the source object into the destination object.
 * <p/>
 * <p/>
 *
 * @author vitaliy
 * @version 1.0
 *          Date: Jan 10, 2010
 *          Time: 9:42:39 PM
 */
public final class ValueObjectMapperUtil {
	/**
	 * Singleton mapper instance.
	 */
	private static final Mapper mapper
			= DozerBeanMapperSingletonWrapper.getInstance();
	private final static Logger logger = Logger.getLogger(ValueObjectMapperUtil.class);

	/**
	 * Default constructor.
	 */
	private ValueObjectMapperUtil() {
	}

	/**
	 * Maps one object to another provided.
	 *
	 * @param source	  given source object
	 * @param destination given destination object
	 */
	public static void map(final Object source, final Object destination) {
		final Map<String, Object> annotatedFields = new HashMap<String, Object>();
		final boolean isMap = source instanceof Map; 
		final Class destinationClass = destination.getClass();
		final Field[] fields = destinationClass.getDeclaredFields();
		for (Field field : fields) {
			final PopulateWith populateWith = field.getAnnotation(PopulateWith.class);
			if (populateWith != null) {
				if(isMap) {
					final Map<String, Object> sourceMap = (Map<String, Object>) source;
					annotatedFields.put(field.getName(), sourceMap.get(populateWith.value()));
				} else {
					annotatedFields.put(field.getName(), getAnnotatedValue(source, populateWith.value()));
				}
			}
		}

		mapper.map(source, destination);
		mapper.map(annotatedFields, destination);
	}

	private static Object getAnnotatedValue(Object source, String key) {
		try {
			final Field sourceField = source.getClass().getDeclaredField(key);
			if (sourceField == null) {
				throw new NoSuchFieldException("Source class field not found by " + key );
			}
			sourceField.setAccessible(true);
			return sourceField.get(source);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (NoSuchFieldException e) {
			logger.error(e);
		}
		return null;
	}

}
