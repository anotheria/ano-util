package net.anotheria.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class URLUtilsTest {

	@Test public void escape(){
		String url = "http://anotheria.net/file/foo bar.jpg";
		System.out.println(URLUtils.encode(url));
		assertEquals("http://anotheria.net/file/foo%20bar.jpg", URLUtils.encode(url));
	}
	
}
