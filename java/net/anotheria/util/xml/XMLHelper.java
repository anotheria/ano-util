/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/xml/XMLHelper.java,v $
  $Author: lro $
  $Date: 2004/02/06 21:41:49 $
  $Revision: 1.1 $


  Copyright 2002 by BeagleSoft GmbH, Berlin, Germany
  All rights reserved.

  This software is the confidential and proprietary information
  of BeagleSoft GmbH. ("Confidential Information").  You
  shall not disclose such Confidential Information and shall use
  it only in accordance with the terms of the license agreement
  you entered into with BeagleSoft GmbH.
  See www.beaglesoft.biz for details.
** ------------------------------------------------------------------------- */
package net.anotheria.util.xml;

import java.io.*;

public class XMLHelper implements IXMLBasic {
	public static String openTag(String tag){
	    return TAG_START + tag + TAG_END;
	}

	public static String closeTag(String tag){
	    return "" + TAG_START + TAG_CLOSE + tag + TAG_END+CRLF;
	}

	public static String makeTabs(int count){
	    String ret = "";
		for(int i=0;i<count;i++)
			ret += TAB;
		return ret;
	}

	public static void save2file(String name, IXMLable object) throws IOException{
	    save2file(new File(name), object);
	}

	public static void save2file(File file, IXMLable object) throws IOException{
	    FileOutputStream fOut = new FileOutputStream(file);
		fOut.write(("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"+CRLF).getBytes());
		fOut.write(object.toXML(0).getBytes());
		fOut.close();
	}

}
/* ------------------------------------------------------------------------- *
  $Log: XMLHelper.java,v $
  Revision 1.1  2004/02/06 21:41:49  lro
  *** empty log message ***

  Revision 1.1.1.1  2004/02/04 16:31:15  lro
  initial checkin

  Revision 1.1  2004/01/30 22:06:41  cvs
  *** empty log message ***

  Revision 1.1.1.1  2002/02/05 16:26:21  another
  no message

  Revision 1.1.1.1  2001/10/22 10:34:14  lro
  no message

  Revision 1.1  2001/10/21 21:03:21  lro
  no message



** ------------------------------------------------------------------------- */
