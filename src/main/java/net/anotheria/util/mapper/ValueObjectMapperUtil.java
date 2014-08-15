package net.anotheria.util.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			= new DozerBeanMapper();

	/**
	 * Mapper LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ValueObjectMapperUtil.class);

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

		final boolean isMap = source instanceof Map;
		final Class destinationClass = destination.getClass();
		@SuppressWarnings({"unchecked"})
		final PopulateMe populateMe = (PopulateMe) destinationClass.getAnnotation(PopulateMe.class);
		boolean populateAll = false;
		if (populateMe != null) {
			populateAll = populateMe.all();
			if (!populateAll) {
				return;
			}
		}
		final Map<String, Object> populateAllFields = new HashMap<String, Object>();
		final Field[] fields = destinationClass.getDeclaredFields();
		for (Field field : fields) {
			final PopulateWith populateWith = field.getAnnotation(PopulateWith.class);
			if (populateWith != null) {
				if (isMap) {
					@SuppressWarnings({"unchecked"})
					final Map<String, Object> sourceMap = (Map<String, Object>) source;
					sourceMap.put(field.getName(), sourceMap.get(populateWith.value()));
				} else {
					setFieldValue(destination, field, getFieldValue(source, populateWith.value()));
				}
			}
			final DontPopulate dontPopulate = field.getAnnotation(DontPopulate.class);
			if (populateAll && dontPopulate == null) {
				populateAllFields.put(field.getName(), getFieldValue(source, field.getName()));
			}
		}
		if (populateAll) {
			mapDozer(populateAllFields, destination);
		} else {
			mapDozer(source, destination);
		}
	}

	/**
	 * Internal mapping facility using Dozer.
	 *
	 * @param source	  source object
	 * @param destination destination object
	 */
	private static void mapDozer(final Object source, final Object destination) {
		try {
			mapper.map(source, destination);
		} catch (MappingException e) {
			LOGGER.debug("mapDozer", e);
		}
	}

	/**
	 * Return field value by field name.
	 *
	 * @param source given mapped object
	 * @param key	field name
	 * @return field value
	 */
	private static Object getFieldValue(final Object source, final String key) {
		try {
			final Field sourceField = source.getClass().getDeclaredField(key);
			if (sourceField == null) {
				throw new NoSuchFieldException("Source class field not found by " + key);
			}
			sourceField.setAccessible(true);
			return sourceField.get(source);
		} catch (IllegalAccessException e) {
			LOGGER.error("getFieldField", e);
		} catch (NoSuchFieldException e) {
			LOGGER.error("getFieldField", e);
		}
		return null;
	}

	/**
	 * Set filed value for destination object.
	 *
	 * @param destination destination object
	 * @param field	   given field
	 * @param value	   field value
	 */
	private static void setFieldValue(final Object destination, final Field field, final Object value) {
		try {
			field.setAccessible(true);
			field.set(destination, value);
		} catch (IllegalAccessException e) {
			LOGGER.debug("setFieldValue", e);
		}
	}
}