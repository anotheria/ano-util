package net.anotheria.util;

import net.anotheria.util.crypt.CryptToolTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={IdCodeGeneratorTest.class, StringUtilsTest.class, BasicComparableTest.class, CharacterEntityCoderTest.class, DateTest.class, DateUtilityTest.class, CryptToolTest.class, MD5UtilTest.class, ExecutionTimerTest.class} )
public class AnoUtilTestSuite {
	
}
