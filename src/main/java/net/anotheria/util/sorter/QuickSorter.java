package net.anotheria.util.sorter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;


/**
 *  just an implementation of the well known QuickSort Algorithm
 *
 *@author Chris Hoffmann
 *@since 21.10.2000
 * @version $Id: $Id
 */
public class QuickSorter<T extends IComparable> extends AbstractSorter<T> {

	/**
	 * {@inheritDoc}
	 *
	 *  sorts an Enumeration with the QuickSort Algorithm and returns a sorted Vector
	 */
	@Override
	public List<T> sort(Enumeration<T> source, SortType sType) {
		List<T> ret = new ArrayList<>();
		while (source.hasMoreElements()) {
			ret.add(source.nextElement());
		}
		return sort(ret, sType);
	}


	/**
	 * {@inheritDoc}
	 *
	 *  sorts a List with the QuickSort Algorithm
	 */
	@Override
	public List<T> sort(List<T> source, SortType sType) {
		boolean sortOrder = sType.getSortOrder();
		int sortAfter = sType.getSortBy();
        SortType tmp = new SortType(sType.getSortBy(),!sType.getSortOrder());
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
        if (start < end) {
            int mid = partition(source, start, end, wanted, sortAfter);
            sort(source, start, mid - 1, wanted, sortAfter);
			sort(source, mid + 1, end, wanted, sortAfter);
		}
	}

    private List<T> upsideDown(List<T> src){
        if(src == null)
            return null;
        List<T> ret = new ArrayList<>(src.size());
        for(int i = src.size()-1; i >= 0; i--){
            ret.add(src.get(i));
		}
        return ret;
    }

    private boolean isSorted(Iterable<T> src, SortType type){
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
            if(wanted ? compare(comp,comp2,sortAfter) > 0 : compare(comp,comp2,sortAfter) < 0)
                return false;
            comp = comp2;
        }
        return true;
    }

	private int partition(List<T> source, int start, int end, boolean wanted, int sortAfter) {
        T partElement = source.get(end);
        int left = start - 1;
        int right = end;
        while(true) {
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
