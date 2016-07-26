package net.anotheria.util.sorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public abstract class AbstractSorter<T extends IComparable> implements Sorter<T> {
  	/**
     * Transfers a vector into array.
     * Vector should include only objects of type IComparable.
     */
 	public static IComparable[] vector2array(Vector<IComparable> src){
  		IComparable[] ret = new IComparable[src.size()];
    	src.copyInto(ret);
     	return ret;
   	}
   	
 	/**
 	 * Transfers a list into array.
 	 * The list should include only objects of type IComparable.
 	 */

 	public static <T extends IComparable> T[] list2array(List<T> src){
 		T[] tmp = (T[])new IComparable[0];
   		return src.toArray(tmp);
   	}

    /**
     * Transfers an array into Vector.
     */
    public static <T extends IComparable> Vector<T> array2vector(T[] src){
        int l = src.length;
 		Vector<T> ret = new Vector<T>(l);
   		for (int i=0; i<l; i++)
     		ret.addElement(src[i]);
       	return ret;
    }
    
    public static <T extends IComparable> List<T> array2list(T src[]){
    	return Arrays.asList(src);
    }
    
 	public List<T> sort(Enumeration<T> source, SortType how){
 		ArrayList<T> list = new ArrayList<T>();
 		while(source.hasMoreElements()){
 			list.add(source.nextElement());
 		}
 		return sort(list,how);
 	}


}
