/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/xml/XMLList.java,v $
  $Author: lrosenberg $
  $Date: 2006/04/26 09:56:43 $
  $Revision: 1.2 $


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

import java.util.Vector;
import java.util.Enumeration;

public class XMLList implements IXMLable{

	private String name;

	private Vector data;

	public XMLList(String name, Vector data){
	    this.name = name;
		this.data = data;
	}

	public XMLList(Vector data){
		this(TAG_LIST, data);
	}

	public XMLList(Enumeration _enum){
	    this(enumeration2vector(_enum));
	}

	public XMLList(String name, Enumeration _enum){
	    this(name, enumeration2vector(_enum));
	}

	private static Vector enumeration2vector(Enumeration _enum){
	    Vector v = new Vector();
		while(_enum.hasMoreElements())
			v.addElement(_enum.nextElement());
		return v;
	}

	public void setName(String name){
	    this.name = name;
	}

	public String getName(){
	    return name;
	}

	public String toXML(int tabs){
	    String ret = "";
		String tab = XMLHelper.makeTabs(tabs);
		ret += tab + XMLHelper.openTag(getName()+" count=\""+data.size()+"\"")+CRLF;
		for (int i=0; i<data.size(); i++){
		    Object o = data.elementAt(i);
			if (o instanceof IXMLable){
				ret += ((IXMLable)o).toXML(tabs+1);
			}else{
				ret += tab + TAB + XMLHelper.openTag(TAG_ITEM) + o.toString() + XMLHelper.closeTag(TAG_ITEM);
			}
		}
		ret += tab + XMLHelper.closeTag(getName());
		return ret;
	}

}
/* ------------------------------------------------------------------------- *
  $Log: XMLList.java,v $
  Revision 1.2  2006/04/26 09:56:43  lrosenberg
  1.5 compliance, renamed enum variable into _enum

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
