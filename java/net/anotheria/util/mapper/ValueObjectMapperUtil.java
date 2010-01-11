package net.anotheria.util.mapper;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * VO mapper maps properties between objects.
 * Mapping can be configured with annotations, xml files, mapping by default.
 *
 * <p/>
 * <P>Mapping automatically performed for all fields with the same property
 *  name from the source object into the destination object.
 * 
 * <p/>
 *
 * @author vitaliy
 * @version 1.0
 *          Date: Jan 10, 2010
 *          Time: 9:42:39 PM
 */
public final class ValueObjectMapperUtil {
	/**
	 *  Singleton mapper instance.
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
	 * @param source given source object
	 * @param destination given destination object
	 */
	public static void map(final Object source, final Object destination) {
		final Map<String, Object> annotatedFields = new HashMap<String, Object>();
		final Class sourceClass = source.getClass();
		final Class destinationClass = destination.getClass();
		final Field[] fields = sourceClass.getDeclaredFields();
		for (Field field : fields) {
			final PopulateWith populateWith = field.getAnnotation(PopulateWith.class);
			if (populateWith != null) {
				field.setAccessible(true);				
				try {
					final Object fieldValue = field.get(source);
					final Field destinationField = destinationClass.getDeclaredField(populateWith.value());
					destinationField.setAccessible(true);
					annotatedFields.put(destinationField.getName(), fieldValue);
				} catch (IllegalAccessException e) {
					logger.error(e);
				} catch (NoSuchFieldException e) {
					logger.error(e);
				}
			}
		}

		mapper.map(annotatedFields, destination);
		mapper.map(source, destination);
	}
	
}
