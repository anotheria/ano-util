/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/sorter/QuickSorter.java,v $
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
import java.util.Iterator;
import java.util.List;


/**
 *  just an implementation of the well known QuickSort Algorithm
 *
 *@author     Chris Hoffmann
 *@created    October 21, 2000
 */
public class QuickSorter<T extends IComparable> extends AbstractSorter<T> {



	/**
	 *  sorts an Enumeration with the QuickSort Algorithm and returns a sorted Vector
	 *
	 *@param  source  the Enumeration you want to sort
	 *@param  sType   how you want to sort
	 *@return         a sorted Vector containing the Objects of the Enumeration
	 */
	public List<T> sort(Enumeration<T> source, SortType sType) {
		List<T> ret = new ArrayList<T>();
		while (source.hasMoreElements()) {
			ret.add(source.nextElement());
		}
		return sort(ret, sType);
	}


	/**
	 *  sorts a Vector with the QuickSort Algorithm
	 *
	 *@param  source  the vector you want to sort
	 *@param  sType   SortType  ...  how you wanna sort the Vector
	 *@return         the sorted Vector
	 */
	public List<T> sort(List<T> source, SortType sType) {
		boolean sortOrder = sType.sortOrder;
		int sortAfter = sType.getSortBy();
        SortType tmp = new SortType(sType.getSortBy(),!sType.sortOrder);
        if(isSorted(source,sType)){
		    return source;
		}
        if(isSorted(source,tmp)){
            return (upsideDown(source));
		}
		boolean wanted = sortOrder == SortType.ASC;
		sort(source, 0, source.size() - 1, wanted, sortAfter);
		return source;
	}

	private void sort(List<T> source, int start, int end, boolean wanted, int sortAfter) {
		int mid;
		if (start < end) {
			mid = partition(source, start, end, wanted, sortAfter);
			sort(source, start, mid - 1, wanted, sortAfter);
			sort(source, mid + 1, end, wanted, sortAfter);
		}
	}

    private List<T> upsideDown(List<T> src){
        if(src == null)
            return null;
        List<T> ret = new ArrayList<T>(src.size());
        for(int i = src.size()-1; i >= 0; i--){
            ret.add(src.get(i));
		}
        return ret;
    }

    private boolean isSorted(List<T> src, SortType type){
        boolean wanted = (type.sortOrder == SortType.ASC);
        int sortAfter = type.getSortBy();
        Iterator<T> elements = src.iterator();
        T comp, comp2;
        if(elements.hasNext())
            comp = elements.next();
        else
            return true;
        while(elements.hasNext()){
            comp2 = elements.next();
            if(wanted ? compare(comp,comp2,sortAfter) > 0 : compare(comp,comp2,sortAfter) < 0)
                return false;
            comp = comp2;
        }
        return true;
    }

	private int partition(List<T> source, int start, int end, boolean wanted, int sortAfter) {
		int left;
		int right;
		T partElement = source.get(end);
		left = start - 1;
		right = end;
		for (; ; ) {
			while (wanted ? compare(partElement, source.get(++left), sortAfter) > 0 : compare(partElement, source.get(++left), sortAfter) < 0) {
				if (left == end) {
					break;
				}
			}
			while (wanted ? compare(partElement, source.get(--right), sortAfter) < 0 : compare(partElement, source.get(--right), sortAfter) > 0) {
				if (right == start) {
					break;
				}
			}
			if (left >= right) {
				break;
			}
			swap(source, left, right);
		}
		swap(source, left, end);

		return left;
	}

	private void swap(List<T> source, int i, int j) {
		T tmp = source.get(i);
		source.set(i, source.get(j));
		source.set(j, tmp);
	}


	private int compare(T a, T b, int sortAfter) {
		return a.compareTo(b, sortAfter);
	}
}