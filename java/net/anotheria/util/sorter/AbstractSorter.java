package net.anotheria.util.sorter;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public abstract class AbstractSorter<T extends IComparable> implements Sorter<T>{
  	/**
     * Transfers a vector into array.
     * Vector should include only objects of type IComparable.
     */
 	public static IComparable[] vector2array(Vector src){
  		IComparable[] ret = new IComparable[src.size()];
    	src.copyInto(ret);
     	return ret;
   	}
   	
 	/**
 	 * Transfers a list into array.
 	 * The list should include only objects of type IComparable.
 	 */
 	public static<T extends IComparable> IComparable[] list2array(List<T> src){
 		IComparable[] tmp = new IComparable[1];
   		return src.toArray(tmp);
   	}

    /**
     * Transfers an array into Vector.
     */
    public static Vector array2vector(Object[] src){
        int l = src.length;
 		Vector ret = new Vector(l);
   		for (int i=0; i<l; i++)
     		ret.addElement(src[i]);
       	return ret;
    }
    
    public static List array2list(IComparable src[]){
    	return Arrays.asList(src);
    }

}
