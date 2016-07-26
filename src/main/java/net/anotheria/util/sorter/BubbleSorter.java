package net.anotheria.util.sorter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * An implementation of a bubble sorter for EQS FI Sorter Pattern.
 */
public class BubbleSorter<T extends IComparable> extends AbstractSorter<T> {

    public List<T> sort(Enumeration<T> source, SortType method){
           List<T> toSort = new ArrayList<>();
           while(source.hasMoreElements())
                toSort.add(source.nextElement());
           return sort(toSort, method);
    }

	public List<T> sort(List<T> source, SortType method){
     	boolean sortOrder = method.getSortOrder();
      	int sortAfter = method.getSortBy();
        if (source==null || source.size()==0)
        	return source;
        T[] data = list2array(source);

        boolean wanted = sortOrder == SortType.ASC ;//? true : false;
        int l = java.lang.reflect.Array.getLength(data);
        boolean changed;
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

   	private static void swap(Object[] data, int first, int second){
    	Object tmp = data[first];
     	data[first] = data[second];
      	data[second] = tmp;
    }
}

