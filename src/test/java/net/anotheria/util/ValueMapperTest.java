package net.anotheria.util;

import net.anotheria.util.mapper.DestinationClass;
import net.anotheria.util.mapper.DestinationExcludedClass;
import net.anotheria.util.mapper.SourceClass;
import net.anotheria.util.mapper.ValueObjectMapperUtil;
import org.dozer.CustomConverter;
import org.dozer.CustomFieldMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests for Model Object mapper.
 * <p/>
 *
 * @author vitaliy
 * @version 1.0
 *          Date: Jan 9, 2010
 *          Time: 4:03:23 PM
 */
public class ValueMapperTest {

	@Test
	public void shouldMapSameField() {
		//given
		SourceClass source = new SourceClass(12);
		HashMap<String, Integer> intMap = new HashMap<String, Integer>(2);
		intMap.put("testVal", 1);
		intMap.put("testVal2", 2);
		source.setIntMap(intMap);
		//when
		Mapper mapper = new DozerBeanMapper();
		DestinationClass destination = mapper.map(source, DestinationClass.class);

		//then
		Assert.assertEquals(String.valueOf(source.getStep()), destination.getStep());
		Assert.assertEquals(source.getIntMap(), destination.getIntMap());
	}

	@Test
	public void shouldMapHashMapValueToField() {
		//given
		Map<String, Object> source = new HashMap<String, Object>();
		source.put("step", 2);
		HashMap<String, Integer> intMap = new HashMap<String, Integer>(2);
		intMap.put("testVal", 1);
		intMap.put("testVal2", 2);

		source.put("intMap", intMap);
		Mapper mapper = new DozerBeanMapper();

		//when
		DestinationClass destination = mapper.map(source, DestinationClass.class);

		//then
		Assert.assertEquals(String.valueOf(source.get("step")), destination.getStep());
		Assert.assertEquals(source.get("intMap"), destination.getIntMap());
	}



	public void shouldMapConfigurableField() {
		//given
		SourceClass source = new SourceClass(12);
		HashMap<String, Integer> intMap = new HashMap<String, Integer>(2);
		intMap.put("testVal", 1);
		intMap.put("testVal2", 2);
		source.setIntMap(intMap);
		DozerBeanMapper mapper = new DozerBeanMapper(Arrays.asList("testDozerBeanMapping.xml"));
		Map<String, CustomConverter> converterMap = new HashMap<String, CustomConverter>();
		converterMap.put("testConverter", new DozerConverter<String, Integer>(String.class, Integer.class) {

			@Override
			public Integer convertTo(String s, Integer integer) {
				integer = Integer.valueOf(s) - 5;
				return integer;
			}

			@Override
			public String convertFrom(Integer integer, String s) {
				s = String.valueOf(integer + 5);
				return s;
			}
		});

		mapper.setCustomConvertersWithId(converterMap);

		//when
		DestinationClass destination = mapper.map(source, DestinationClass.class);

		//then
		Assert.assertEquals(source.getStep(), destination.getStepConfigured().intValue());
	}

	@Test
	public void shouldMapCustomField() {
		//given
		SourceClass source = new SourceClass(12);
		HashMap<String, Integer> intMap = new HashMap<String, Integer>(2);
		intMap.put("testVal", 1);
		intMap.put("testVal2", 2);
		source.setIntMap(intMap);
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setCustomFieldMapper(new CustomFieldMapper() {
			public boolean mapField(Object source, Object destination, Object fieldValue, ClassMap classMap, FieldMap fieldMap) {
				SourceClass aClass = (SourceClass) source;
				DestinationClass bClass = (DestinationClass) destination;
				if (Integer.valueOf(aClass.getStep()).equals(fieldValue)) {
					bClass.setStepConfigured((Integer) fieldValue);
					return true;
				}
				return false;
			}
		});


		//when
		DestinationClass destination = mapper.map(source, DestinationClass.class);

		//then
		Assert.assertEquals(Integer.valueOf(source.getStep()), destination.getStepConfigured());
	}

	
	public void shouldMapCustomFieldConverter() {
		//given
		SourceClass source = new SourceClass(12);
		HashMap<String, Integer> intMap = new HashMap<String, Integer>(2);
		intMap.put("testVal", 1);
		intMap.put("testVal2", 2);
		source.setIntMap(intMap);
		DozerBeanMapper mapper = new DozerBeanMapper(Arrays.asList("testDozerBeanMapping.xml"));

		Map<String, CustomConverter> converterMap = new HashMap<String, CustomConverter>();
		converterMap.put("testConverter", new DozerConverter<String, Integer>(String.class, Integer.class) {

			@Override
			public Integer convertTo(String s, Integer integer) {
				integer = Integer.valueOf(s) - 5;
				return integer;
			}

			@Override
			public String convertFrom(Integer integer, String s) {
				s = String.valueOf(integer + 5);
				return s;
			}
		});

		mapper.setCustomConvertersWithId(converterMap);		 

		//when
		DestinationClass destination = mapper.map(source, DestinationClass.class);

		//then
		Assert.assertEquals(String.valueOf(source.getStep() + 5), destination.getStepConverted());
		Assert.assertEquals(source.getIntMap(), destination.getIntMap());
	}

	@Test
	public void shouldMapAnnotatedField() {
		//given
		SourceClass source = new SourceClass(12);
		HashMap<String, Integer> intMap = new HashMap<String, Integer>(2);
		intMap.put("testVal", 1);
		intMap.put("testVal2", 2);
		source.setIntMap(intMap);
		DestinationClass destination = new DestinationClass();

		//when
		ValueObjectMapperUtil.map(source, destination);

		//then
		Assert.assertEquals(Integer.valueOf(source.getStep()), destination.getStepAnnotated());
	}

	@Test
	public void shouldNotMapAnnotatedClass() {
		//given
		SourceClass source = new SourceClass(12);
		HashMap<String, Integer> intMap = new HashMap<String, Integer>(2);
		intMap.put("testVal", 1);
		intMap.put("testVal2", 2);
		source.setIntMap(intMap);
		DestinationExcludedClass destination = new DestinationExcludedClass();

		//when
		ValueObjectMapperUtil.map(source, destination);

		//then
		Assert.assertFalse(Integer.valueOf(source.getStep()).equals(destination.getStepAnnotated()));
	}

	@Test
	public void shouldPopulateAllExceptDontFields() {
		//given
		SourceClass source = new SourceClass(12);
		HashMap<String, Integer> intMap = new HashMap<String, Integer>(2);
		intMap.put("testVal", 1);
		intMap.put("testVal2", 2);
		source.setIntMap(intMap);
		DestinationExcludedClass destination = new DestinationExcludedClass();

		//when
		ValueObjectMapperUtil.map(source, destination);

		//then
		Assert.assertFalse(source.getIntMap().equals(destination.getIntMap()));
		Assert.assertNull(destination.getIntMap());
	}


	@Test
	public void shouldMapDictionaryToAnnotatedField() {
		//given
		HashMap<String, Integer> sourceMap = new HashMap<String, Integer>(2);
		sourceMap.put("step", 1);
		sourceMap.put("stepToConvert", 5);
		DestinationClass destination = new DestinationClass();

		//when
		ValueObjectMapperUtil.map(sourceMap, destination);

		//then
		Assert.assertEquals(sourceMap.get("stepToConvert"), destination.getStepConfigured());
	}

}
