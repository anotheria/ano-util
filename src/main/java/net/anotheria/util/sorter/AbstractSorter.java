package net.anotheria.util.sorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * <p>Abstract AbstractSorter class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public abstract class AbstractSorter<T extends IComparable> implements Sorter<T> {
 	/**
 	 * Transfers a vector into array.
 	 * Vector should include only objects of type IComparable.
 	 *
 	 * @param src a {@link java.util.Collection} object.
 	 * @return an array of {@link net.anotheria.util.sorter.IComparable} objects.
 	 */
 	public static IComparable[] vector2array(Collection<IComparable> src){
  		IComparable[] ret = new IComparable[src.size()];
		src.toArray(ret);
     	return ret;
   	}
   	
 	/**
 	 * Transfers a list into array.
 	 * The list should include only objects of type IComparable.
 	 *
 	 * @param src a {@link java.util.List} object.
 	 * @return an array of T objects.
 	 */
 	public static <T extends IComparable> T[] list2array(List<T> src){
 		T[] tmp = (T[]) new IComparable[0];
   		return src.toArray(tmp);
   	}

    /**
     * Transfers an array into Vector.
     *
     * @param src a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T extends IComparable> List<T> array2vector(T... src){
        int l = src.length;
		List<T> ret = new ArrayList<>(l);
        Collections.addAll(ret, src);
       	return ret;
    }
    
    /**
     * <p>array2list.</p>
     *
     * @param src a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T extends IComparable> List<T> array2list(T... src){
    	return Arrays.asList(src);
    }
    
 	/** {@inheritDoc} */
 	public List<T> sort(Enumeration<T> source, SortType how){
 		List<T> list = new ArrayList<>();
 		while(source.hasMoreElements()){
 			list.add(source.nextElement());
 		}
 		return sort(list,how);
 	}


}
