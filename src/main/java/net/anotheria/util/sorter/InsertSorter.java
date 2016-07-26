package net.anotheria.util.sorter;

import java.util.ArrayList;
import java.util.List;

/**
 * A sorter implementation which works by the insert sort algorithm.
 */
public class InsertSorter<T extends IComparable> extends AbstractSorter<T> {

	@Override
	public List<T> sort(List<T> toSort, SortType how){
      	ListEntry<T> list = null;
		int method = how.getSortBy();
		for (T c : toSort){
			if (list==null){
       			list = new ListEntry<>(c);
          		continue;
         	}
          	ListEntry<T> le = new ListEntry<>(c);
          	if (list.value.compareTo(le.value,method)>=0){
           		le.next = list;
             	list = le;
            }else{
            	list.insert(le, method);
            }



        }/*...while*/

        List<T> v = new ArrayList<>(list.length());
        do{
        	v.add(list.value);
         	list = list.next;
        }while(list!=null);

      	return v;


 	}

  	private static class ListEntry<T extends IComparable> {
  		/**
  		 * The value of the entry.
  		 */
  		private T value;

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


