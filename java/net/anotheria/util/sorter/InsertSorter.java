/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/sorter/InsertSorter.java,v $
  $Author: lrosenberg $
  $Date: 2007/08/21 09:31:25 $
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
package net.anotheria.util.sorter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class InsertSorter<T extends IComparable> extends AbstractSorter<T> {

	public List<T> sort(List<T> toSort, SortType how){
      	ListEntry<T> list = null;
		int method = how.getSortBy();
		for (T c : toSort){
			if (list==null){
       			list = new ListEntry<T>(c);
          		continue;
         	}
          	ListEntry<T> le = new ListEntry<T>(c);
          	if (list.value.compareTo(le.value,method)>=0){
           		le.next = list;
             	list = le;
            }else{
            	list.insert(le, method);
            }



        }/*...while*/

        ArrayList<T> v = new ArrayList<T>(list.length());
        do{
        	v.add(list.value);
         	list = list.next;
        }while(list!=null);

      	return v;


 	}

  	private static class ListEntry<T extends IComparable> {
  		public T value;

  	 	public ListEntry<T> next;

  	  	public ListEntry(){
  	   		next = null;
  	     	value = null;
  	    }

  	    public ListEntry(T aValue){
  	    	this.value = aValue;
  	    }

  	    public int length(){
  	    	if (next==null)
  	     		return 1;
  	       	return 1+next.length();
  	    }

  		public void insert(ListEntry<T> aNewEntry, int aMethod){
  	 		if (next == null){
  	   			next = aNewEntry;
  	      		return;
  	        }
  	        if (next.value.compareTo(aNewEntry.value, aMethod)>=0){
  	        	aNewEntry.next = next;
  	         	next = aNewEntry;
  	          	return;
  	        }
  	        next.insert(aNewEntry, aMethod);

  	  	}
  	}
}


