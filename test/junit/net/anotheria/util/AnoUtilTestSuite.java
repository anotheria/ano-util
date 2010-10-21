package net.anotheria.util;

import net.anotheria.util.crypt.CryptToolTest;
import net.anotheria.util.crypt.MD5Test;
import net.anotheria.util.network.IPRangeTest;
import net.anotheria.util.network.PlainIPFilterTest;
import net.anotheria.util.resource.ClassPathResourceLoader;
import net.anotheria.util.resource.ClassPathResourceLoaderTest;
import net.anotheria.util.resource.ResourceTest;
import net.anotheria.util.slicer.SlicerTest;
import net.anotheria.util.sorter.SorterTest;
import net.anotheria.util.xml.XMLUtilsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {IdCodeGeneratorTest.class,
		StringUtilsTest.class,
		URLUtilsTest.class,
		BasicComparableTest.class,
		CharacterEntityCoderTest.class,
		DateTest.class,
		DateUtilityTest.class,
		CryptToolTest.class,
		MD5Test.class,
		MD5UtilTest.class,
		ExecutionTimerTest.class,
		NumberUtilsTest.class,
		SlicerTest.class,
		SorterTest.class,
		ArrayUtilsTest.class,
		IPRangeTest.class,
		PlainIPFilterTest.class,
		XMLUtilsTest.class,
		ValueMapperTest.class,
		ClassPathResourceLoaderTest.class,
		ResourceTest.class
		})
		
public class AnoUtilTestSuite {
}
