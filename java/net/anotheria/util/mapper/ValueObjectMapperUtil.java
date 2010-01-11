package net.anotheria.util.mapper;

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
				System.out.println("hello=" );
				try {
					final Object fieldValue = field.get(source);
					System.out.println("fieldValue="  +fieldValue);
					final Field destinationField = destinationClass.getDeclaredField(populateWith.value());
					System.out.println("destinationField="  +destinationField);
					destinationField.setAccessible(true);
					final String converterId = populateWith.converterId();
					if (!"".equals(converterId)
						&& (mapper instanceof DozerBeanMapper
								&& ((DozerBeanMapper) mapper).getCustomConvertersWithId() != null)) {
							final CustomConverter converter = ((DozerBeanMapper) mapper).getCustomConvertersWithId().get(converterId);
						System.out.println("converter="  +converter);
							converter.convert(fieldValue, destinationField.get(destination), field.getType(), destinationField.getType());
						annotatedFields.put(destinationField.getName(), destinationField.get(destination));
					} else {
						annotatedFields.put(destinationField.getName(), fieldValue);
					}

				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
		}

		mapper.map(annotatedFields, destination);
		mapper.map(source, destination);
	}

	/**
	 * Used to register custom converter and refer by converter id.
	 *
	 * @param converterId reference id
	 * @param converter dozer custom converter
	 */
	public static void registerCustomConverter(final String converterId, final CustomConverter converter) {
		if (mapper instanceof DozerBeanMapper) {
			Map<String, CustomConverter> converterMap = ((DozerBeanMapper) mapper).getCustomConvertersWithId();
			if (converterMap == null) {
				converterMap = new HashMap<String, CustomConverter>(1);
			}
			converterMap.put(converterId, converter);
			((DozerBeanMapper) mapper).setCustomConvertersWithId(converterMap);
		}
	}
}
