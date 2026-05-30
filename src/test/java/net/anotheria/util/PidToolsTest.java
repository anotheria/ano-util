package net.anotheria.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PidToolsTest {
	@Test public void testPid(){
		assertTrue(PidTools.getPid()>0);
	}
}
