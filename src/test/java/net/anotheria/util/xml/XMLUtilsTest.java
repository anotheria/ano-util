package net.anotheria.util.xml;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.junit.Assert.assertEquals;

/**
 * THIS TEST IS YET STARTED AND SHOUT BE WRITTEN FURTHER!
 * @author another
 *
 */

public class XMLUtilsTest {
	@Test public void testSimpleTree() throws IOException, ParserConfigurationException, SAXException{
		XMLTree tree = new XMLTree();
		
		XMLNode first = new XMLNode("FIRST");
		first.setContent("FIRST CONTENT");
		
		XMLNode second = new XMLNode("SECOND");
		second.addAttribute(new XMLAttribute("AAA", 111));
		second.addAttribute(new XMLAttribute("BBB", 222));
		
		XMLNode third = new XMLNode("THIRD");
		third.addAttribute(new XMLAttribute("XXX", 111));
		third.addAttribute(new XMLAttribute("YYY", 222));

		tree.setRoot(first);
		tree.getRoot().addChildNode(second);
		tree.getRoot().addChildNode(third);
		second.addChildNode(third);
		
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(10000);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		tree.write(writer);
		writer.flush();
		String content = new String(out.toByteArray());
		System.out.println("content:"+content);
		
		//now parse resulting xml
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(out.toByteArray()));
		NodeList rootlist = doc.getChildNodes();
		System.out.println(rootlist);
		Node root = rootlist.item(0);
		System.out.println(root);
		assertEquals("FIRST", root.getNodeName());
		System.out.println(root.getTextContent());
		assertEquals("FIRST CONTENT", root.getTextContent().trim());//TODO FIXME
	}
}
