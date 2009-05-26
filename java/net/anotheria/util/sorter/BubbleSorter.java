/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/sorter/BubbleSorter.java,v $
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


/**
 * An implementation of a bubble sorter for EQS FI Sorter Pattern.
 */
public class BubbleSorter<T extends IComparable> extends AbstractSorter<T> {

    public List<T> sort(Enumeration<T> source, SortType method){
           ArrayList<T> toSort = new ArrayList<T>();
           while(source.hasMoreElements())
                toSort.add(source.nextElement());
           return sort(toSort, method);
    }

	public List<T> sort(List<T> source, SortType method){
     	boolean sortOrder = method.getSortOrder();
      	int sortAfter = method.getSortBy();
       	boolean changed;
        if (source==null || source.size()==0)
        	return source;
        T[] data = list2array(source);

        boolean wanted = sortOrder == SortType.ASC ;//? true : false;
        int l = java.lang.reflect.Array.getLength(data);
        do{
            changed = false;
            for (int i=0; i<l-1; i++){
            	if ( wanted? data[i].compareTo(data[i+1], sortAfter)>0 :data[i].compareTo(data[i+1], sortAfter)<0 ){
             		swap(data, i, i+1);
               		changed = true;
              	}
            }
        }while(changed);
 		return array2list(data);
  	}

   	private void swap(Object[] data, int first, int second){
    	Object tmp = data[first];
     	data[first] = data[second];
      	data[second] = tmp;
    }
}

