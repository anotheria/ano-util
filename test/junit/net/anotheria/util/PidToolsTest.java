package net.anotheria.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class PidToolsTest {
	@Test public void testPid(){
		assertTrue(PidTools.getPid()>0);
	}
}
