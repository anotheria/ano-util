package net.anotheria.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={IdCodeGeneratorTest.class, StringUtilsTest.class, BasicComparableTest.class, CharacterEntityCoderTest.class, DateTest.class} )
public class AnoUtilTestSuite {
	
}
