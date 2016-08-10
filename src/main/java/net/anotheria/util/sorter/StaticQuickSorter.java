package net.anotheria.util.sorter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 *  just an implementation of the well known QuickSort Algorithm
 *
 * @author another
 * @version $Id: $Id
 */
public final class StaticQuickSorter {

	/**
	 * Sorts a collection of comparables
	 *
	 * @param source collection to sort
	 * @param sType the sort type (sort method and order).
	 * @return a {@link java.util.List} object.
	 */
	public static <T extends IComparable> List<T> sort(Collection<T> source, SortType sType) {
		List<T> tmp = new ArrayList<>(source.size());
		tmp.addAll(source);
		return sort(tmp, sType);
	}

	/**
	 *  sorts a List with the QuickSort Algorithm
	 *
	 *@param  source  the vector you want to sort
	 *@param  sType   SortType  ...  how you wanna sort the Vector
	 *@return         the sorted Vector
	 * @param <T> a T object.
	 */
	public static <T extends IComparable> List<T> sort(List<T> source, SortType sType) {
		boolean sortOrder = sType.getSortOrder();
		int sortAfter = sType.getSortBy();
        
        if(isSorted(source, sType)){
		    return source;
		}
        
        if(isSorted(source, sType.reverse())){
        	return (upsideDown(source));
		}

        boolean wanted = sortOrder == SortType.ASC;
		sort(source, 0, source.size() - 1, wanted, sortAfter);
		return source;
	}

	private static <T extends IComparable> void sort(List<T> source, int start, int end, boolean wanted, int sortAfter) {
        if (start < end) {
            int mid = partition(source, start, end, wanted, sortAfter);
            sort(source, start, mid - 1, wanted, sortAfter);
			sort(source, mid + 1, end, wanted, sortAfter);
		}
	}

    private static <T extends IComparable> List<T> upsideDown(List<T> src){
        if(src == null)
            return null;
        List<T> ret = new ArrayList<>(src.size());
        for(int i = src.size()-1; i >= 0; i--){
            ret.add(src.get(i));
		}
        return ret;
    }

    private static <T extends IComparable> boolean isSorted(Iterable<T> src, SortType type){
        boolean wanted = (type.getSortOrder() == SortType.ASC);
        int sortAfter = type.getSortBy();
        Iterator<T> elements = src.iterator();
        T comp;
        if(elements.hasNext())
            comp = elements.next();
        else
            return true;
        while(elements.hasNext()){
            T comp2 = elements.next();
            if(wanted ? comp.compareTo(comp2,sortAfter) > 0 : comp.compareTo(comp2,sortAfter) < 0)
                return false;
            comp = comp2;
        }
        return true;
    }

	private static <T extends IComparable> int partition(List<T> source, int start, int end, boolean wanted, int sortAfter) {
        T partElement = source.get(end);
        int left = start - 1;
        int right = end;
        while(true) {
			while (wanted ? partElement.compareTo(source.get(++left), sortAfter) > 0 : partElement.compareTo(source.get(++left), sortAfter) < 0) {
				if (left == end) {
					break;
				}
			}
			while (wanted ? partElement.compareTo(source.get(--right), sortAfter) < 0 : partElement.compareTo(source.get(--right), sortAfter) > 0) {
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

	private static <T extends IComparable> void swap(List<T> source, int i, int j) {
		T tmp = source.get(i);
		source.set(i, source.get(j));
		source.set(j, tmp);
	}

	//prevent from instantiation
	private StaticQuickSorter(){}
}
